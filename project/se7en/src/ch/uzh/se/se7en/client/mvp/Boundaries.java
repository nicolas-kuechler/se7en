package ch.uzh.se.se7en.client.mvp;


/**
 This class holds the boundaries for the FilmData.
 In particular it is used for the range slider in the filterview and the mapview.
 @author Nicolas Küchler
 */
public class Boundaries {
	public static final int MIN_YEAR = 1890;
	public static final int MAX_YEAR = 2015;//Integer.parseInt(DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[2]); //get current year
	
	public static final int MIN_LENGTH = 0;
	public static final int MAX_LENGTH = 600;
}
