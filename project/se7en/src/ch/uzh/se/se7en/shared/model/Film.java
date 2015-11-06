package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
import java.util.List;

/**
 * Container to hold the film data on client side and server side. Is used to
 * transport the movie data between client and server.
 * 
 * @author Nicolas Küchler
 */
public class Film implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Integer length;
	private Integer year;
	private List<String> countries;
	private List<String> languages;
	private List<String> genres;

	public Film() {
	}

	public Film(String name) {
		this.name = name;
	}

	public Film(String name, int length, int year, List<String> countries, List<String> languages,
			List<String> genres) {
		this.name = name;
		this.length = length;
		this.countries = countries;
		this.languages = languages;
		this.year = year;
		this.genres = genres;
	}

	public Film(int id, String name, int length, int year, List<String> countries, List<String> languages,
			List<String> genres) {
		this(name, length, year, countries, languages, genres);

		this.id = id;
	}

	/**
	 * Returns a string representation of this Film
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The string representation of this instance
	 */
	@Override
	public String toString() {
		return "Id: " + id + " - Name: " + name + " - Länge: " + length + " - Länder: " + countries + " - Sprachen: "
				+ languages + " - Genres: " + genres;
	}

	/**
	 * @pre id!= null
	 * @post -
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @pre -
	 * @post id==id
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @pre name!= null
	 * @post -
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @pre -
	 * @post name==name
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @pre length!= null
	 * @post -
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * @pre -
	 * @post length==length
	 * @param length
	 *            the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * @pre year!= null
	 * @post -
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @pre -
	 * @post year==year
	 * @param year
	 *            the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @pre countries!= null
	 * @post -
	 * @return the countries
	 */
	public List<String> getCountries() {
		return countries;
	}

	/**
	 * @pre -
	 * @post countries==countries
	 * @param countries
	 *            the countries to set
	 */
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	/**
	 * @pre languages!= null
	 * @post -
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/**
	 * @pre -
	 * @post languages==languages
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	/**
	 * @pre genres!= null
	 * @post -
	 * @return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * @pre -
	 * @post genres==genres
	 * @param genres
	 *            the genres to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
}
