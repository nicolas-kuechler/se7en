package ch.uzh.se.se7en.server.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ch.uzh.se.se7en.shared.model.Country;

/**
 * Container for fetching all Country related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Table(name = "countries")
public class CountryDB {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@OneToMany(mappedBy = "primaryKey.country", fetch = FetchType.LAZY)
	private List<FilmCountryDB> filmCountryEntities;

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
	 * @return Country The CountryDB entity converted to a country data transfer
	 *         object
	 */
	public Country toCountry() {
		return new Country(id, name, code);
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
	 * @pre code!= null
	 * @post -
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @pre -
	 * @post code==code
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @pre filmCountryEntities!= null
	 * @post -
	 * @return the filmCountryEntities
	 */
	public List<FilmCountryDB> getFilmCountryEntities() {
		return filmCountryEntities;
	}

	/**
	 * @pre -
	 * @post filmCountryEntities==filmCountryEntities
	 * @param filmCountryEntities
	 *            the filmCountryEntities to set
	 */
	public void setFilmCountryEntities(List<FilmCountryDB> filmCountryEntities) {
		this.filmCountryEntities = filmCountryEntities;
	}
}
