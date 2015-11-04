package ch.uzh.se.se7en.server.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A join table entity that links films and countries
 * 
 * @author Roland Schläfli
 *
 */
@Entity
@Table(name = "film_countries")
public class FilmCountryDB {
	// create a composite primary key for this join table entity
	@EmbeddedId
	private FilmCountryDBId primaryKey = new FilmCountryDBId();
	
	// read only copy of the film id
	@Column(name = "film_id", insertable = false, updatable = false)
	private int filmId;
	
	// read only copy of the country id
	@Column(name = "country_id", insertable = false, updatable = false)
	private int countryId;
	
	public FilmCountryDB() {
		
	}
	
	public FilmCountryDB(FilmDB film, CountryDB country) {
		this.setFilm(film);
		this.setCountry(country);
	}
	
	/**
	 * Returns the associated film entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return FilmDB The associated film entity
	 */
	@Transient
	public FilmDB getFilm() {
		return getPrimaryKey().getFilm();
	}
	
	/**
	 * Returns the name of the associated film entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The name of the associated film entity
	 */
	@Transient
	public String getFilmName() {
		return this.getFilm().getName();
	}
	
	/**
	 * Sets the associated film entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmDB film The newly associated film entity
	 */
	public void setFilm(FilmDB film) {
		getPrimaryKey().setFilm(film);
	}
	
	/**
	 * Returns the associated country entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return CountryDB The associated country entity
	 */
	@Transient
	public CountryDB getCountry() {
		return getPrimaryKey().getCountry();
	}
	
	/**
	 * Returns the name of the associated country entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The name of the associated country entity
	 */
	@Transient
	public String getCountryName() {
		return this.getCountry().getName();
	}
	
	/**
	 * Sets the associated country entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param CountryDB The newly associated country entity
	 */
	public void setCountry(CountryDB country) {
		getPrimaryKey().setCountry(country);
	}

	/**
	@pre primaryKey!= null
	@post -
	@return the primaryKey
	 */
	public FilmCountryDBId getPrimaryKey() {
		return primaryKey;
	}

	/**
	@pre -
	@post primaryKey==primaryKey
	@param primaryKey the primaryKey to set
	*/
	public void setPrimaryKey(FilmCountryDBId primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	@pre filmId!= null
	@post -
	@return the filmId
	 */
	public int getFilmId() {
		return filmId;
	}

	/**
	@pre -
	@post filmId==filmId
	@param filmId the filmId to set
	*/
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	/**
	@pre countryId!= null
	@post -
	@return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	@pre -
	@post countryId==countryId
	@param countryId the countryId to set
	*/
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
}
