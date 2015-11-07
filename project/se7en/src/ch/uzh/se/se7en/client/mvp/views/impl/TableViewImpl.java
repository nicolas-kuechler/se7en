package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.PanelFooter;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.Film;

public class TableViewImpl extends Composite implements TableView {

	private static TableViewImplUiBinder uiBinder = GWT.create(TableViewImplUiBinder.class);

	interface TableViewImplUiBinder extends UiBinder<Widget, TableViewImpl> {
	}

	private TablePresenter tablePresenter;
	/**
	 * The main DataGrid.
	 */
	@UiField(provided = true)
	DataGrid<Film> dataGrid;
	ListDataProvider<Film> filmProvider = new ListDataProvider<Film>();
	ListHandler<Film> columnSortHandler;

	TextColumn<Film> nameColumn;
	TextColumn<Film> lengthColumn;
	TextColumn<Film> countryColumn;
	TextColumn<Film> languageColumn;
	TextColumn<Film> yearColumn;
	TextColumn<Film> genreColumn;

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
		tablePresenter.onDownloadStarted();
	}

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
		dataGrid.setHeight("500px");
		dataGrid.setBordered(false);
		dataGrid.setAutoHeaderRefreshDisabled(true);

		buildTable();
		filmProvider.addDataDisplay(dataGrid);

		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Set the presnter for the table view
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param presenter
	 */
	@Override
	public void setPresenter(TablePresenter presenter) {
		this.tablePresenter = presenter;
	}

	/**
	 * Writes the films who were sent from the presenter into the dataGrid
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param films
	 */
	@Override
	public void setTable(List<Film> films) {
		filmProvider.setList(films);
		createColumnSortHandler();
		dataGrid.addColumnSortHandler(columnSortHandler);
	}

	/**
	 * Give the download url to the user
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param downloadUrl
	 */
	@Override
	public void startDownload(String downloadUrl) {
		// TODO Start the download Window.open(download url.....)
		Window.alert("Demo Download Started; Url: " + downloadUrl);
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
		nameColumn.setSortable(true);

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
		lengthColumn.setSortable(true);

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
		countryColumn.setSortable(true);

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
		languageColumn.setSortable(true);

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
		yearColumn.setSortable(true);

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
		genreColumn.setSortable(true);

		dataGrid.setColumnWidth(nameColumn, 21.5, Unit.PCT);
		dataGrid.addColumn(nameColumn, "Name");
		dataGrid.setColumnWidth(yearColumn, 7, Unit.PCT);
		dataGrid.addColumn(yearColumn, "Year");
		dataGrid.setColumnWidth(lengthColumn, 7, Unit.PCT);
		dataGrid.addColumn(lengthColumn, "Length");
		dataGrid.setColumnWidth(countryColumn, 21.5, Unit.PCT);
		dataGrid.addColumn(countryColumn, "Country");
		dataGrid.setColumnWidth(languageColumn, 21.5, Unit.PCT);
		dataGrid.addColumn(languageColumn, "Language");
		dataGrid.setColumnWidth(genreColumn, 21.5, Unit.PCT);
		dataGrid.addColumn(genreColumn, "Genre");

	}

	/**
	 * Initialize the columnsorthandler and assign them to the according column
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param -
	 */
	private void createColumnSortHandler() {
		columnSortHandler = new ListHandler<Film>(filmProvider.getList());

		columnSortHandler.setComparator(nameColumn, new Comparator<Film>() {
			@Override
			public int compare(Film o1, Film o2) {
				if (o1.getName() == o2.getName()) {
					return 0;
				}

				if (o1.getName() == null) {
					return 1;
				}
				if (o2.getName() == null) {
					return -1;
				}

				return o1.getName().compareTo(o2.getName());
			}

		});

		columnSortHandler.setComparator(lengthColumn, new Comparator<Film>() {
			@Override
			public int compare(Film o1, Film o2) {

				if (o1.getLength() == o2.getLength()) {
					return 0;
				}
				if (o1.getLength() == null) {
					return 1;
				}
				if (o2.getLength() == null) {
					return -1;
				}

				if (o1.getLength() < o2.getLength()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

		columnSortHandler.setComparator(countryColumn, new Comparator<Film>() {
			@Override
			public int compare(Film o1, Film o2) {
				if (o1.getCountries() == o2.getCountries()) {
					return 0;
				}
				if (o1.getCountries() == null) {
					return 1;
				}
				if (o2.getCountries() == null) {
					return -1;
				}

				return listToString(o1.getCountries()).compareTo(listToString(o2.getCountries()));
			}
		});

		columnSortHandler.setComparator(languageColumn, new Comparator<Film>() {
			@Override
			public int compare(Film o1, Film o2) {
				if (o1.getLanguages() == o2.getLanguages()) {
					return 0;
				}
				if (o1.getLanguages() == null) {
					return 1;
				}
				if (o2.getLanguages() == null) {
					return -1;
				}
				return listToString(o1.getLanguages()).compareTo(listToString(o2.getLanguages()));
			}
		});

		columnSortHandler.setComparator(yearColumn, new Comparator<Film>() {
			@Override
			public int compare(Film o1, Film o2) {
				if (o1.getYear() == o2.getYear()) {
					return 0;
				}
				if (o1.getYear() == null) {
					return 1;
				}
				if (o2.getYear() == null) {
					return -1;
				}

				if (o1.getYear() < o2.getYear()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

		columnSortHandler.setComparator(genreColumn, new Comparator<Film>() {
			@Override
			public int compare(Film o1, Film o2) {
				if (o1.getGenres() == o2.getGenres()) {
					return 0;
				}
				if (o1.getGenres() == null) {
					return 1;
				}
				if (o2.getGenres() == null) {
					return -1;
				}

				return listToString(o1.getGenres()).compareTo(listToString(o2.getGenres()));
			}
		});

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

}