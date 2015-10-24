package ch.uzh.se.se7en.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.uzh.se.se7en.shared.model.FilmFilter;

public interface FilmListExportServiceAsync {
	
	void getFilmListDownloadUrl(FilmFilter filter, AsyncCallback<String> callback);
}
