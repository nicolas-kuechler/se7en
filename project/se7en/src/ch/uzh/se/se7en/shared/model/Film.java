package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private String name;
	
	@Column(name = "length", nullable=true)
	private Integer length;
	
	@Column(name = "year", nullable=true)
	private Integer year;
	
	// TODO: will be in a different relation
	@Column(name = "country", nullable=true)
	private String country;
	
	// TODO: will be in a different relation
	@Column(name = "language", nullable=true)
	private String language;
	
	// TODO: will be in a different relation
	@Column(name = "genre", nullable=true)
	private String genre;

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
	@pre country!= null
	@post -
	@return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	@pre -
	@post country==country
	@param country the country to set
	*/
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	@pre language!= null
	@post -
	@return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	@pre -
	@post language==language
	@param language the language to set
	*/
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	@pre genre!= null
	@post -
	@return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	@pre -
	@post genre==genre
	@param genre the genre to set
	*/
	public void setGenre(String genre) {
		this.genre = genre;
	}
}
