package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.Comparator;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.Film;

public class TableViewImpl extends Composite implements TableView{

	private static TableViewImplUiBinder uiBinder = GWT.create(TableViewImplUiBinder.class);

	interface TableViewImplUiBinder extends UiBinder<Widget, TableViewImpl> {
	}
	
	private TablePresenter tablePresenter;
	
	private TextColumn sortColumn;
	String cwDataGridEmpty = "IS EMPTY";
	private String concatString;
	/**
	 * The main DataGrid.
	 */
	@UiField(provided = true)
	DataGrid<Film> dataGrid;

	@UiHandler("downloadBtn")
	public void onDownloadBtnClicked(final ClickEvent event) {
		tablePresenter.onDownloadStarted();
	}

	/**
	 * The pager used to change the range of data.
	 */
	// @UiField(provided = true)
	// SimplePager pager;

	/**
	 * Initialize this example.
	 */

	public TableViewImpl() {

		// DataGrid<Film> dataGrid = new DataGrid<Film>();
		dataGrid = new DataGrid<Film>();
		dataGrid.setWidth("100%");
		dataGrid.setHeight("500px");
		dataGrid.setBordered(true);
		dataGrid.setAutoHeaderRefreshDisabled(true);
		
		buildTable();

		initWidget(uiBinder.createAndBindUi(this));

		// TODO Setup Enter Pressed for start search
	}

	private String listToString(List<String> list){
		
		concatString = "";
		int iterator=0;
		for(String str: list){
			if(iterator == 0){
				concatString = str;
				iterator++;
			}else{
				concatString = concatString + " / " + str  ;
				iterator++;
			}
			
		}
		return concatString;
	}
	/**
	 * Add the columns to the table.
	 */
	private void buildTable() {

		TextColumn<Film> nameColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				return filmObject.getName();
			}
		};
		
		sortColumn = nameColumn;
		nameColumn.setSortable(true);
		


		TextColumn<Film> lengthColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				return Integer.toString(filmObject.getLength());
			}
		};


		TextColumn<Film> countryColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				return listToString(filmObject.getCountries());
			}
		};


		TextColumn<Film> languageColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				return listToString(filmObject.getLanguages());
			}
		};


		TextColumn<Film> yearColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				return Integer.toString(filmObject.getYear());
			}
		};


		TextColumn<Film> genreColumn = new TextColumn<Film>() {
			@Override
			public String getValue(Film filmObject) {
				return listToString(filmObject.getGenres());
			}
		};
		
		dataGrid.setColumnWidth(nameColumn, 16.66, Unit.PCT);
		dataGrid.addColumn(nameColumn, "Name");
		dataGrid.setColumnWidth(lengthColumn, 16.66, Unit.PCT);
		dataGrid.addColumn(lengthColumn, "Length");
		dataGrid.setColumnWidth(countryColumn, 16.66, Unit.PCT);
		dataGrid.addColumn(countryColumn, "Country");
		dataGrid.setColumnWidth(languageColumn, 16.66, Unit.PCT);
		dataGrid.addColumn(languageColumn, "Language");
		dataGrid.setColumnWidth(yearColumn, 16.66, Unit.PCT);
		dataGrid.addColumn(yearColumn, "Year");
		dataGrid.setColumnWidth(genreColumn,16.66 , Unit.PCT);
		dataGrid.addColumn(genreColumn, "Genre");
		
		
	}

	@Override
	public void setPresenter(TablePresenter presenter) {
		this.tablePresenter = presenter;
	}

	@Override
	public void setTable(List<Film> films) {
		
		ListHandler<Film> columnSortHandler = new ListHandler<Film>(films);
	    columnSortHandler.setComparator(sortColumn,new Comparator<Film>() {
	          public int compare(Film f1, Film f2) {
	            if (f1 == f2) {
	              return 0;
	            }

	            // Compare the name columns.
	            if (f1 != null) {
	            	if (f2!=null){
	            		dataGrid.redraw(); 
	            		return f1.getName().compareTo(f2.getName());
	            	}
	            }
	            return -1;
	          }
	        });
	    
	    dataGrid.addColumnSortHandler(columnSortHandler);
	    // We know that the data is sorted alphabetically by default.
	    dataGrid.getColumnSortList().push(sortColumn);
	    dataGrid.redraw(); 
		dataGrid.setRowCount(films.size(), true);
		dataGrid.setRowData(0, films);

	}
	


	@Override
	public void startDownload(String downloadUrl) {
		// TODO Start the download Window.open(download url.....)
		Window.alert("Demo Download Started; Url: " + downloadUrl);
	}
}
