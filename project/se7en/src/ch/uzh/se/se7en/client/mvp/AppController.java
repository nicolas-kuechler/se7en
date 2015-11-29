/**
 * 
 */
package ch.uzh.se.se7en.client.mvp;

import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlPosition;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;

import ch.uzh.se.se7en.client.mvp.views.widgets.NavigationBar;
import ch.uzh.se.se7en.client.rpc.TriggerImportService;
import ch.uzh.se.se7en.client.rpc.TriggerImportServiceAsync;

/**
 * Central control of the application. The ValueChangeHandler reacts to Url
 * History Token changes, parses the token and loads the corresponding view.
 * AppWide EventHandling is controlled here as well.
 * 
 * @author Nicolas Küchler
 */
public class AppController implements ValueChangeHandler<String> {

	private final AppGinjector injector = GWT.create(AppGinjector.class);
	private HasWidgets container;
	private HasWidgets subContainer;
	private NavigationBar navBar;
	private GrowlOptions go = new GrowlOptions();

	public AppController(final NavigationBar navBar) {
		this.navBar = navBar;
		bind();

		injector.getFilmDataModel();
		injector.getFilterPresenter();
		injector.getMapPresenter();
		injector.getTablePresenter();
	}

	/**
	 * @author Nicolas Küchler
	 * @pre -
	 * @post -
	 * @param container
	 *            The container where all the views are loaded in
	 */
	public void go(HasWidgets container, HasWidgets subContainer) {
		this.container = container;
		this.subContainer = subContainer;
	}

	/**
	 * This methods is called whenever there is a change in the url token. The
	 * token gets parsed and the corresponding view is loaded.
	 * 
	 * @author Nicolas Küchler
	 * @pre -
	 * @post -
	 * @param event
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		String filterToken = "";
		
		if(token.indexOf('?')>0) //if there is a query part in the url
		{
			filterToken =token.substring(token.indexOf('?'));
			token = token.substring(0, token.indexOf('?'));
		}
		
		if ((token != null) && (!token.equals(Tokens.HOME))) // if there is a
																// specific
																// token set
		{
			if (token.startsWith(Tokens.MAP)) // it's the map token
			{
				// Filter Parsing needs to be implemented
				doMapView(filterToken);
			} else if (token.startsWith(Tokens.TABLE)) // it's the table token
			{
				// Filter Parsing needs to be implemented
				doTableView(filterToken);
			} else if (token.startsWith(Tokens.IMPORT)) {
				doImport(token);
				doWelcomeView();
			} else if (token.startsWith("msreload")) {
				// TODO Remove for productive
				// Enables to reload the multiselects when they are not loaded
				// properly due to a gwt superdev mode / hibernate bug.
				doReloadMultiSelect();
				doWelcomeView();
			}
		} else // if there is no token --> welcomePage
		{
			doWelcomeView();
		}
	}

	private void doReloadMultiSelect() {
		injector.getFilterPresenter().setupMultiSelects();
	}

	/**
	 * Sets up the History ValueChangeHandler and the application wide events
	 * which go through the eventBus.
	 * 
	 * @author Nicolas Küchler
	 * @pre -
	 * @post -
	 */
	private void bind() {
		// Listen for History Change Events
		History.addValueChangeHandler(this);
	}

	/**
	 * Loads the view of the map consisting of the filterView and the mapView
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post
	 */
	private void doMapView(String filterToken) {
		navBar.setActive(Tokens.MAP);
		Window.setTitle("GIR | Map");
		RootPanel.get("subContainer").setVisible(true);
		injector.getFilterPresenter().go(subContainer);
		injector.getFilterPresenter().setMode(Tokens.MAP);
		injector.getFilterPresenter().setFilter(filterToken);
		injector.getMapPresenter().go(container);
	}

	/**
	 * Loads the view of the table consisting of the filterView and the
	 * tableView
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post
	 */
	private void doTableView(String filterToken) {
		navBar.setActive(Tokens.TABLE);
		Window.setTitle("GIR | Table");
		RootPanel.get("subContainer").setVisible(true);
		injector.getFilterPresenter().go(subContainer);
		injector.getFilterPresenter().setMode(Tokens.TABLE);
		injector.getFilterPresenter().setFilter(filterToken);
		injector.getTablePresenter().go(container);
	}

	/**
	 * Loads the welcome view
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post
	 */
	private void doWelcomeView() {
		Window.setTitle("GIR | Welcome");
		navBar.setActive(Tokens.HOME);
		subContainer.clear();
		RootPanel.get("subContainer").setVisible(false);
		injector.getWelcomePresenter().go(container);
	}

	/**
	 * Triggers the import
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post
	 * @param String
	 *            token
	 */
	private void doImport(String token) {
		final String fileName = token.substring(Tokens.IMPORT.length() + 1); // token
																				// has
																				// format:
																				// import=filename.csv
		// TODO: get the password from the input field
		String password = "TschegeTschege";
		
		navBar.setLoading(true, "Importing...");

		TriggerImportServiceAsync triggerImportService = GWT.create(TriggerImportService.class);
		triggerImportService.importFile(fileName, password, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				navBar.setLoading(false, "");
				go.setType(GrowlType.WARNING);
				go.setPosition(GrowlPosition.TOP_CENTER);
				Growl.growl("An RPC error occured while importing file: " + fileName, go);
			}

			@Override
			public void onSuccess(String result) {
				navBar.setLoading(false, "");
				
				switch(result) {
					case "SUCCESS":
						go.setType(GrowlType.SUCCESS);
						go.setPosition(GrowlPosition.TOP_CENTER);
						Growl.growl(fileName + " was successfully imported!", go);
						break;
					case "INVALID_PASSWORD":
						go.setType(GrowlType.WARNING);
						go.setPosition(GrowlPosition.TOP_CENTER);
						Growl.growl("The provided import password was invalid. Please try again.", go);
						break;
					case "IO_EXCEPTION":
						go.setType(GrowlType.WARNING);
						go.setPosition(GrowlPosition.TOP_CENTER);
						Growl.growl("An IO exception occurred while importing file: " + fileName, go);
						break;
					case "UNKNOWN_ERROR":
					default:
						go.setType(GrowlType.WARNING);
						go.setPosition(GrowlPosition.TOP_CENTER);
						Growl.growl("An unknown error occured while importing file: " + fileName, go);
				}
			}
		});

	}

}
