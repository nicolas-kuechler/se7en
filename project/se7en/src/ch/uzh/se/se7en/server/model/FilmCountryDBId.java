package ch.uzh.se.se7en.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Composite key entity for the film-country many-to-many relationship
 * 
 * @author Roland Schläfli
 *
 */
@Embeddable
public class FilmCountryDBId implements java.io.Serializable {
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private FilmDB film;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CountryDB country;

	public FilmCountryDBId() {

	}

	/**
	 * @pre film!= null
	 * @post -
	 * @return the film
	 */
	public FilmDB getFilm() {
		return film;
	}

	/**
	 * @pre -
	 * @post film==film
	 * @param film
	 *            the film to set
	 */
	public void setFilm(FilmDB film) {
		this.film = film;
	}

	/**
	 * @pre country!= null
	 * @post -
	 * @return the country
	 */
	public CountryDB getCountry() {
		return country;
	}

	/**
	 * @pre -
	 * @post country==country
	 * @param country
	 *            the country to set
	 */
	public void setCountry(CountryDB country) {
		this.country = country;
	}

}
