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

import ch.uzh.se.se7en.client.mvp.Tokens;

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
	
	public void setActive(String navToken)
	{
		if (navToken.equals(Tokens.HOME))
		{
			updateNavigationBar(true, false, false);
		}
		else if (navToken.equals(Tokens.MAP))
		{
			updateNavigationBar(false, true, false);
		}
		else if (navToken.equals(Tokens.TABLE))
		{
			updateNavigationBar(false, false, true);
		}
	}
	
	private void barSetup()
	{
		homeNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateNavigationBar(true, false, false);
				History.newItem(Tokens.HOME);
			}
		});
		
		mapNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateNavigationBar(false, true, false);
				History.newItem(Tokens.MAP);
			}
		});
		
		tableNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateNavigationBar(false, false, true);
				History.newItem(Tokens.TABLE);
			}
		});
	}

	private void updateNavigationBar(boolean isHomeActive, boolean isMapActive, boolean isTableActive)
	{
		homeNav.setFocus(false);
		mapNav.setFocus(false);
		tableNav.setFocus(false);
		homeNav.setActive(isHomeActive);
		mapNav.setActive(isMapActive);
		tableNav.setActive(isTableActive);
	}

}
