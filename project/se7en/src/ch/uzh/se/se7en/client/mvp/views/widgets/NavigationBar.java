package ch.uzh.se.se7en.client.mvp.views.widgets;

import org.gwtbootstrap3.client.ui.AnchorListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NavigationBar extends Composite {

	private static NavigationBarUiBinder uiBinder = GWT.create(NavigationBarUiBinder.class);

	interface NavigationBarUiBinder extends UiBinder<Widget, NavigationBar> {
	}
	
	@UiField AnchorListItem homeNav;
	@UiField AnchorListItem mapNav;
	@UiField AnchorListItem tableNav;

	public NavigationBar() {
		initWidget(uiBinder.createAndBindUi(this));
		barSetup();
	}
	
	private void barSetup()
	{
		homeNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				homeNav.setActive(true);
				mapNav.setActive(false);
				tableNav.setActive(false);
				History.newItem("");
			}
		});
		
		mapNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				homeNav.setActive(false);
				mapNav.setActive(true);
				tableNav.setActive(false);
				History.newItem("map");
			}
		});
		
		tableNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				homeNav.setActive(false);
				mapNav.setActive(false);
				tableNav.setActive(true);
				History.newItem("table");
			}
		});
	}

}
