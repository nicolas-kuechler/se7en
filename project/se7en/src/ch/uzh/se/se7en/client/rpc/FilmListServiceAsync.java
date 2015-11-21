package ch.uzh.se.se7en.client.rpc;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.FilterOptions;
import ch.uzh.se.se7en.shared.model.Genre;

public interface FilmListServiceAsync {

	void getCountryList(FilmFilter filter, AsyncCallback<List<Country>> callback);

	void getFilmList(FilmFilter filter, AsyncCallback<List<Film>> callback);

	void getGenreList(FilmFilter filter, AsyncCallback<List<Genre>> callback);

	void getSelectOptions(AsyncCallback<FilterOptions> callback);

}
