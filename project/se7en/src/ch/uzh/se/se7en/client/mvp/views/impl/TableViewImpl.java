package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.Film;

public class TableViewImpl extends Composite implements TableView{

	private static TableViewImplUiBinder uiBinder = GWT.create(TableViewImplUiBinder.class);

	interface TableViewImplUiBinder extends UiBinder<Widget, TableViewImpl> {
	}
	
	private TablePresenter tablePresenter;
	
	//DEMO PURPOSE
	@UiField Button tableViewButton;
	
	public TableViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(TablePresenter presenter) {
		this.tablePresenter=presenter;
		
	}

	@Override
	public void setTable(List<Film> films) {
		//The following Code is just to demonstrate the EventBus Function
		String demo = "";
		for(int i = 0; i < films.size(); i++)
		{
			demo = demo + " " +films.get(i).getName();
		}

		Window.alert("Table Data Updated: " + demo);
		//Demo Code End
	}
	
	//DEMO PURPOSE
	@UiHandler("tableViewButton")
	public void onButtonClick(final ClickEvent event)
	{
		tablePresenter.onNewFilmDataNeeded();
	}

}
