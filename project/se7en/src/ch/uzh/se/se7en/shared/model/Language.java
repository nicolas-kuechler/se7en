package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;

/**
 * Container to hold information about a language
 * 
 * @author Roland Schläfli
 */
public class Language implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int numberOfFilms;

	public Language() {

	}

	public Language(int id, String name, int numberOfFilms) {
		this.id = id;
		this.name = name;
		this.numberOfFilms = numberOfFilms;
	}

	/**
	 * Returns a string representation of this Language
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The string representation of this instance
	 */
	@Override
	public String toString() {
		return "Id: " + id + " - Name: " + name + " - Anzahl Filme: " + numberOfFilms;
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
}
