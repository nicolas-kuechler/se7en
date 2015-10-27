package ch.uzh.se.se7en.client;

/**
 * This class is used for client side console logging
 * @author Cyrill Halter
 *
 */
public final class ClientLog {

	/**
	 * Prints a message to the client console
	@author Cyrill Halter
	@pre -
	@post -
	@param String message The message to print to the client console
	
	@return
	 */
	public static native void writeMsg(String message)
	/*-{
    console.log(message);
	}-*/;
	
	/**
	 * Prints a message to the client erro console
	@author Cyrill Halter
	@pre -
	@post -
	@param String message The message to print to the client error console
	
	@return
	 */
	public static native void writeErr(String message)
	/*-{
	 console.error(message);
	 }-*/;
	
}
