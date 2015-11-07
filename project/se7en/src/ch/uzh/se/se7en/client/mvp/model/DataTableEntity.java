package ch.uzh.se.se7en.client.mvp.model;

public class DataTableEntity {
	private String name;
	private int value;
	
	public DataTableEntity(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	@pre name!= null
	@post -
	@return the name
	 */
	public String getName() {
		return name;
	}

	/**
	@pre -
	@post name==name
	@param name the name to set
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	@pre value!= null
	@post -
	@return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	@pre -
	@post value==value
	@param value the value to set
	*/
	public void setValue(int value) {
		this.value = value;
	}

}
