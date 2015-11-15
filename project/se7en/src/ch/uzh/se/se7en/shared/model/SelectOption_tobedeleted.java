package ch.uzh.se.se7en.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class is used to store options for the multiselect widgets in the
 * FilterView
 * 
 * @author Cyrill Halter
 *
 */
public class SelectOption_tobedeleted implements IsSerializable {

	private Integer id;
	private String name;

	public SelectOption_tobedeleted() {
	}

	public SelectOption_tobedeleted(int id, String name) {

		this.id = id;
		this.name = name;
	}

	/**
	 * This Method returns the id of the option
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * 
	 * @return Integer id The id of the option
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This Method returns the name of the option
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * 
	 * @return String name The name of the option
	 */
	public String getName() {
		return name;
	}

}
