package ch.uzh.se.se7en.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class CountryYearCountDBId implements Serializable {
	@Column(name = "name")
	public String name;
	
	@Column(name = "year")
	public Integer year;

	@Column(name = "count")
	public int count;

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
	@pre year!= null
	@post -
	@return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	@pre -
	@post year==year
	@param year the year to set
	*/
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	@pre count!= null
	@post -
	@return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	@pre -
	@post count==count
	@param count the count to set
	*/
	public void setCount(int count) {
		this.count = count;
	}
}
