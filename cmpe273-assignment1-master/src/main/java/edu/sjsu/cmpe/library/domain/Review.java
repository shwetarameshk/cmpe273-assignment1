package edu.sjsu.cmpe.library.domain;

public class Review {
	
	private int id;	
	private int rating;
	private String comment;

	
	public int getId (){
		return id;
	}
	
	public void setId(int reviewID){
		this.id = reviewID;
	}
	
	public int getRating(){
		return rating;
	}
	
	public void setRating (int rating){
		this.rating = rating;
	}
	
	public String getComment(){
		return comment;
	}
	
	public void setComment (String comment){
		this.comment = comment;
	}
	
	public boolean checkRating(){
		if (rating<1 || rating>5)
			return false;
		else
			return true;
	}
	
	public boolean checkComment(){
		if (comment == "" || comment == null)
			return false;
		else
			return true;
	}
	
}
