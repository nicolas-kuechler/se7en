package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Container to hold information about a genre. Is used to transport the
 * genreData between client and server.
 * 
 * @author Nicolas Küchler, Roland Schläfli
 * 
 */
@Entity
@Table(name = "genres")
public class Genre implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Transient
	private int numberOfFilms;

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
	 * @pre numberOfFilms!= null
	 * @post -
	 * @return the numberOfFilms
	 */
	public int getNumberOfFilms() {
		return numberOfFilms;
	}

	/**
	 * @pre -
	 * @post numberOfFilms==numberOfFilms
	 * @param numberOfFilms
	 *            the numberOfFilms to set
	 */
	public void setNumberOfFilms(int numberOfFilms) {
		this.numberOfFilms = numberOfFilms;
	}
}
