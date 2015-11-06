package ch.uzh.se.se7en.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.Genre;
import ch.uzh.se.se7en.shared.model.SelectOption;

public interface FilmListServiceAsync {

	void getCountryList(FilmFilter filter, AsyncCallback<List<Country>> callback);

	void getFilmList(FilmFilter filter, AsyncCallback<List<Film>> callback);

	void getGenreList(FilmFilter filter, AsyncCallback<List<Genre>> callback);

	void getGenreSelectOption(AsyncCallback<List<SelectOption>> callback);

	void getCountrySelectOption(AsyncCallback<List<SelectOption>> callback);

	void getLanguageSelectOption(AsyncCallback<List<SelectOption>> callback);

}
