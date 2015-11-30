package ch.uzh.se.se7en.server;

import java.util.Arrays;
import java.util.List;

import com.googlecode.jcsv.reader.CSVEntryParser;

import ch.uzh.se.se7en.shared.model.Film;

/**
 * This class defines an entry parser for use with the jcsv library.
 * @author Cyrill Halter
 *
 */
public class FilmEntryParser implements CSVEntryParser<Film>{

	@Override
	/**
	 * This method is called to to parse a line from the csv file into a
	 * Film object. Fields that have no value in the csv file are set to
	 * null in the Film object.
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * @param String... data String array read from the csv file line
	 * @returns new Film A Film object created from a line out of  the csv file
	 *            
	 */
	public Film parseEntry(String... data) {
		
		String name = null;
		Integer length = null;
		List<String> countries = null;
		List<String> languages = null;
		Integer year = null;
		List<String> genres = null;
		String wikipedia = null;
		
		if (!data[0].trim().equals("")){
			name = data[0];
			// ServerLog.writeErr(name);
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
			year = Integer.parseInt(data[4]);
		}
		if(!data[5].trim().equals("")){
			genres = Arrays.asList(data[5].split("--"));
		}
		// TODO CH check dass de RS da nix falsch gmacht het ;)
		if (!data[6].trim().equals("")){
			wikipedia = data[6];
		}
		// TODO CH check dass de RS da nix falsch gmacht het ;)
		Film importedFilm = new Film(name, wikipedia, length, year, countries, languages, genres);
		return importedFilm;
		
	}

}
