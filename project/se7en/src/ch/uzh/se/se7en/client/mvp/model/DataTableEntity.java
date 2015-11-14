package ch.uzh.se.se7en.client.mvp.model;

public class DataTableEntity{
	private String name;
	private int value;
	private int id;
	
	public DataTableEntity(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public DataTableEntity(String name, int value, int id) {
		this.name = name;
		this.value = value;
		this.id = id;
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

	/**
	@pre id!= null
	@post -
	@return the id
	 */
	public int getId() {
		return id;
	}

	/**
	@pre -
	@post id==id
	@param id the id to set
	*/
	public void setId(int id) {
		this.id = id;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DataTableEntity))
			return false;
		DataTableEntity other = (DataTableEntity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataTableEntity [name=" + name + ", value=" + value + ", id=" + id + "]";
	}
	
}
