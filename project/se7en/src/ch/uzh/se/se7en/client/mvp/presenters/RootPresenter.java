package ch.uzh.se.se7en.client.mvp.presenters;

import com.google.gwt.user.client.ui.HasWidgets;

public interface RootPresenter {
	/**
	Gives the presenter the control over part of the application.
	@author Nicolas Küchler
	@pre	container != null
	@post	container contains the choosen view and the choosen presenter takes control over the container 
	@param	container a container where widgets can be placed
	 */
	public void go(final HasWidgets container);
	
	/**
	Binds view and presenter together
	@author Nicolas Küchler
	@pre	-
	@post	-
	 */
	public void bind();
}
