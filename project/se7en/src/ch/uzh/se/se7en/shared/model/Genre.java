package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
/**
 Container to hold information about a genre.
 Is used to transport the genreData between client and server.
@author Nicolas Küchler

*/
public class Genre implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int numberOfFilms;
	
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
	@pre numberOfFilms!= null
	@post -
	@return the numberOfFilms
	 */
	public int getNumberOfFilms() {
		return numberOfFilms;
	}
	
	/**
	@pre -
	@post numberOfFilms==numberOfFilms
	@param numberOfFilms the numberOfFilms to set
	*/
	public void setNumberOfFilms(int numberOfFilms) {
		this.numberOfFilms = numberOfFilms;
	}	

}
