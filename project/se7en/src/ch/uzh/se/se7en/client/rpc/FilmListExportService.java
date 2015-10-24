package ch.uzh.se.se7en.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ch.uzh.se.se7en.shared.model.FilmFilter;

@RemoteServiceRelativePath("filmListExportService")
public interface FilmListExportService extends RemoteService{
	public String getFilmListDownloadUrl(FilmFilter filter);

}
