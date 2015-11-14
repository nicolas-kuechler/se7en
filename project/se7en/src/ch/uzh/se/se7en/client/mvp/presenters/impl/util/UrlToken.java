package ch.uzh.se.se7en.client.mvp.presenters.impl.util;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.http.client.URL;

import ch.uzh.se.se7en.shared.model.FilmFilter;

public class UrlToken {
	//TODO Test encode decode method (looking if & are replaced aswell)
	/**

	@author Nicolas Küchler
	@pre
	@post

	@return
	 */ //TODO NK Test, Comments
	public static String createUrlToken(FilmFilter filter, boolean autoSearch)
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

		//Name --> need to encode 
		if(filter.getName()!=null)
		{
			//token+= "&na="+ filter.getName(); TODO NK Problem to solve calling static native method (used for junit test)
			token+= "&na="+ URL.encodePathSegment(filter.getName());
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
	TODO: NK Test and Comments. Probably better to put into a Utils class
	@author Nicolas Küchler
	@pre
	@post

	@return
	 */
	public static FilmFilter parseFilter(String urlToken)
	{
		//TODO Define Exception Handling
		FilmFilter filter = new FilmFilter();
		
		String[] parts = urlToken.substring(1).split("&");
		String fieldId ="";
		String value ="";
		for(String part : parts)
		{
			fieldId = part.substring(0, 2);
			value = part.substring(3);
			switch(fieldId){
			case "sb":
				//TODO NK Define what to do with autosearch
				break;
			case "na":
				filter.setName(URL.decodePathSegment(value));
				//filter.setName(value); TODO NK Problem to solve calling static native method (used for junit test)
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
