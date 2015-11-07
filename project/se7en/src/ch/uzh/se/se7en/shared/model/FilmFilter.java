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

	// TODO WE MIGHT WANT TO USE DEFAULT VALUES TO INDICATE THAT A CERTAIN FIELD
	// OF THE FILTER IS NOT SET
	private String name;
	private int lengthStart = 0; // start point from the length range
	private int lengthEnd = 600; // end point from the length range
	private int yearStart = 1890; // start point from the year range
	private int yearEnd = Calendar.getInstance().get(Calendar.YEAR); // end point from the year range
	private List<String> countries;
	private Set<Integer> countryIds;
	private List<String> languages;
	private Set<Integer> languageIds;
	private List<String> genres;
	private Set<Integer> genreIds;

	public FilmFilter() {

	}

	public FilmFilter(String name) {
		this.name = name;
	}

	public FilmFilter(String name, int lengthStart, int lengthEnd, int yearStart, int yearEnd, List<String> countries,
			List<String> languages, List<String> genres) {
		this.name = name;
		this.lengthStart = lengthStart;
		this.lengthEnd = lengthEnd;
		this.yearStart = yearStart;
		this.yearEnd = yearEnd;
		this.countries = countries;
		this.languages = languages;
		this.genres = genres;
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
	 * @pre lengthStart!= null
	 * @post -
	 * @return the lengthStart
	 */
	public int getLengthStart() {
		return lengthStart;
	}

	/**
	 * @pre -
	 * @post lengthStart==lengthStart
	 * @param lengthStart
	 *            the lengthStart to set
	 */
	public void setLengthStart(int lengthStart) {
		this.lengthStart = lengthStart;
	}

	/**
	 * @pre lengthEnd!= null
	 * @post -
	 * @return the lengthEnd
	 */
	public int getLengthEnd() {
		return lengthEnd;
	}

	/**
	 * @pre -
	 * @post lengthEnd==lengthEnd
	 * @param lengthEnd
	 *            the lengthEnd to set
	 */
	public void setLengthEnd(int lengthEnd) {
		this.lengthEnd = lengthEnd;
	}

	/**
	 * @pre yearStart!= null
	 * @post -
	 * @return the yearStart
	 */
	public int getYearStart() {
		return yearStart;
	}

	/**
	 * @pre -
	 * @post yearStart==yearStart
	 * @param yearStart
	 *            the yearStart to set
	 */
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}

	/**
	 * @pre yearEnd!= null
	 * @post -
	 * @return the yearEnd
	 */
	public int getYearEnd() {
		return yearEnd;
	}

	/**
	 * @pre -
	 * @post yearEnd==yearEnd
	 * @param yearEnd
	 *            the yearEnd to set
	 */
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}

	/**
	 * @pre countries!= null
	 * @post -
	 * @return the countries
	 */
	public List<String> getCountries() {
		return countries;
	}

	/**
	 * @pre -
	 * @post countries==countries
	 * @param countries
	 *            the countries to set
	 */
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	/**
	 * Sets the country name and id according to a list of Select Option.
	 * 
	 * @pre
	 * @post this.countries == List<selectOption>.getName() && this.countryIds
	 *       == List<selectOption>.getId();
	 * @param selectOptions
	 *            which should be set as a filter attribute to the FilmFilter
	 *            object
	 */
	public void setCountryOptions(List<SelectOption> selectOptions) {
		List<String> countryName = new ArrayList<String>(selectOptions.size());

		Set<Integer> countryId = new HashSet<Integer>();

		for (SelectOption option : selectOptions) {
			countryId.add(option.getId());
			countryName.add(option.getName());
		}

		setCountries(countryName);
		setCountryIds(countryId);
	}

	/**
	 * Sets the genre name and id according to a list of Select Option.
	 * 
	 * @pre
	 * @post this.genres == List<selectOption>.getName() && this.genreIds ==
	 *       List<selectOption>.getId();
	 * @param selectOptions
	 *            which should be set as a filter attribute to the FilmFilter
	 *            object
	 */
	public void setGenreOptions(List<SelectOption> selectOptions) {
		List<String> genreName = new ArrayList<String>(selectOptions.size());

		Set<Integer> genreId = new HashSet<Integer>();

		for (SelectOption option : selectOptions) {
			genreId.add(option.getId());
			genreName.add(option.getName());
		}

		setGenres(genreName);
		setGenreIds(genreId);
	}

	/**
	 * Sets the language name and id according to a list of Select Option.
	 * 
	 * @pre
	 * @post this.languages == List<selectOption>.getName() && this.languageIds
	 *       == List<selectOption>.getId();
	 * @param selectOptions
	 *            which should be set as a filter attribute to the FilmFilter
	 *            object
	 */
	public void setLanguageOptions(List<SelectOption> selectOptions) {
		List<String> languageName = new ArrayList<String>(selectOptions.size());

		Set<Integer> languageId = new HashSet<Integer>();

		for (SelectOption option : selectOptions) {
			languageId.add(option.getId());
			languageName.add(option.getName());
		}

		setLanguages(languageName);
		setLanguageIds(languageId);
	}

	/**
	 * @pre languages!= null
	 * @post -
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/**
	 * @pre -
	 * @post languages==languages
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	/**
	 * @pre genres!= null
	 * @post -
	 * @return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * @pre -
	 * @post genres==genres
	 * @param genres
	 *            the genres to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	/**
	 * @pre countryIds!= null
	 * @post -
	 * @return the countryIds
	 */
	public Set<Integer> getCountryIds() {
		return countryIds;
	}

	/**
	 * @pre -
	 * @post countryIds==countryIds
	 * @param countryIds
	 *            the countryIds to set
	 */
	public void setCountryIds(Set<Integer> countryIds) {
		this.countryIds = countryIds;
	}

	/**
	 * @pre languageIds!= null
	 * @post -
	 * @return the languageIds
	 */
	public Set<Integer> getLanguageIds() {
		return languageIds;
	}

	/**
	 * @pre -
	 * @post languageIds==languageIds
	 * @param languageIds
	 *            the languageIds to set
	 */
	public void setLanguageIds(Set<Integer> languageIds) {
		this.languageIds = languageIds;
	}

	/**
	 * @pre genreIds!= null
	 * @post -
	 * @return the genreIds
	 */
	public Set<Integer> getGenreIds() {
		return genreIds;
	}

	/**
	 * @pre -
	 * @post genreIds==genreIds
	 * @param genreIds
	 *            the genreIds to set
	 */
	public void setGenreIds(Set<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FilmFilter [name=" + name + ", lengthStart=" + lengthStart + ", lengthEnd=" + lengthEnd + ", yearStart="
				+ yearStart + ", yearEnd=" + yearEnd + ", countries=" + countries + ", languages=" + languages
				+ ", genres=" + genres + "]";
	}

}
