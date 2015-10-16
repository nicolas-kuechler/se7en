package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
import java.util.List;


/**
Container to transport the applied Filter from the client side to the server side.
Container for the applied filter
@author Nicolas Küchler
*/
public class FilmFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//WE MIGHT WANT TO USE DEFAULT VALUES TO INDICATE THAT A CERTAIN FIELD OF THE FILTER IS NOT SET
	private String name;
	private int lengthStart; 	//start point from the length range
	private int lengthEnd; 		//end point from the length range
	private int yearStart; 		//start point from the year range
	private int yearEnd; 		//end point from the year range
	private List<String> countries;
	private List<String> languages;
	private List<String> genres;
	
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
	@pre lengthStart!= null
	@post -
	@return the lengthStart
	 */
	public int getLengthStart() {
		return lengthStart;
	}
	
	/**
	@pre -
	@post lengthStart==lengthStart
	@param lengthStart the lengthStart to set
	*/
	public void setLengthStart(int lengthStart) {
		this.lengthStart = lengthStart;
	}
	
	/**
	@pre lengthEnd!= null
	@post -
	@return the lengthEnd
	 */
	public int getLengthEnd() {
		return lengthEnd;
	}
	
	/**
	@pre -
	@post lengthEnd==lengthEnd
	@param lengthEnd the lengthEnd to set
	*/
	public void setLengthEnd(int lengthEnd) {
		this.lengthEnd = lengthEnd;
	}
	
	/**
	@pre yearStart!= null
	@post -
	@return the yearStart
	 */
	public int getYearStart() {
		return yearStart;
	}
	
	/**
	@pre -
	@post yearStart==yearStart
	@param yearStart the yearStart to set
	*/
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}
	
	/**
	@pre yearEnd!= null
	@post -
	@return the yearEnd
	 */
	public int getYearEnd() {
		return yearEnd;
	}
	
	/**
	@pre -
	@post yearEnd==yearEnd
	@param yearEnd the yearEnd to set
	*/
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}
	
	/**
	@pre countries!= null
	@post -
	@return the countries
	 */
	public List<String> getCountries() {
		return countries;
	}
	
	/**
	@pre -
	@post countries==countries
	@param countries the countries to set
	*/
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
	
	/**
	@pre languages!= null
	@post -
	@return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}
	
	/**
	@pre -
	@post languages==languages
	@param languages the languages to set
	*/
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
	/**
	@pre genres!= null
	@post -
	@return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}
	
	/**
	@pre -
	@post genres==genres
	@param genres the genres to set
	*/
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

}
