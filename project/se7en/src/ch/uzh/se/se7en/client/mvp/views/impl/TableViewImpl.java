package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.Film;

public class TableViewImpl extends Composite implements TableView {

	private static TableViewImplUiBinder uiBinder = GWT.create(TableViewImplUiBinder.class);

	interface TableViewImplUiBinder extends UiBinder<Widget, TableViewImpl> {
	}
	

	private TablePresenter tablePresenter;
	private int panelHeight;
	/**
	 * The main DataGrid.
	 */
	@UiField(provided = true)
	DataGrid<Film> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	@UiField 
	Button downloadButton;
	
	AsyncDataProvider<Film> dataProvider;
	
	ListHandler<Film> columnSortHandler;

	TextColumn<Film> nameColumn;
	TextColumn<Film> lengthColumn;
	TextColumn<Film> countryColumn;
	TextColumn<Film> languageColumn;
	TextColumn<Film> yearColumn;
	TextColumn<Film> genreColumn;


	/**
	 * Initialize table view and set default height, width, headerRefresh=true
	 * and border=false
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param -
	 */
	public TableViewImpl() {
		dataGrid = new DataGrid<Film>();
		dataGrid.setWidth("100%");
		panelHeight= Window.getClientHeight();
		dataGrid.setHeight((panelHeight*6)/10 + "px");
		dataGrid.setHeight("500px");
		dataGrid.setBordered(false);
		dataGrid.setAutoHeaderRefreshDisabled(true);

		buildTable();
		setupAsyncDataProvider();
		
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(dataGrid);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(TablePresenter presenter) {
		this.tablePresenter = presenter;
	}
	
	/**
	 * triggers the download when user clicks on download link
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post
	 * @param event
	 */
	@UiHandler("downloadButton")
	public void onDownloadBtnClicked(final ClickEvent event) {
		downloadButton.setText("Loading...");
		downloadButton.setIcon(IconType.REFRESH);
		downloadButton.setIconSpin(true);
		tablePresenter.onDownloadStarted();
	}
	
	private void setupAsyncDataProvider()
	{
		// Create a data provider.
	    dataProvider = new AsyncDataProvider<Film>() {
	      @Override
	      protected void onRangeChanged(HasData<Film> display) {
	        final Range range = display.getVisibleRange();
	        final ColumnSortList sortList = dataGrid.getColumnSortList();
	        if(tablePresenter!=null)
	        {
	        	//prepare the sorting String
	        	if(sortList.size()>0)
	        	{
	        		String direction;
		        	if(sortList.get(0).isAscending()){
		        		direction = "asc";	        			
		        	}
		        	else
		        	{
		        		direction ="desc";
		        	}
		        	
		        	String orderBy = "f."+sortList.get(0).getColumn().getDataStoreName()+ " " + direction;
		        	tablePresenter.orderFilmListBy(orderBy); //give the presenter the ordering String
	        	}
	        	// give the information to the presenter that a the table range changed
	        	tablePresenter.onTableRangeChanged(range.getStart(), range.getLength());
	        }
	      }
	    };
	    //columnSortHandlers makes sure, that whenever a sorting event is called, that the on range changed method is called
	    AsyncHandler columnSortHandler = new AsyncHandler(dataGrid);
	    dataGrid.addColumnSortHandler(columnSortHandler);
	    
	    //add the dataGrid to the async dataprovider
	    dataProvider.addDataDisplay(dataGrid);
	}

	@Override
	public void setTable(List<Film> films, int start) {
		dataProvider.updateRowData(start, films);
		dataGrid.setVisibleRange(new Range(start, films.size()));
	}

	/**
	 * Start the csv download with the obtained url and show modal to start download manually
	 * 
	 * @author Cyrill Halter
	 * @pre downloadUrl != null
	 * @post -
	 * @param String downloadUrl the obtained downloadurl
	 */
	@Override
	public void startDownload(String downloadUrl) {
		// Start the download
		downloadButton.setText("Download");
		downloadButton.setIcon(IconType.DOWNLOAD);
		downloadButton.setIconSpin(false);
		Modal modal = new Modal();
		ModalBody modalBody = new ModalBody();
		Label downloadLabel = new Label();
		
		if(downloadUrl != null){
			
			//download file at downloadUrl	
			Window.open(downloadUrl, "CSV Download", "");

			//show modal to start download manually
			modal.setTitle("Download CSV");
			modal.setClosable(true);
			modal.setFade(true);
			downloadLabel.setText("If the download doesn't start automatically, deactivate your popup blocker or use this link: ");
			downloadLabel.setStyleName("modalText");
			Anchor downloadLink = new Anchor("Download Now", downloadUrl);
			modalBody.add(downloadLabel);
			modalBody.add(downloadLink);
			modal.add(modalBody);
			modal.show();

		}else{

			modal.setTitle("CSV Download Failed");
			modal.setClosable(true);
			modal.setFade(true);
			downloadLabel.setText("Something went wrong... Please try again later.");
			downloadLabel.setStyleName("modalText");
			modalBody.add(downloadLabel);
			modal.add(modalBody);
			modal.show();
		}
	}

	/**
	 * Build the dataGrid and set all column, also set the columns as sortable
	 * if needed
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param -
	 */
	private void buildTable() {
	    
		nameColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				String value;
				if (filmObject.getName() != null) {
					value = filmObject.getName();
				} else {
					value = "";
				}
				return value;
			}
		};
		

		lengthColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {

				String value;
				if (filmObject.getLength() != null) {
					value = Integer.toString(filmObject.getLength());
				} else {
					value = "";
				}
				return value;
			}
		};

		countryColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				String value;
				if (filmObject.getCountries() != null) {
					value = listToString(filmObject.getCountries());
				} else {
					value = "";
				}
				return value;
			}
		};

		languageColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {

				String value;
				if (filmObject.getLanguages() != null) {
					value = listToString(filmObject.getLanguages());
				} else {
					value = "";
				}
				return value;
			}
		};

		yearColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				String value;
				if (filmObject.getYear() != null) {
					value = Integer.toString(filmObject.getYear());
				} else {
					value = "";
				}
				return value;
			}
		};

		genreColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {

				String value;
				if (filmObject.getGenres() != null) {
					value = listToString(filmObject.getGenres());
				} else {
					value = "";
				}
				return value;
			}
		};
		
		nameColumn.setSortable(true);
		lengthColumn.setSortable(true);
		yearColumn.setSortable(true);
		
		nameColumn.setDataStoreName("name");
		lengthColumn.setDataStoreName("length");
		yearColumn.setDataStoreName("year");

		

		dataGrid.setColumnWidth(nameColumn, 18.5, Unit.PCT);
		dataGrid.addColumn(nameColumn, "Name");
		dataGrid.setColumnWidth(yearColumn, 11, Unit.PCT);
		dataGrid.addColumn(yearColumn, "Year");
		dataGrid.setColumnWidth(lengthColumn, 11, Unit.PCT);
		dataGrid.addColumn(lengthColumn, "Length");
		dataGrid.setColumnWidth(countryColumn, 19.5, Unit.PCT);
		dataGrid.addColumn(countryColumn, "Country");
		dataGrid.setColumnWidth(languageColumn, 19.5, Unit.PCT);
		dataGrid.addColumn(languageColumn, "Language");
		dataGrid.setColumnWidth(genreColumn, 20.5, Unit.PCT);
		dataGrid.addColumn(genreColumn, "Genre");

	}


	/**
	 * method that converts a list of genres, countries or languages into a
	 * string so they can be displayed in a cell
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param list
	 * @return concatString
	 */
	private String listToString(List<String> list) {

		String concatString = "";
		int iterator = 0;
		for (String str : list) {
			if (iterator == 0) {
				concatString = str;
				iterator++;
			} else {
				concatString = concatString + " / " + str;
				iterator++;
			}
		}
		return concatString;
	}

	@Override
	public void setResultSize(int size) {
		if(dataProvider!=null)
		{
			dataProvider.updateRowCount(size, true);
			dataGrid.setRowCount(size);
		}
		else
		{
			ClientLog.writeErr("Try to updateRowCount of datagrid before async datatProvider is set");
		}
	}

}
