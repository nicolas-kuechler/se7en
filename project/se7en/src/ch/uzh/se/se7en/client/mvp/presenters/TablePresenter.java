package ch.uzh.se.se7en.client.mvp.presenters;

public interface TablePresenter extends RootPresenter {
	//For whenever the tableview needs data, there must be a method in this interface
	public void onNewFilmDataNeeded();
}
