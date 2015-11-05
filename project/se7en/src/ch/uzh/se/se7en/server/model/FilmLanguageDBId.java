package ch.uzh.se.se7en.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Composite key entity for the film-language many-to-many relationship
 * 
 * @author Roland Schläfli
 *
 */
@Embeddable
public class FilmLanguageDBId implements java.io.Serializable {
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private FilmDB film;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private LanguageDB language;
	
	public FilmLanguageDBId() {
		
	}

	/**
	@pre film!= null
	@post -
	@return the film
	 */
	public FilmDB getFilm() {
		return film;
	}

	/**
	@pre -
	@post film==film
	@param film the film to set
	*/
	public void setFilm(FilmDB film) {
		this.film = film;
	}

	/**
	@pre language!= null
	@post -
	@return the language
	 */
	public LanguageDB getLanguage() {
		return language;
	}

	/**
	@pre -
	@post language==language
	@param language the language to set
	*/
	public void setLanguage(LanguageDB language) {
		this.language = language;
	}
	
	
}
