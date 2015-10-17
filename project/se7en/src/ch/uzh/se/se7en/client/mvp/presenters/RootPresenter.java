package ch.uzh.se.se7en.client.mvp.presenters;

import com.google.gwt.user.client.ui.HasWidgets;

public interface RootPresenter {
	public void go(final HasWidgets container);
	public void bind();
	/**
	Provides the presenters, which communicate with the server over the FilmDataModel, with information about the current loading state. 
	This allows the presenter to use his associated view to provide the user the information.
	@author Nicolas Küchler
	@pre	-
	@post	associated view displays the loading state to the user
	@param 	state == LoadingStates.DEFAULT || state == LoadingStates.LOADING || state == LoadingStates.ERROR || state == LoadingStates.SUCCESS
	 */
	public void setLoadingState(String state);
}
