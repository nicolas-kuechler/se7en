package ch.uzh.se.se7en.client.mvp;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class Boundaries {
	public static final int MIN_YEAR = 1890;
	public static final int MAX_YEAR = Integer.parseInt(DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[2]);
	
	public static final int MIN_LENGTH = 0;
	public static final int MAX_LENGTH = 600;

}
