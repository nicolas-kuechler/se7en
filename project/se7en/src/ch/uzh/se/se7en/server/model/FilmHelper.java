package ch.uzh.se.se7en.server.model;

import com.googlecode.jcsv.annotations.MapToColumn;

/***
 * This class is used to help with importing the csv file to java objects. Objects of this class
 * are later converted to real Film objects.
 * @author Cyrill Halter
 *
 */
public class FilmHelper{
	
	@MapToColumn(column = 0)
	public String name;
	
	@MapToColumn(column = 1)
	public Integer length;
	
	@MapToColumn(column = 2)
	public String countries;
	
	@MapToColumn(column = 3)
	public String languages;
	
	@MapToColumn(column = 4)
	public Integer year;
	
	@MapToColumn(column = 5)
	public String genres;
	
	public FilmHelper()
	{
		
	}
	

	
}