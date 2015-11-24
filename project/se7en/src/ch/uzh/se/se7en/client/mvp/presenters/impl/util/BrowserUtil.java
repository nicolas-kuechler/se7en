package ch.uzh.se.se7en.client.mvp.presenters.impl.util;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;

import ch.uzh.se.se7en.client.ClientLog;

/**
 Util class used to interact with javascrips native functions to control browser functionalities.
 * @author Nicolas Küchler
 */
public class BrowserUtil {
	
	/**
	Method to encode a String
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param	a String which should be encoded
	@return the encoded String
	 */
	public String encode(String word)
	{
		return URL.encodePathSegment(word);
	}
	
	/**
	Method to decode an encoded String
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param	a String which should be decoded
	@return the decoded String
	 */
	public String decode(String word)
	{
		return URL.decodePathSegment(word);
	}
	
	/**
	Method to fire a new item on top of the history stack
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param	historyToken the name of the new token
	@param	issueEvent true if a ValueChangeEvent should be issued
	 */
	public void newHistoryItem(String historyToken, boolean issueEvent)
	{
		History.newItem(historyToken, issueEvent);
	}
	
	/**
	Method to replace the item on top of the history stack
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param	historyToken the name of the replacement token 
	@param	issueEvent true if a ValueChangeEvent should be issued
	 */
	public void replaceHistoryItem(String historyToken, boolean issueEvent)
	{
		History.replaceItem(historyToken, issueEvent);
	}
	
	/**
	Method to get the current History token
	@author Nicolas Küchler
	@pre	-
	@post	History.getToken().equals(History.getToken()@pre)
	@return the current history token
	 */
	public String getHistoryToken()
	{
		return History.getToken();
	}
	
	/**
	Method to write a message to the browser console
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param	msg the message that should be written to the console
	 */
	public void writeMsg(String msg)
	{
		ClientLog.writeMsg(msg);
	}
	
	/**
	Method to write an error to the browser console
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param	msg the error message that should be written to the console
	 */
	public void writeErr(String msg)
	{
		ClientLog.writeErr(msg);
	}

}
