package ch.uzh.se.se7en.server.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A join table entity that links films and genres
 * 
 * @author Roland Schläfli
 *
 */
@Entity
@Table(name = "film_genres")
public class FilmGenreDB {
	// create a composite primary key for this join table entity
	@EmbeddedId
	private FilmGenreDBId primaryKey = new FilmGenreDBId();
	
	// read only copy of the film id
	@Column(name = "film_id", insertable = false, updatable = false)
	private int filmId;
	
	// read only copy of the genre id
	@Column(name = "genre_id", insertable = false, updatable = false)
	private int GenreId;
	
	public FilmGenreDB() {
		
	}
	
	public FilmGenreDB(FilmDB film, GenreDB Genre) {
		this.setFilm(film);
		this.setGenre(Genre);
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
	 * Returns the associated Genre entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return GenreDB The associated Genre entity
	 */
	@Transient
	public GenreDB getGenre() {
		return getPrimaryKey().getGenre();
	}
	
	/**
	 * Returns the name of the associated Genre entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The name of the associated Genre entity
	 */
	@Transient
	public String getGenreName() {
		return this.getGenre().getName();
	}
	
	/**
	 * Sets the associated Genre entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param GenreDB The newly associated Genre entity
	 */
	public void setGenre(GenreDB Genre) {
		getPrimaryKey().setGenre(Genre);
	}

	/**
	@pre primaryKey!= null
	@post -
	@return the primaryKey
	 */
	public FilmGenreDBId getPrimaryKey() {
		return primaryKey;
	}

	/**
	@pre -
	@post primaryKey==primaryKey
	@param primaryKey the primaryKey to set
	*/
	public void setPrimaryKey(FilmGenreDBId primaryKey) {
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
	@pre GenreId!= null
	@post -
	@return the GenreId
	 */
	public int getGenreId() {
		return GenreId;
	}

	/**
	@pre -
	@post GenreId==GenreId
	@param GenreId the GenreId to set
	*/
	public void setGenreId(int GenreId) {
		this.GenreId = GenreId;
	}
}
