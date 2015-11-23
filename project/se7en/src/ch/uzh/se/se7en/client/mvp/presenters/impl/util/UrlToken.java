package ch.uzh.se.se7en.client.mvp.presenters.impl.util;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;

import ch.uzh.se.se7en.shared.model.FilmFilter;

public class UrlToken {
	
	BrowserUtil browserUtil;
	
	@Inject
	public UrlToken(BrowserUtil browserUtil)
	{
		this.browserUtil = browserUtil;
	}
	
	/**
	Method to create an Url Token representing the filter given as an argument.
	@author Nicolas Küchler
	@pre -
	@post -
	@param filter a FilmFilter object that should pe parsed to a token
	@param autoSearch defines if the autoSearch flag in the token should be set
	@return	the token representing the given FilmFilter
	 */ 
	public String createUrlToken(FilmFilter filter, boolean autoSearch)
	{
		String token = "?sb=";

		//defines if the search is automatically started or if just the filterfields are filled
		if(autoSearch)
		{
			token += "1";
		}
		else
		{
			token += "0";
		}

		//Name --> need to encode due to possible occurrence of & in search
		if(filter.getName()!=null)
		{
			token+= "&na="+ browserUtil.encode(filter.getName());
		}

		//Length
		token+= "&le="+filter.getLengthStart()+":"+filter.getLengthEnd();

		//Year
		token+= "&ye="+filter.getYearStart()+":"+filter.getYearEnd();

		//Genre
		Set<Integer> genres = filter.getGenreIds();
		if(genres!=null && genres.size()>0)
		{
			Integer[] ids = new Integer[genres.size()];
			ids = genres.toArray(ids);
			token += "&ge="+ids[0];
			for (int i = 1; i < ids.length; i++)
			{
				token += ":"+ids[i];
			}
		}

		//Language
		Set<Integer> languages = filter.getLanguageIds();
		if(languages!=null && languages.size()>0)
		{
			Integer[] ids = new Integer[languages.size()];
			ids = languages.toArray(ids);
			token += "&la="+ids[0];
			for (int i = 1; i < ids.length; i++)
			{
				token += ":"+ids[i];
			}
		}

		//Country
		Set<Integer> countries = filter.getCountryIds();
		if(countries!=null && countries.size()>0)
		{
			Integer[] ids = new Integer[countries.size()];
			ids = countries.toArray(ids);
			token += "&co="+ids[0];
			for (int i = 1; i < ids.length; i++)
			{
				token += ":"+ids[i];
			}
		}

		return token;
	}

	/**
	Creates a FilmFilter object from a given Url Token
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param 	urlToken needs to match the defined structure.
	@return	FilmFilter object with the fields set according to the urlToken
	 */
	public FilmFilter parseFilter(String urlToken)
	{
		FilmFilter filter = new FilmFilter();
		
		String[] parts = urlToken.substring(1).split("&");
		String fieldId ="";
		String value ="";
		for(String part : parts)
		{
			fieldId = part.substring(0, 2);
			value = part.substring(3);
			switch(fieldId){
			case "na":
				filter.setName(browserUtil.decode(value));
				break;

			case "le":
				String[] length = value.split(":");
				filter.setLengthStart(Integer.parseInt(length[0]));
				filter.setLengthEnd(Integer.parseInt(length[1]));
				break;

			case "ye":
				String[] year = value.split(":");
				
				filter.setYearStart(Integer.parseInt(year[0]));
				filter.setYearEnd(Integer.parseInt(year[1]));
				break;

			case "ge":
				String[] genre = value.split(":");
				Set<Integer> genreIds = new HashSet<Integer>();
				for(String g : genre)
				{
					genreIds.add(Integer.parseInt(g));
				}
				filter.setGenreIds(genreIds);
				break;

			case "la":
				String[] language = value.split(":");
				Set<Integer> languageIds = new HashSet<Integer>();
				for(String l : language)
				{
					languageIds.add(Integer.parseInt(l));
				}
				filter.setLanguageIds(languageIds);
				break;

			case "co":
				String[] country = value.split(":");
				Set<Integer> countryIds = new HashSet<Integer>();
				for(String c : country)
				{
					countryIds.add(Integer.parseInt(c));
				}
				filter.setCountryIds(countryIds);
				break;
			default:
				break;
			}
		}
		return filter;
	}
}
