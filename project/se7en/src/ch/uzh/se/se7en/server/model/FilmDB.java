package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.CascadeType;

import ch.uzh.se.se7en.shared.model.DTO;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * Container for fetching all Country related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Table(name = "films")
public class FilmDB implements DTO {
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

	// the corresponding entities in the join table film_countries
	@OneToMany(mappedBy = "primaryKey.film", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<FilmCountryDB> filmCountryEntities;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "film_languages", joinColumns = { @JoinColumn(name = "film_id") }, inverseJoinColumns = {
			@JoinColumn(name = "language_id") })
	private Set<LanguageDB> languages;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "film_genres", joinColumns = { @JoinColumn(name = "film_id") }, inverseJoinColumns = {
			@JoinColumn(name = "genre_id") })
	private Set<GenreDB> genres;

	public FilmDB() {

	}
	
	public FilmDB(String name, Integer length, Integer year) {
		this.name = name;
		this.length = length;
		this.year = year;
	}

	public FilmDB(String name, Integer length, Integer year, Set<FilmCountryDB> filmCountryEntities, Set<LanguageDB> languages,
			Set<GenreDB> genres) {
		this(name, length, year);
		this.filmCountryEntities = filmCountryEntities;
		this.languages = languages;
		this.genres = genres;
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
		return new Film(id, name, length, year, entitiesToStringList(filmCountryEntities), setToStringList(languages),
				setToStringList(genres));
	}

	/**
	 * Parsing of a set of countries, genres or languages into a list of strings
	 * (names)
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param Set<?
	 *            extends DTO> input The set of DataTransferObjects (Genre,
	 *            Language or Country) to be parsed
	 * @return List<String> result The list of names of all items in the set
	 */
	private List<String> setToStringList(Set<? extends DTO> input) {
		List<String> result = new ArrayList<String>();

		for (DTO item : input) {
			result.add(item.getName());
		}

		return result;
	}
	
	/**
	 * Parse a set of join table entities into a string list for display in the table view
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param Set<FilmCountryDB> entities The set of join table entities
	 * @return List<String> result The sorted list of country names
	 */
	private List<String> entitiesToStringList(Set<FilmCountryDB> entities) {
		List<String> result = new ArrayList<String>();
		
		// for each entity, get the country name
		for(FilmCountryDB entity : entities) {
			result.add(entity.getCountryName());
		}
		
		// sort the string list alphabetically
		java.util.Collections.sort(result);
		
		return result;
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
	@pre filmCountryEntities!= null
	@post -
	@return the filmCountryEntities
	 */
	public Set<FilmCountryDB> getFilmCountryEntities() {
		return filmCountryEntities;
	}

	/**
	@pre -
	@post filmCountryEntities==filmCountryEntities
	@param filmCountryEntities the filmCountryEntities to set
	*/
	public void setFilmCountryEntities(Set<FilmCountryDB> filmCountryEntities) {
		this.filmCountryEntities = filmCountryEntities;
	}

	/**
	 * @pre languages!= null
	 * @post -
	 * @return the languages
	 */
	public Set<LanguageDB> getLanguages() {
		return languages;
	}

	/**
	 * @pre -
	 * @post languages==languages
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(Set<LanguageDB> languages) {
		this.languages = languages;
	}

	/**
	 * @pre genres!= null
	 * @post -
	 * @return the genres
	 */
	public Set<GenreDB> getGenres() {
		return genres;
	}

	/**
	 * @pre -
	 * @post genres==genres
	 * @param genres
	 *            the genres to set
	 */
	public void setGenres(Set<GenreDB> genres) {
		this.genres = genres;
	}
}
