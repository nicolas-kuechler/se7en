package ch.uzh.se.se7en.shared.model;

import java.util.HashMap;

/**
 * This class is used as a business object in the rpc used to fill the filter with
 * values to select from
 * @author Cyrill Halter
 *
 */
public class FilterOptions {

	private HashMap<Integer,String> genreSelectOptions;
	private HashMap<Integer,String> languageSelectOptions;
	private HashMap<Integer,String> countrySelectOptions;
	
	
	/**
	@pre genreSelectOptions!= null
	@post -
	@return the genreSelectOptions
	 */
	public HashMap<Integer, String> getGenreSelectOptions() {
		return genreSelectOptions;
	}
	/**
	@pre -
	@post genreSelectOptions==genreSelectOptions
	@param genreSelectOptions the genreSelectOptions to set
	*/
	public void setGenreSelectOptions(HashMap<Integer, String> genreSelectOptions) {
		this.genreSelectOptions = genreSelectOptions;
	}
	/**
	@pre languageSelectOptions!= null
	@post -
	@return the languageSelectOptions
	 */
	public HashMap<Integer, String> getLanguageSelectOptions() {
		return languageSelectOptions;
	}
	/**
	@pre -
	@post languageSelectOptions==languageSelectOptions
	@param languageSelectOptions the languageSelectOptions to set
	*/
	public void setLanguageSelectOptions(HashMap<Integer, String> languageSelectOptions) {
		this.languageSelectOptions = languageSelectOptions;
	}
	/**
	@pre countrySelectOptions!= null
	@post -
	@return the countrySelectOptions
	 */
	public HashMap<Integer, String> getCountrySelectOptions() {
		return countrySelectOptions;
	}
	/**
	@pre -
	@post countrySelectOptions==countrySelectOptions
	@param countrySelectOptions the countrySelectOptions to set
	*/
	public void setCountrySelectOptions(HashMap<Integer, String> countrySelectOptions) {
		this.countrySelectOptions = countrySelectOptions;
	}
}
