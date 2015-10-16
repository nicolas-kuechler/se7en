package ch.uzh.se.se7en.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.Genre;

/**
 * The service interface for the Film server-side service.
 */
@RemoteServiceRelativePath("service")
public interface FilmService extends RemoteService{
	
	public List<Country> getCountryList(FilmFilter filter);
	
	public List<Film> getFilmList(FilmFilter filter);
	
	public List<Genre> getGenreList(FilmFilter filter);
}
