package ch.uzh.se.se7en.server.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ch.uzh.se.se7en.shared.model.DTO;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.Genre;

/**
 * Container for fetching all Genre related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 * 
 */
@Entity
@Table(name = "genres")
public class GenreDB implements DTO {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;
	
	@ManyToMany(targetEntity = FilmDB.class)
	private Set<FilmDB> films;
	
	public GenreDB(String name) {
		this.name = name;
	}
	
	public GenreDB(int id, String name) {
		this(name);
		
		this.id = id;
	}
	
	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Genre The GenreDB entity converted to a genre data transfer object
	 */
	public Genre toGenre() {
		int numberOfFilms = films.size();
		
		return new Genre(id, name, numberOfFilms);
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
	@pre films!= null
	@post -
	@return the films
	 */
	public Set<FilmDB> getFilms() {
		return films;
	}

	/**
	@pre -
	@post films==films
	@param films the films to set
	*/
	public void setFilms(Set<FilmDB> films) {
		this.films = films;
	}
}
