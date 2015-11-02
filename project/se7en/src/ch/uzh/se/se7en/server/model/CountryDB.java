package ch.uzh.se.se7en.server.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.DTO;

/**
 * Container for fetching all Country related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Table(name = "countries")
public class CountryDB implements DTO {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;
	
	@ManyToMany(targetEntity = FilmDB.class)
	@JoinTable(name = "film_countries",
		joinColumns={@JoinColumn(name = "country_id")},
		inverseJoinColumns={@JoinColumn(name = "film_id")})
	private List<FilmDB> films;
	
	public CountryDB() {
		
	}
	
	public CountryDB(int id) {
		this.id = id;
	}
	
	public CountryDB(String name) {
		this.name = name;
	}
	
	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @pre -
	 * @post -
	 * @return Country The CountryDB entity converted to a country data transfer object
	 */
	public Country toCountry() {
		return new Country(id, name, code);
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
	@pre code!= null
	@post -
	@return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	@pre -
	@post code==code
	@param code the code to set
	*/
	public void setCode(String code) {
		this.code = code;
	}

	/**
	@pre films!= null
	@post -
	@return the films
	 */
	public List<FilmDB> getFilms() {
		return films;
	}

	/**
	@pre -
	@post films==films
	@param films the films to set
	*/
	public void setFilms(List<FilmDB> films) {
		this.films = films;
	}
}
