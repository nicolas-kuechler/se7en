package ch.uzh.se.se7en.client.mvp.presenters;

public interface FilterPresenter extends RootPresenter {
	//For whenever the filterview needs data or if there is an event, there must be a method in this interface
	public void onSendFilter(); 	//for button to send the filter
	public void onClearFilter();	//for button to clear the filter
}
