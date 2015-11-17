package ch.uzh.se.se7en.client.mvp.model;

import java.util.HashMap;
import java.util.List;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public interface FilmDataModel {
	/**
	Setter method to store the country list in the filmDataModel
	@author Nicolas Küchler
	@pre	-
	@post	this.countries == countries
	@param	A list of country objects
	 */
	public void setCountryList(List<Country> countries);
	
	/**
	Getter method to get the currently valid countrylist.
	@author Nicolas Küchler
	@pre 	countries != null
	@post	countries == countries @pre
	@return A List of Country objects
	 */
	public List<Country> getCountryList();
	
	/**
	Setter method to store the currently applied filter in the client side data model
	@author Nicolas Küchler
	@pre	-
	@post	this.appliedFilter == filter
	@param	filter: The filter object which is currently applied on the client side
	 */
	public void setAppliedFilter(FilmFilter filter);
	
	/**
	Getter method to get the currently applied filter.
	@author Nicolas Küchler
	@pre	this.appliedFilter != null
	@post	this.appliedFilter == this.appliedFilter @pre
	@return The FilmFilter object which is currently applied
	 */
	public FilmFilter getAppliedFilter();
	
	/**
	Setter method to store the currently applied map filter in the client side data model
	@author Nicolas Küchler
	@pre	-
	@post	this.appliedMapFilter == filter
	@param	filter: The filter object which is currently applied on the map.
	 */
	public void setAppliedMapFilter(FilmFilter filter);
	
	/**
	Getter method to get the currently applied filter on the map.
	@author Nicolas Küchler
	@pre	this.appliedMapFilter != null
	@post	this.appliedMapFilter == this.appliedMapFilter @pre
	@return The FilmFilter object which is currently applied on the map.
	 */
	public FilmFilter getAppliedMapFilter();
	
	/**
	Setter Method that stores the available Countries from the DB on the client side. 
	@author Nicolas Küchler
	@pre	-
	@post	selectOptions are stored in countryNames
	@param	A list of selectOptions where all ids from minId to maxId are included without a gap.
	 */
	public void setCountryOptions(HashMap<Integer,String> selectOptions);
	
	/**
	Allows to match the id to the name of a country.
	@author Nicolas Küchler
	@pre	countryNames != null
	@post	countryNames == countryNames @pre
	@param	id: the id of a country that is in the DB
	@return	the name of the country with the given id
	 */
	public String getCountryName(int id);
	
	/**
	Setter Method that stores the available Genres from the DB on the client side. 
	@author Nicolas Küchler
	@pre	-
	@post	selectOptions are stored in genreNames
	@param	A list of selectOptions where all ids from minId to maxId are included without a gap.
	 */
	public void setGenreOptions(HashMap<Integer,String> selectOptions);
	
	/**
	Allows to match the id to the name of a genre.
	@author Nicolas Küchler
	@pre	genreNames != null
	@post	genreNames == genreNames @pre
	@param	id: the id of a genre that is in the DB
	@return	the name of the genre with the given id
	 */
	public String getGenreName(int id);
	
	/**
	Setter Method that stores the available Languages from the DB on the client side. 
	@author Nicolas Küchler
	@pre	-
	@post	selectOptions are stored in languageNames
	@param	A list of selectOptions where all ids from minId to maxId are included without a gap.
	 */
	public void setLanguageOptions(HashMap<Integer,String> selectOptions);
	
	/**
	Allows to match the id to the name of a language.
	@author Nicolas Küchler
	@pre	languageNames != null
	@post	languageNames == languageNames @pre
	@param	id: the id of a language that is in the DB
	@return	the name of the language with the given id
	 */
	public String getLanguageName(int id);
}
