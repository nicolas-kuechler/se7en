package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
	private DataGrid filmTable;
	
	@UiField Button downloadBtn;
	
	//DEMO PURPOSE is replaced by datagrid
	@UiField ListGroup	resultListGroup;
	
	
	@Inject
	public TableViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(TablePresenter presenter) {
		this.tablePresenter=presenter;
		
	}

	@Override
	public void setTable(List<Film> films) {
		//TODO refresh Table with new film List
		
		//The following Code is just to demonstrate the setTable Function
		resultListGroup.clear();
		for(int i = 0; i < films.size(); i++)
		{
			ListGroupItem item = new ListGroupItem();
			item.setText(films.get(i).getName());
			resultListGroup.add(item);
		}
		//Demo Code End
	}
	
	@UiHandler("downloadBtn")
	public void onDownloadBtnClicked(final ClickEvent event)
	{
		tablePresenter.onDownloadStarted();
	}
	
	private void buildTable()
	{
		//TODO Build Datagrid with all columns...
		//sets up the table with all the sortable columns, headers etc.
	}
	

	@Override
	public void startDownload(String downloadUrl) {
		// TODO Start the download Window.open(download url.....)
		Window.alert("Demo Download Started; Url: " + downloadUrl);
	}

}
