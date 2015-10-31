package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jcsv.annotations.MapToColumn;

/**
 * Container to hold the film data on client side and server side. Is used to
 * transport the movie data between client and server.
 * 
 * @author Nicolas Küchler, Roland Schläfli
 */
@Entity
@Table(name = "movies")
public class Film implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	@MapToColumn(column=0)
	private String name;

	@Column(name = "length", nullable=true)
	@MapToColumn(column=1)
	private Integer length;
	
	// TODO: will be in a different relation
	@Column(name = "country", nullable=true)
	@MapToColumn(column=2)
	private Set<String> countries;
		
	// TODO: will be in a different relation
	@Column(name = "language", nullable=true)
	@MapToColumn(column=3)
	private Set<String> languages;
	
	@Column(name = "year", nullable=true)
	@MapToColumn(column=4)
	private Integer year;
	
	// TODO: will be in a different relation
	@Column(name = "genre", nullable=true)
	@MapToColumn(column=5)
	private Set<String> genres;
	
	public Film()
	{
		
	}
	
	public Film(String name)
	{
		setName(name);
	}
	
	public Film(String name, int length, Set<String> countries, Set<String> languages, int year, Set<String> genres) 
	{
		this.name = name;
		this.length = length;
		this.countries = countries;
		this.languages = languages;
		this.year = year;
		this.genres = genres;
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
	public Set<String> getCountries() {
		return countries;
	}

	/**
	@pre -
	@post countries==countries
	@param countries the countries to set
	*/
	public void setCountries(Set<String> countries) {
		this.countries = countries;
	}

	/**
	@pre languages!= null
	@post -
	@return the languages
	 */
	public Set<String> getLanguages() {
		return languages;
	}

	/**
	@pre -
	@post languages==languages
	@param languages the languages to set
	*/
	public void setLanguages(Set<String> languages) {
		this.languages = languages;
	}

	/**
	@pre genres!= null
	@post -
	@return the genres
	 */
	public Set<String> getGenres() {
		return genres;
	}

	/**
	@pre -
	@post genres==genres
	@param genres the genres to set
	*/
	public void setGenre(Set<String> genres) {
		this.genres = genres;
	}
}
