package ch.uzh.se.se7en.server.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CountryYearCountDB {
	@EmbeddedId
	public CountryYearCountDBId countryYearCount;

	/**
	@pre countryYearCount!= null
	@post -
	@return the countryYearCount
	 */
	public CountryYearCountDBId getCountryYearCount() {
		return countryYearCount;
	}

	/**
	@pre -
	@post countryYearCount==countryYearCount
	@param countryYearCount the countryYearCount to set
	*/
	public void setCountryYearCount(CountryYearCountDBId countryYearCount) {
		this.countryYearCount = countryYearCount;
	}
}
