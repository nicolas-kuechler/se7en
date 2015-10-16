package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;

/**
 Container to hold the numberOfFilms which were produced in each Country.
 Is used to transport this information from server to client side.
 @author Nicolas Küchler
 */
public class Country implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final int YEAR_OFFSET = 1900; //each index in numberOfFilms represents a year, 
								//but because there are no films before 1900 allowed, to save memory, there is a year offset.
	
	private String name;
	private String code; //2 letter iso country code
	
	private int[] numberOfFilms; //index 1 holds the numberOfFilms in the year 1900
									//index 2 holds the numberOfFilms from year 1900 too year 1901
	

	/**
	Calculates the number of films which were produced between two given years 
	(including the films from the two given years)
	@pre startYear >= 1900 && startYear <= currentYear && 
			startYear <=endYear && endYear >= 1900 && endYear <= currentYear
	@post -
	@param startYear the year from which the calculation begins (must be >= 1900 && <= currentYear)
	@param endYear the year where the calculation stops (must be >= 1900 && <= currentYear)
	@return the number of films produced 
	 */
	public int getNumberOfFilms(int startYear, int endYear)
	{
		//test if preconditions are true
		if(startYear>= YEAR_OFFSET && startYear <= numberOfFilms.length+YEAR_OFFSET-1 && //calculates the current year using the array length
				startYear <= endYear && endYear>= YEAR_OFFSET 
				&& endYear <= numberOfFilms.length+YEAR_OFFSET-1) //calculates the current year using the array length
		{
			return numberOfFilms[endYear-YEAR_OFFSET] - numberOfFilms[startYear-YEAR_OFFSET-1]; 
			//numberOfFilms up to the endYear - numberOfFilms up to the year before the startYear = numberOfFilms between start- and endYear
		}
		//preconditions not valid, so result is 0
		else
		{
			return 0;
		}
	}
	
	/**
	Set up for the NumberOfFilms attribute
	@pre filmsInEachYear.length == currentYear-YEAR_OFFSET 
	@post numberOfFilms holds in every index the numberOfFilms which were produced up to that year since YEAR_OFFSET
	@param filmsInEachYear in each field there is the number of films which were produced in that year (year calculation: index-YEAR_OFFSET)
	 */
	public void setNumberOfFilms(int[] filmsInEachYear)
	{
		numberOfFilms = new int[filmsInEachYear.length + 1]; //in filmsInEachYear year 1899 is not part of the array, therefore + 1
		numberOfFilms[0] = 0; //represents year 1899, and is used to prevent arrayIndexOutOfBounds exceptions
		
		for(int i = 1; i < numberOfFilms.length; i++)
		{
			numberOfFilms[i] = numberOfFilms[i-1] + filmsInEachYear[i-1]; //films up to the year before + films current year = films up to current year
		}
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
	@pre code!= null
	@post -
	@return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	@pre -
	@post code==code
	@param code the code to set
	*/
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
