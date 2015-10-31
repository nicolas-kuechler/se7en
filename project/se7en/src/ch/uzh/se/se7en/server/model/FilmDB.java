package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ch.uzh.se.se7en.shared.model.DTO;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * Container for fetching all Country related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) 
@Table(name = "films")
public class FilmDB implements DTO {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "length", nullable=true)
	private Integer length;

	@Column(name = "year", nullable=true)
	private Integer year;
	
	@ManyToMany
	@JoinTable(name = "film_countries",
		joinColumns={@JoinColumn(name = "film_id")},
		inverseJoinColumns={@JoinColumn(name = "country_id")})
	private Set<CountryDB> countries;
	
	@ManyToMany
	@JoinTable(name = "film_languages",
		joinColumns={@JoinColumn(name = "film_id")},
		inverseJoinColumns={@JoinColumn(name = "language_id")})
	private Set<LanguageDB> languages;
	
	@ManyToMany
	@JoinTable(name = "film_genres",
		joinColumns={@JoinColumn(name = "film_id")},
		inverseJoinColumns={@JoinColumn(name = "genre_id")})
	private Set<GenreDB> genres;
	
	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Film The FilmDB entity converted to a film data transfer object
	 */
	public Film toFilm() {
		return new Film(id, name, length, year, setToStringList(countries), setToStringList(languages), setToStringList(genres));
	}
	
	/**
	 * Parsing of a set of countries, genres or languages into a list of strings (names)
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param Set<? extends DTO> input The set of DataTransferObjects (Genre, Language or Country) to be parsed
	 * @return List<String> result The list of names of all items in the set
	 */
	private List<String> setToStringList(Set<? extends DTO> input) {
		List<String> result = new ArrayList();
		
		for(DTO item : input) {
			result.add(item.getName());
		}
		
		return result;
	}

	/**
	@pre id!= null
	@post -
	@return the id
	 */
	public int getId() {
		return id;
	}

	/**
	@pre -
	@post id==id
	@param id the id to set
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	@pre name!= null
	@post -
	@return the name
	 */
	public String getName() {
		return name;
	}

	/**
	@pre -
	@post name==name
	@param name the name to set
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	@pre length!= null
	@post -
	@return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	@pre -
	@post length==length
	@param length the length to set
	*/
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	@pre year!= null
	@post -
	@return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	@pre -
	@post year==year
	@param year the year to set
	*/
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	@pre countries!= null
	@post -
	@return the countries
	 */
	public Set<CountryDB> getCountries() {
		return countries;
	}

	/**
	@pre -
	@post countries==countries
	@param countries the countries to set
	*/
	public void setCountries(Set<CountryDB> countries) {
		this.countries = countries;
	}

	/**
	@pre languages!= null
	@post -
	@return the languages
	 */
	public Set<LanguageDB> getLanguages() {
		return languages;
	}

	/**
	@pre -
	@post languages==languages
	@param languages the languages to set
	*/
	public void setLanguages(Set<LanguageDB> languages) {
		this.languages = languages;
	}

	/**
	@pre genres!= null
	@post -
	@return the genres
	 */
	public Set<GenreDB> getGenres() {
		return genres;
	}

	/**
	@pre -
	@post genres==genres
	@param genres the genres to set
	*/
	public void setGenres(Set<GenreDB> genres) {
		this.genres = genres;
	}
}
