package ch.uzh.se.se7en.server;

/***
 * This class is used for server side logging
 * @author Cyrill Halter
 *
 */
public final class ServerLog {
	/**
	 * Writes a string to the console
	@author Cyrill Halter
	@pre -
	@post -
	@param String message The message to write to the console
	
	@return -
	 */
	//This method may not be working correctly, don't use for the moment.. writeErr works fine
	public static void writeMsg(String message){
		System.out.println(message);
	}
	
	/**
	 * Writes a string to the error console
	@author Cyrill Halter
	@pre -
	@post -
	@param String message the message to write to the error console
	
	@return -
	 */
	public static void writeErr(String message){
		System.err.println(message);
	}
}
