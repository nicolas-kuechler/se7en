package ch.uzh.se.se7en.server.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ch.uzh.se.se7en.shared.model.Language;

/**
 * Container for fetching all Language related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Table(name = "languages")
public class LanguageDB {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "primaryKey.language", fetch = FetchType.LAZY)
	private List<FilmLanguageDB> filmLanguageEntities;

	public LanguageDB() {

	}

	public LanguageDB(String name) {
		this.name = name;
	}

	public LanguageDB(int id) {
		this.id = id;
	}

	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Language The LanguageDB entity converted to a language data
	 *         transfer object
	 */
	public Language toLanguage() {
		int numberOfFilms = filmLanguageEntities.size();

		return new Language(id, name, numberOfFilms);
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
	@pre filmLanguageEntities!= null
	@post -
	@return the filmLanguageEntities
	 */
	public List<FilmLanguageDB> getFilmLanguageEntities() {
		return filmLanguageEntities;
	}

	/**
	@pre -
	@post filmLanguageEntities==filmLanguageEntities
	@param filmLanguageEntities the filmLanguageEntities to set
	*/
	public void setFilmLanguageEntities(List<FilmLanguageDB> filmLanguageEntities) {
		this.filmLanguageEntities = filmLanguageEntities;
	}
}
