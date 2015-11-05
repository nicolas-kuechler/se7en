package ch.uzh.se.se7en.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Composite key entity for the film-genre many-to-many relationship
 * 
 * @author Roland Schläfli
 *
 */
@Embeddable
public class FilmGenreDBId implements java.io.Serializable {
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private FilmDB film;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private GenreDB genre;
	
	public FilmGenreDBId() {
		
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
	@pre Genre!= null
	@post -
	@return the Genre
	 */
	public GenreDB getGenre() {
		return genre;
	}

	/**
	@pre -
	@post Genre==Genre
	@param Genre the Genre to set
	*/
	public void setGenre(GenreDB genre) {
		this.genre = genre;
	}
	
	
}
