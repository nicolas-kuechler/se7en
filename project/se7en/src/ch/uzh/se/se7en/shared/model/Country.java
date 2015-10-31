package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;

/**
 * Container to hold the numberOfFilms which were produced in each Country. Is
 * used to transport this information from server to client side.
 * 
 * @author Nicolas Küchler
 */
public class Country implements Serializable, DTO {

	private static final long serialVersionUID = 1L;

	public static final int YEAR_OFFSET = 1890; // each index in numberOfFilms
												// represents a year,
	// but because there are no films before 1890 allowed, to save memory, there
	// is a year offset.

	private int id;
	private String name;
	private String code; // 2 letter iso country code

	// index 1 holds the numberOfFilms in the year 1889
	// index 2 holds the numberOfFilms from year 1889 too year 1890
	private int[] numberOfFilms;

	public Country() {

	}

	public Country(String name) {
		this.name = name;
	}

	public Country(int id, String name, String code, int[] numberOfFilms) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.numberOfFilms = numberOfFilms;
	}
	
	/**
	 * Returns a string representation of this Country
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return String The string representation of this instance
	 */
	@Override
	public String toString() {
		return "Id: " + id + " - Name: " + name + " - Code: " + code + " - Anzahl Filme: " + numberOfFilms;
	}

	/**
	 * Calculates the number of films which were produced between two given
	 * years (including the films from the two given years)
	 * 
	 * @pre startYear >= 1900 && startYear <= currentYear && startYear <=endYear
	 *      && endYear >= 1900 && endYear <= currentYear
	 * @post -
	 * @param startYear
	 *            the year from which the calculation begins (must be >= 1900 &&
	 *            <= currentYear)
	 * @param endYear
	 *            the year where the calculation stops (must be >= 1900 && <=
	 *            currentYear)
	 * @return the number of films produced
	 */
	public int getNumberOfFilms(int startYear, int endYear) {
		// if any precondition fails, return 0 as a result
		if (startYear < YEAR_OFFSET || startYear > numberOfFilms.length + YEAR_OFFSET - 1 || startYear > endYear
				|| endYear < YEAR_OFFSET || endYear > numberOfFilms.length + YEAR_OFFSET - 1) {
			return 0;
		}

		// calculates the current year using the array length
		// numberOfFilms up to the endYear - numberOfFilms up to the year
		// before the startYear = numberOfFilms between start- and endYear
		return numberOfFilms[endYear - YEAR_OFFSET + 1] - numberOfFilms[startYear - YEAR_OFFSET];
	}

	/**
	 * Set up for the NumberOfFilms attribute
	 * 
	 * @pre filmsInEachYear.length == currentYear-YEAR_OFFSET
	 * @post numberOfFilms holds in every index the numberOfFilms which were
	 *       produced up to that year since YEAR_OFFSET
	 * @param filmsInEachYear
	 *            in each field there is the number of films which were produced
	 *            in that year (year calculation: index-YEAR_OFFSET)
	 */
	public void setNumberOfFilms(int[] filmsInEachYear) {
		// in filmsInEachYear year 1899 is not part of the array, therefore + 1
		numberOfFilms = new int[filmsInEachYear.length + 1];

		// represents year 1899, and is used to prevent arrayIndexOutOfBounds
		numberOfFilms[0] = 0;

		for (int i = 1; i < numberOfFilms.length; i++) {
			// films up to the year before + films current year = films up to
			// current year
			numberOfFilms[i] = numberOfFilms[i - 1] + filmsInEachYear[i - 1];
		}
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
	 * @pre code!= null
	 * @post -
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @pre -
	 * @post code==code
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
