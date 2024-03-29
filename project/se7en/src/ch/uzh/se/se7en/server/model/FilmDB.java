package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import ch.uzh.se.se7en.shared.model.Film;

/**
 * Container for fetching all Country related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Table(name = "films")
public class FilmDB {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "length", nullable = true)
	private Integer length;

	@Column(name = "year", nullable = true)
	private Integer year;

	@Column(name = "wikipedia", nullable = true)
	private String wikipedia;
	
	@Column(name = "country_string", nullable = true)
	private String countryString;
	
	@Column(name = "genre_string", nullable = true)
	private String genreString;
	
	@Column(name = "language_string", nullable = true)
	private String languageString;

	// the corresponding entities in the join table film_countries
	@OneToMany(mappedBy = "primaryKey.film", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Set<FilmCountryDB> filmCountryEntities;

	// the corresponding entities in the join table film_genres
	@OneToMany(mappedBy = "primaryKey.film", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Set<FilmGenreDB> filmGenreEntities;

	// the corresponding entities in the join table film_languages
	@OneToMany(mappedBy = "primaryKey.film", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Set<FilmLanguageDB> filmLanguageEntities;

	public FilmDB() {

	}

	public FilmDB(String name, Integer length, Integer year) {
		this.name = name;
		this.length = length;
		this.year = year;
	}

	public FilmDB(String name, Integer length, Integer year, Set<FilmCountryDB> filmCountryEntities,
			Set<FilmLanguageDB> filmLanguageEntities, Set<FilmGenreDB> filmGenreEntities) {
		this(name, length, year);
		this.filmCountryEntities = filmCountryEntities;
		this.filmLanguageEntities = filmLanguageEntities;
		this.filmGenreEntities = filmGenreEntities;
	}

	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Film The FilmDB entity converted to a film data transfer object
	 */
	public Film toFilm() {
		// TODO RS: better way? => generic method, via interface
		List<String> filmCountryEntityNames = new ArrayList<String>();
		List<String> filmGenreEntityNames = new ArrayList<String>();
		List<String> filmLanguageEntityNames = new ArrayList<String>();
		
		if(countryString != null) {
			filmCountryEntityNames.add(countryString);
		}
		
		if(genreString != null) {
			filmGenreEntityNames.add(genreString);
		}
		
		if(languageString != null) {
			filmLanguageEntityNames.add(languageString);
		}

		return new Film(id, name, length, year, filmCountryEntityNames, filmLanguageEntityNames, filmGenreEntityNames);
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
	 * @pre wikipedia!= null
	 * @post -
	 * @return the wikipedia
	 */
	public String getWikipedia() {
		return wikipedia;
	}

	/**
	 * @pre -
	 * @post wikipedia==wikipedia
	 * @param wikipedia
	 *            the wikipedia to set
	 */
	public void setWikipedia(String wikipedia) {
		this.wikipedia = wikipedia;
	}

	/**
	@pre countryString!= null
	@post -
	@return the countryString
	 */
	public String getCountryString() {
		return countryString;
	}

	/**
	@pre -
	@post countryString==countryString
	@param countryString the countryString to set
	*/
	public void setCountryString(String countryString) {
		this.countryString = countryString;
	}

	/**
	@pre genreString!= null
	@post -
	@return the genreString
	 */
	public String getGenreString() {
		return genreString;
	}

	/**
	@pre -
	@post genreString==genreString
	@param genreString the genreString to set
	*/
	public void setGenreString(String genreString) {
		this.genreString = genreString;
	}

	/**
	@pre languageString!= null
	@post -
	@return the languageString
	 */
	public String getLanguageString() {
		return languageString;
	}

	/**
	@pre -
	@post languageString==languageString
	@param languageString the languageString to set
	*/
	public void setLanguageString(String languageString) {
		this.languageString = languageString;
	}

	/**
	 * @pre filmCountryEntities!= null
	 * @post -
	 * @return the filmCountryEntities
	 */
	public Set<FilmCountryDB> getFilmCountryEntities() {
		return filmCountryEntities;
	}

	/**
	 * @pre -
	 * @post filmCountryEntities==filmCountryEntities
	 * @param filmCountryEntities
	 *            the filmCountryEntities to set
	 */
	public void setFilmCountryEntities(Set<FilmCountryDB> filmCountryEntities) {
		this.filmCountryEntities = filmCountryEntities;
	}

	/**
	 * @pre filmGenreEntities!= null
	 * @post -
	 * @return the filmGenreEntities
	 */
	public Set<FilmGenreDB> getFilmGenreEntities() {
		return filmGenreEntities;
	}

	/**
	 * @pre -
	 * @post filmGenreEntities==filmGenreEntities
	 * @param filmGenreEntities
	 *            the filmGenreEntities to set
	 */
	public void setFilmGenreEntities(Set<FilmGenreDB> filmGenreEntities) {
		this.filmGenreEntities = filmGenreEntities;
	}

	/**
	 * @pre filmLanguageEntities!= null
	 * @post -
	 * @return the filmLanguageEntities
	 */
	public Set<FilmLanguageDB> getFilmLanguageEntities() {
		return filmLanguageEntities;
	}

	/**
	 * @pre -
	 * @post filmLanguageEntities==filmLanguageEntities
	 * @param filmLanguageEntities
	 *            the filmLanguageEntities to set
	 */
	public void setFilmLanguageEntities(Set<FilmLanguageDB> filmLanguageEntities) {
		this.filmLanguageEntities = filmLanguageEntities;
	}
}
