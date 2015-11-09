package ch.uzh.se.se7en.server.model;

import com.googlecode.jcsv.annotations.MapToColumn;

/***
 * This class is used to help with importing the csv file to java objects.
 * Objects of this class are later converted to real Film objects.
 * 
 * @author Cyrill Halter
 *
 */
public class FilmHelper {

	@MapToColumn(column = 0)
	private String name;

	@MapToColumn(column = 1)
	private Integer length;

	@MapToColumn(column = 2)
	private String countries;

	@MapToColumn(column = 3)
	private String languages;

	@MapToColumn(column = 4)
	private Integer year;

	@MapToColumn(column = 5)
	private String genres;
	
	public FilmHelper(){}

	public FilmHelper(String name, Integer length, String countries, String languages, Integer year, String genres) {
		
		this.name = name;
		this.length = length;
		this.countries = countries;
		this.languages = languages;
		this.year = year;
		this.genres = genres;

	}

	public String getName() {
		return name;
	}

	public Integer getLength() {
		return length;
	}

	public String getCountries() {
		return countries;
	}

	public String getLanguages() {
		return languages;
	}

	public Integer getYear() {
		return year;
	}

	public String getGenres() {
		return genres;
	}

}