package edu.sjsu.cmpe.library.domain;


public class Author {
	private long id;
	private String name;
	
	public long getId (){
		return id;
	}
	
	public void setId (long authorID){
		this.id = authorID;
	}
	
	public String getName (){
		return name;
	}
	
	public void setName (String name){
		this.name = name;
	}
}
