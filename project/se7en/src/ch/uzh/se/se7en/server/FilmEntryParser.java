package ch.uzh.se.se7en.server;

import java.util.Arrays;
import java.util.List;

import com.googlecode.jcsv.reader.CSVEntryParser;

import ch.uzh.se.se7en.shared.model.Film;

public class FilmEntryParser implements CSVEntryParser<Film>{

	@Override
	public Film parseEntry(String... data) {
		
		String name = null;
		Integer length = null;
		List<String> countries = null;
		List<String> languages = null;
		Integer year = null;
		List<String> genres = null;
		
		if (!data[0].trim().equals("")){
			name = data[0];
		}
		if(!data[1].trim().equals("")){
			length = Integer.parseInt(data[1]);
		}
		if(!data[2].trim().equals("")){
			countries = Arrays.asList(data[2].split("--"));
		}
		if(!data[3].trim().equals("")){
			languages = Arrays.asList(data[3].split("--"));
		}
		if(!data[4].trim().equals("")){
			length = Integer.parseInt(data[4]);
		}
		if(!data[5].trim().equals("")){
			genres = Arrays.asList(data[4].split("--"));
		}
		
		return new Film(name, length, year, countries, languages, genres);
		
	}

}
