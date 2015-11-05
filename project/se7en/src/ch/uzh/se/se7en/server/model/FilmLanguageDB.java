package ch.uzh.se.se7en.server.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A join table entity that links films and languages
 * 
 * @author Roland Schläfli
 *
 */
@Entity
@Table(name = "film_languages")
public class FilmLanguageDB {
	// create a composite primary key for this join table entity
	@EmbeddedId
	private FilmLanguageDBId primaryKey = new FilmLanguageDBId();
	
	// read only copy of the film id
	@Column(name = "film_id", insertable = false, updatable = false)
	private int filmId;
	
	// read only copy of the Language id
	@Column(name = "language_id", insertable = false, updatable = false)
	private int languageId;
	
	public FilmLanguageDB() {
		
	}
	
	public FilmLanguageDB(FilmDB film, LanguageDB language) {
		this.setFilm(film);
		this.setLanguage(language);
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
	 * Returns the associated language entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return LanguageDB The associated language entity
	 */
	@Transient
	public LanguageDB getLanguage() {
		return getPrimaryKey().getLanguage();
	}
	
	/**
	 * Returns the name of the associated language entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The name of the associated language entity
	 */
	@Transient
	public String getLanguageName() {
		return this.getLanguage().getName();
	}
	
	/**
	 * Sets the associated language entity
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param LanguageDB The newly associated language entity
	 */
	public void setLanguage(LanguageDB language) {
		getPrimaryKey().setLanguage(language);
	}

	/**
	@pre primaryKey!= null
	@post -
	@return the primaryKey
	 */
	public FilmLanguageDBId getPrimaryKey() {
		return primaryKey;
	}

	/**
	@pre -
	@post primaryKey==primaryKey
	@param primaryKey the primaryKey to set
	*/
	public void setPrimaryKey(FilmLanguageDBId primaryKey) {
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
	@pre languageId!= null
	@post -
	@return the languageId
	 */
	public int getLanguageId() {
		return languageId;
	}

	/**
	@pre -
	@post languageId==languageId
	@param languageId the languageId to set
	*/
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
}
