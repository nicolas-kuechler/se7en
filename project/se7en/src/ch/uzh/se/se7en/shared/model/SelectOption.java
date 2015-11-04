package ch.uzh.se.se7en.shared.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SelectOption implements IsSerializable{
	
	private Integer id;
	private String name;
	
	public SelectOption(){}
	
	public SelectOption(int id, String name){
		
		this.id = id;
		this.name = name;
	}
	
	public Integer getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

}
