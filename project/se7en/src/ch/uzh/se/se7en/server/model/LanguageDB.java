package ch.uzh.se.se7en.server.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ch.uzh.se.se7en.shared.model.DTO;
import ch.uzh.se.se7en.shared.model.Language;

/**
 * Container for fetching all Language related data from the DB via hibernate.
 * 
 * @author Roland Schläfli
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) 
@Table(name = "languages")
public class LanguageDB implements DTO {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;
	
	@ManyToMany(targetEntity = FilmDB.class)
	private Set<FilmDB> films;
	
	/**
	 * Converts this entity to a data transfer object
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Language The LanguageDB entity converted to a language data transfer object
	 */
	public Language toLanguage() {		
		int numberOfFilms = films.size();
		
		return new Language(id, name, numberOfFilms);
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
