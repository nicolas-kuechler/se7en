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

import ch.uzh.se.se7en.shared.model.Genre;

/**
 * Container for fetching all Genre related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 * 
 */
@Entity
@Table(name = "genres")
public class GenreDB {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "primaryKey.genre", fetch = FetchType.LAZY)
	private List<FilmGenreDB> filmGenreEntities;

	public GenreDB() {

	}

	public GenreDB(String name) {
		this.name = name;
	}

	public GenreDB(int id) {
		this.id = id;
	}

	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Genre The GenreDB entity converted to a genre data transfer
	 *         object
	 */
	public Genre toGenre() {
		int numberOfFilms = filmGenreEntities.size();

		return new Genre(id, name, numberOfFilms);
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
	@pre filmGenreEntities!= null
	@post -
	@return the filmGenreEntities
	 */
	public List<FilmGenreDB> getFilmGenreEntities() {
		return filmGenreEntities;
	}

	/**
	@pre -
	@post filmGenreEntities==filmGenreEntities
	@param filmGenreEntities the filmGenreEntities to set
	*/
	public void setFilmGenreEntities(List<FilmGenreDB> filmGenreEntities) {
		this.filmGenreEntities = filmGenreEntities;
	}
}
