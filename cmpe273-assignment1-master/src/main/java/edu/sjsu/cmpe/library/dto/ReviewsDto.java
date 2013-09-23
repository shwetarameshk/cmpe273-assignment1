package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;

import edu.sjsu.cmpe.library.domain.Review;

public class ReviewsDto extends LinksDto  {

	private ArrayList<Review> reviews = new ArrayList<Review>();
	
	public ReviewsDto (ArrayList<Review> reviews){
		this.reviews = reviews;
	}
	
	public void addReviews (ArrayList<Review> reviews){
		reviews.addAll(reviews);
	}
	
	public ArrayList<Review> getReviews(){
		return reviews;
	}
	
	public void setReviews (ArrayList<Review> reviews){
		this.reviews = reviews;
	}

}
