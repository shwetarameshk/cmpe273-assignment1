package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;


public class Book {
    private long isbn;
    private String title;
    private String publicationDate;
    private String language;
    private int numOfPages;
    private String status;
    ArrayList<Author> authors;
    ArrayList<Review> reviews;
    private long authorID;
    private int reviewID;
    private int noOfReviews;
    private ArrayList<String> bookStatus = new ArrayList<String>();
    

    public Book(){
    	this.authors = new ArrayList<Author>();
    	this.authorID = 1;    	
    	this.reviews = new ArrayList<Review>();
    	this.reviewID = 1;
    	this.noOfReviews = 0;
    	bookStatus.add("lost");
    	bookStatus.add("checked-in");
    	bookStatus.add("in-queue");
    	bookStatus.add("available");
    }

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    

    /**
     * @return the publicationDate
     */
    public String getPublicationDate() {
	return publicationDate;
    }

    /**
     * @param publicationDate
     *            the publicationDate to set
     */
    public void setPublicationDate(String publicationDate) {
	this.publicationDate = publicationDate;
    }
    

    /**
     * @return the language
     */
    public String getLanguage() {
	return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(String language) {
	this.language = language;
    }
    
    
    /**
     * @return the noOfPages
     */
    public int getNumOfPages() {
	return numOfPages;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setNumOfPages(int numOfPages) {
	this.numOfPages = numOfPages;
    }
    
    //get the book status
    
    public String getStatus (){
    	return status;
    }
    
    //set the book status
    public void setStatus (String status){
    	if (status.isEmpty()){
    		this.status="available";
    	}
    	else{
    		this.status = status;   		
    	}
    }
    
    //check if status is valid
    public boolean checkStatus(String status){
    	String bookStatus=status.toLowerCase();
    	if (bookStatus.equals("lost") || bookStatus.equals("checked-in")|| bookStatus.equals("in-queue") || bookStatus.equals("available"))
    		return true;    		
    	else if (status.isEmpty()) 
    		return true;
    	else
    		return false;
    }
    
    //get the authors of the book
    public ArrayList<Author> getAuthors(){
    	return authors;
    }
    
    //set the authors of the book
    public void setAuthors(String authorName){
    	Author newAuthor = new Author();

    	newAuthor.setName(authorName);
    	newAuthor.setId(this.authorID++);

    	authors.add(newAuthor);
     		
     }
    
    //get the reviews for a book
    public ArrayList<Review> getReviews(){
    	return reviews;
    }
    
    //set the reviews for a book
    public void setReviews (String comment, int rating){
    	Review newReview = new Review();
    	newReview.setComment(comment);
    	newReview.setRating(rating);
    	newReview.setId(this.reviewID++);
    	
    	
    	reviews.add(newReview);
    	this.updateReviewCount();
    }
    
    //Get the no. of reviews for a book
    public boolean checkNumReviews()
    {
    	return (noOfReviews > 0);
    }
    
    //Update the number of reviews for a book when a new review is added
    public void updateReviewCount () {
        	this.noOfReviews++;
    }

    //method to add a new review for this book
	public void addNewReview (Review reviewObject){
		reviewObject.setId(this.reviewID++);
		this.updateReviewCount();
	}
	
   
}
