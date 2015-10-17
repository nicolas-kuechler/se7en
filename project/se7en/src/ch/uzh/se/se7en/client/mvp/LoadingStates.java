package ch.uzh.se.se7en.client.mvp;

/**
	This class holds all the possible Loading States within the application
 */
public class LoadingStates {
	public static final String DEFAULT = "default";	//indicates that so far no search for filmdata has taken place
	public static final String LOADING = "loading"; //indicates that filmdata is loaded from the server
	public static final String ERROR = "error";		//indicates that while loading filmdata from the server there was an error and the loading failed 
	public static final String SUCCESS = "success"; //indicates that the filmdata was loaded from the server successfully
}
