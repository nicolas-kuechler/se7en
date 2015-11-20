package ch.uzh.se.se7en.client.mvp.views.widgets;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
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
	@UiField Modal licenseModal;
	@UiField AnchorListItem modalOpen;
	@UiField AnchorListItem loading;
	@UiField Image logoLink;
	
	/**
	 * Initialize the NavigationBar, the Creative Commons Modal and set a default Text for the loadingGrowl (workaround)
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return -
	 */
	public NavigationBar() {
		licenseModal = new Modal();
		initWidget(uiBinder.createAndBindUi(this));
		loading.setText("Hello");
		loading.setVisible(false);
		barSetup();
		logoLink.getElement().getStyle().setCursor(Cursor.POINTER); 
	}
	
	/**
	 * Used to set the loading Item in the Navigation to visible if the user starts an import
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param isVisible, message
	 * @return -
	 */
	public void setLoading(boolean isVisible, String message)
	{ 
		if(isVisible)
		{
			loading.setVisible(true);
			loading.setText(message);
		}
		else
		{
			loading.setVisible(false);
		}
	}
	
	/**
	 * Sets the active navigation element regarding the input of the user
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post active element is set
	 * @param navToken
	 * @return -
	 */ 
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
	
	/**
	 * Initialize the ClickHandlers for each element in the navBar
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post 
	 * @param -
	 * @return -
	 */
	private void barSetup()
	{
		homeNav.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateNavigationBar(true, false, false);
				History.newItem(Tokens.HOME);
			}
		});
		
		modalOpen.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				licenseModal.show();
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
		
		logoLink.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateNavigationBar(true, false, false);
				History.newItem(Tokens.HOME);
			}
		});
	}

	/**
	 * Update the navBar and set focus to the selected element. Also send a message to the assigned presenter
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param isHomeActive, isMapActive, isTableActive
	 * @return -
	 */
	
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
