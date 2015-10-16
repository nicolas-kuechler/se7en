package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ch.uzh.se.se7en.client.rpc.FilmService;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.Genre;

public class FilmServiceImpl extends RemoteServiceServlet implements FilmService {

	@Override
	public List<Country> getCountryList(FilmFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Film> getFilmList(FilmFilter filter) {
		// TODO Auto-generated method stub
		// The following code is just a dummy for a db call
		List<Film> demo = new ArrayList<Film>();
		Film f1 = new Film();
		f1.setName("Test Film 1");
		f1.setCountry("Test Country");
		demo.add(f1);
		
		Film f2 = new Film();
		f2.setName("Test Film 2");
		f2.setCountry("Test Country");
		demo.add(f2);
		
		return demo;
		
		//Demo Content End
	}

	@Override
	public List<Genre> getGenreList(FilmFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
