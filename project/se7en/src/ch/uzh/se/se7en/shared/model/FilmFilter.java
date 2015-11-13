package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Container to transport the applied Filter from the client side to the server
 * side. Container for the applied filter
 * 
 * @author Nicolas Küchler
 */
public class FilmFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int lengthStart = 0; // start point from the length range
	private int lengthEnd = 600; // end point from the length range
	private int yearStart = 1890; // start point from the year range
	private int yearEnd = 2015; // end point from the year range
	private Set<Integer> countryIds;
	private Set<Integer> languageIds;
	private Set<Integer> genreIds;

	public FilmFilter() {

	}

	public FilmFilter(String name) {
		this.name = name;
	}

	public FilmFilter(String name, int lengthStart, int lengthEnd, int yearStart, int yearEnd, Set<Integer> countryIds,
			Set<Integer> languageIds, Set<Integer> genreIds) {
		this.name = name;
		this.lengthStart = lengthStart;
		this.lengthEnd = lengthEnd;
		this.yearStart = yearStart;
		this.yearEnd = yearEnd;
		this.countryIds = countryIds;
		this.languageIds = languageIds;
		this.genreIds = genreIds;
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
	@pre countryIds!= null
	@post -
	@return the countryIds
	 */
	public Set<Integer> getCountryIds() {
		return countryIds;
	}

	/**
	@pre -
	@post countryIds==countryIds
	@param countryIds the countryIds to set
	*/
	public void setCountryIds(Set<Integer> countryIds) {
		this.countryIds = countryIds;
	}

	/**
	@pre languageIds!= null
	@post -
	@return the languageIds
	 */
	public Set<Integer> getLanguageIds() {
		return languageIds;
	}

	/**
	@pre -
	@post languageIds==languageIds
	@param languageIds the languageIds to set
	*/
	public void setLanguageIds(Set<Integer> languageIds) {
		this.languageIds = languageIds;
	}

	/**
	@pre genreIds!= null
	@post -
	@return the genreIds
	 */
	public Set<Integer> getGenreIds() {
		return genreIds;
	}

	/**
	@pre -
	@post genreIds==genreIds
	@param genreIds the genreIds to set
	*/
	public void setGenreIds(Set<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FilmFilter [name=" + name + ", lengthStart=" + lengthStart + ", lengthEnd=" + lengthEnd + ", yearStart="
				+ yearStart + ", yearEnd=" + yearEnd + ", countryIds=" + countryIds + ", languageIds=" + languageIds
				+ ", genreIds=" + genreIds + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FilmFilter))
			return false;
		FilmFilter other = (FilmFilter) obj;
		if (countryIds == null) {
			if (other.countryIds != null)
				return false;
		} else if (!countryIds.equals(other.countryIds))
			return false;
		if (genreIds == null) {
			if (other.genreIds != null)
				return false;
		} else if (!genreIds.equals(other.genreIds))
			return false;
		if (languageIds == null) {
			if (other.languageIds != null)
				return false;
		} else if (!languageIds.equals(other.languageIds))
			return false;
		if (lengthEnd != other.lengthEnd)
			return false;
		if (lengthStart != other.lengthStart)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (yearEnd != other.yearEnd)
			return false;
		if (yearStart != other.yearStart)
			return false;
		return true;
	}

	
}
