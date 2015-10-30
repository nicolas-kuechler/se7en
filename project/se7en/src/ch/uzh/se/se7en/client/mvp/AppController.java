/**
 * 
 */
package ch.uzh.se.se7en.client.mvp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.gin.MyAppGinjector;
import ch.uzh.se.se7en.client.mvp.presenters.impl.MapPresenterImpl;
import ch.uzh.se.se7en.client.mvp.presenters.impl.TablePresenterImpl;
import ch.uzh.se.se7en.client.mvp.presenters.impl.WelcomePresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.widgets.NavigationBar;
import ch.uzh.se.se7en.client.rpc.FilmListService;
import ch.uzh.se.se7en.client.rpc.TriggerImportService;
import ch.uzh.se.se7en.client.rpc.TriggerImportServiceAsync;


/**
 Central control of the application. The ValueChangeHandler reacts 
 to Url History Token changes, parses the token and loads the
 corresponding view.
 AppWide EventHandling is controlled here as well.
 
 @author Nicolas Küchler
 */
public class AppController implements ValueChangeHandler<String> {

	private final MyAppGinjector injector = GWT.create(MyAppGinjector.class);
	private EventBus eventBus;
	private HasWidgets container;
	private HasWidgets subContainer;
	private NavigationBar navBar;
	
	public AppController(final NavigationBar navBar)
	{
		this.navBar = navBar;
//		eventBus = clientFactory.getEventBus();
		bind();
		
//		//Makes sure that the global search is possible
//		clientFactory.getFilmDataModel();
//		clientFactory.getFilterPresenter();
//		clientFactory.getMapPresenter();
//		clientFactory.getTablePresenter();
		
		injector.getFilmDataModel();
		injector.getFilterPresenter();
		injector.getMapPresenter();
		injector.getTablePresenter();
	}
	
	/**
	@author Nicolas Küchler
	@pre 	-
	@post	-
	@param 	container The container where all the views are loaded in
	 */
	public void go(HasWidgets container, HasWidgets subContainer) {
		this.container = container;
		this.subContainer = subContainer;
	}
	
	
	/**
	This methods is called whenever there is a change in the url token.
	The token gets parsed and the corresponding view is loaded.
	@author Nicolas Küchler
	@pre 	-
	@post	-
	@param	event 
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if ((token != null) && (!token.equals(Tokens.HOME))) 	//if there is a specific token set
		{
			if (token.startsWith(Tokens.MAP)) 					//it's the map token
			{
				//Filter Parsing needs to be implemented
				doMapView();
			} 
			else if (token.startsWith(Tokens.TABLE)) 			//it's the table token
			{
				//Filter Parsing needs to be implemented
				doTableView();
			}
			else if(token.startsWith(Tokens.IMPORT))
			{
				doImport(token);
				doWelcomeView();
			}
		} 
		else 													//if there is no token --> welcomePage
		{
			doWelcomeView();
		}
	}
	
	
	/**
	Sets up the History ValueChangeHandler and 
	the application wide events which go through the eventBus.
	@author Nicolas Küchler
	@pre -
	@post -
	 */
	private void bind()
	{
		// Listen for History Change Events
		History.addValueChangeHandler(this);
	
		// Setup listening for events on the event bus
	}

	/**
	Loads the view of the map consisting of the filterView and the mapView
	@author Nicolas Küchler
	@pre	container != null
	@post	
	 */
	private void doMapView()
	{
		//combination of mapView and filterView needs to be implemented
		navBar.setActive(Tokens.MAP);
//		clientFactory.getFilterPresenter().go(subContainer);
//		clientFactory.getMapPresenter().go(container);
		
		injector.getFilterPresenter().go(subContainer);
		injector.getMapPresenter().go(container);
	}

	/**
	Loads the view of the table consisting of the filterView and the tableView
	@author Nicolas Küchler
	@pre	container != null
	@post	
	 */
	private void doTableView()
	{
		//combination of tableView and filterView needs to be implemented
		navBar.setActive(Tokens.TABLE);
		
		//clientFactory.getFilterPresenter().go(subContainer);
		//clientFactory.getTablePresenter().go(container);
		
		injector.getFilterPresenter().go(subContainer);
		injector.getTablePresenter().go(container);
	}

	/**
	Loads the welcome view
	@author Nicolas Küchler
	@pre	container != null
	@post	
	 */
	private void doWelcomeView()
	{
		//welcome view needs to be implemente
		navBar.setActive(Tokens.HOME);
		subContainer.clear();
		//clientFactory.getWelcomePresenter().go(container);
		injector.getWelcomePresenter().go(container);
	}
	
	private void doImport(String token)
	{
		//TODO Import RPC Service
		String fileName = token.substring(Tokens.IMPORT.length()+1); //token has format: import=filename.csv
		TriggerImportServiceAsync triggerImportService = GWT.create(TriggerImportService.class);
		triggerImportService.importFile(fileName, new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Error Handling Import Trigger
			}

			@Override
			public void onSuccess(Boolean result) {
				//TODO Import Successfull message
				Window.alert("Successfull Import");
			}
		});
		
	}


}
