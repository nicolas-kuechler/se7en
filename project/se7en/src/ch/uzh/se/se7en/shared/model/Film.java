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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countries == null) ? 0 : countries.hashCode());
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + id;
		result = prime * result + ((languages == null) ? 0 : languages.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Film))
			return false;
		Film other = (Film) obj;
		if (countries == null) {
			if (other.countries != null)
				return false;
		} else if (!countries.equals(other.countries))
			return false;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (id != other.id)
			return false;
		if (languages == null) {
			if (other.languages != null)
				return false;
		} else if (!languages.equals(other.languages))
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
