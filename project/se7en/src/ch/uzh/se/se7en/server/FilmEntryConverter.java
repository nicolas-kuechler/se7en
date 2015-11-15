package ch.uzh.se.se7en.server;

import com.googlecode.jcsv.writer.CSVEntryConverter;

import ch.uzh.se.se7en.shared.model.Film;

/**
 * This class defines an entry converter for use with the jcsv library.
 * @author Cyrill Halter
 *
 */
public class FilmEntryConverter implements CSVEntryConverter<Film>{

	/**
	 * This method is called to convert a Film object into an array of Strings
	 * corresponding to entries in a CSV file.
	 * Fields that are set to null are converted to empty Strings.
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * @param Film film The Film object to be converted
	 * @returns String [] columns The Film object represented by a String array
	 *            
	 */
	@Override
	public String[] convertEntry(Film film) {
		String[] columns = new String[6];

		//write name
		if (film.getName() == null){
			columns[0] = "";
		}else{
			columns[0] = film.getName();
		}

		//write length
		if (film.getLength() == null){
			columns[1] = "";
		}else{
			columns[1] = film.getLength().toString();
		}

		//write countries
		if(film.getCountries() == null){
			columns[2] = "";
		}else{
			String countries = "";
			for (String country : film.getCountries()){
				if(countries.equals("")){
					countries = country;
				}else{
					countries = countries + "--" + country;
				}
			}
			columns[2] = countries;
		}

		//write languages
		if(film.getLanguages() == null ){
			columns[3] = "";
		}else{
			String languages = "";
			for (String language : film.getLanguages()){
				if(languages.equals("")){
					languages = language;
				}else{
					languages = languages + "--" + language;
				}
			}
			columns[3] = languages;
		}

		//write year
		if (film.getYear() == null){
			columns[4] = "";
		}else{
			columns[4] = film.getYear().toString();
		}

		//write genres
		if(film.getLanguages() == null ){
			columns[3] = "";
		}else{
			String genres = "";
			for (String genre : film.getGenres()){
				if(genres.equals("")){
					genres = genre;
				}else{
					genres = genres + "--" + genre;
				}
			}
			columns[5] = genres;

			
		}
		
		return columns;
	}

}
