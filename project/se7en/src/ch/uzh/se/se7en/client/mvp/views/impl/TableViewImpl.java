package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.mvp.LoadingStates;
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
	@UiField ListGroup	resultListGroup;
	
	public TableViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
		tableViewButton.setDataLoadingText("Loading...");
		setLoadingState(LoadingStates.DEFAULT);
	}

	@Override
	public void setPresenter(TablePresenter presenter) {
		this.tablePresenter=presenter;
		
	}

	@Override
	public void setTable(List<Film> films) {
		//The following Code is just to demonstrate the EventBus Function
		resultListGroup.clear();
		for(int i = 0; i < films.size(); i++)
		{
			ListGroupItem item = new ListGroupItem();
			item.setText(films.get(i).getName());
			resultListGroup.add(item);
		}
		//Demo Code End
	}
	
	//DEMO PURPOSE
	@UiHandler("tableViewButton")
	public void onButtonClick(final ClickEvent event)
	{
		tablePresenter.onNewFilmDataNeeded();
	}

	//Entspricht nicht dem MVP PATTERN UND MUSS DARUM GEÄNDERT WERDEN ??? Oder nicht
	//Presenter hat Zugriff auf alle UI Komponenten und muss diese steuern.
	@Override
	public void setLoadingState(String state) {
		if (state.equals(LoadingStates.ERROR))
		{
			tableViewButton.state().reset();
			tableViewButton.setType(ButtonType.DANGER);
	
		}
		else if(state.equals(LoadingStates.LOADING))
		{
			tableViewButton.state().loading();

		}
		else if(state.equals(LoadingStates.SUCCESS))
		{
			tableViewButton.state().reset();
			tableViewButton.setType(ButtonType.SUCCESS);
		}		
		else if(state.equals(LoadingStates.DEFAULT))
		{
			tableViewButton.state().reset();
			tableViewButton.setType(ButtonType.PRIMARY);

		}
	}

}
