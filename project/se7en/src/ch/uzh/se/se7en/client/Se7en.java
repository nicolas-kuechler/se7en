package ch.uzh.se.se7en.client;
import org.gwtbootstrap3.client.ui.Panel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

import ch.uzh.se.se7en.client.mvp.AppController;
import ch.uzh.se.se7en.client.mvp.views.widgets.FooterBar;
import ch.uzh.se.se7en.client.mvp.views.widgets.NavigationBar;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Se7en implements EntryPoint {

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		//The following Lines are just demo content
		Panel container = new Panel();
		Panel subContainer = new Panel();
		NavigationBar navBar = new NavigationBar();
		FooterBar footerBar = new FooterBar();
		navBar.getElement().setId("navBar");
		subContainer.getElement().setId("subContainer");
		container.getElement().setId("container");
		footerBar.getElement().setId("footerBar");
		
		
		RootPanel.get().add(navBar);
		RootPanel.get().add(subContainer);
		RootPanel.get().add(container);
		RootPanel.get().add(footerBar);
		
		AppController app = new AppController(navBar);
		app.go(container, subContainer);
		History.fireCurrentHistoryState();
		//Demo Content end
	}
}
