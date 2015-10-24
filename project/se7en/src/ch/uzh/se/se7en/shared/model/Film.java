package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;

/**
Container to hold the film data on client side and server side.
Is used to transport the filmdata between client and server.
@author Nicolas Küchler
*/
public class Film implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int length;
	private String country; //if there are more than one country: country1 - country2
	private String language; //if there are more than one language: language1 - language2
	private int year;
	private String genre; //if there are more than one genre: genre1 - genre2
	
	public Film()
	{
		
	}
	
	public Film(String name)
	{
		setName(name);
	}
	
	
	
	public Film(String name, int length, String country, String language, int year, String genre) 
	{
		this.name = name;
		this.length = length;
		this.country = country;
		this.language = language;
		this.year = year;
		this.genre = genre;
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
	@pre length!= null
	@post -
	@return the length
	 */
	public int getLength() {
		return length;
	}
	
	/**
	@pre -
	@post length==length
	@param length the length to set
	*/
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	@pre country!= null
	@post -
	@return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	@pre -
	@post country==country
	@param country the country to set
	*/
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	@pre language!= null
	@post -
	@return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	@pre -
	@post language==language
	@param language the language to set
	*/
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	@pre year!= null
	@post -
	@return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	@pre -
	@post year==year
	@param year the year to set
	*/
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	@pre genre!= null
	@post -
	@return the genre
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	@pre -
	@post genre==genre
	@param genre the genre to set
	*/
	public void setGenre(String genre) {
		this.genre = genre;
	}


}
