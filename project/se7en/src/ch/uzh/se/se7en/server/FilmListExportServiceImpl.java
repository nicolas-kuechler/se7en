package ch.uzh.se.se7en.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;

import ch.uzh.se.se7en.client.rpc.FilmListExportService;
import ch.uzh.se.se7en.shared.model.FilmFilter;

@Singleton
public class FilmListExportServiceImpl extends RemoteServiceServlet implements FilmListExportService {

	@Override
	public String getFilmListDownloadUrl(FilmFilter filter) {
		// TODO CH Implement function
		return "Demo Download Url";
	}

}
