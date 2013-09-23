package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import edu.sjsu.cmpe.library.dto.LinkDto;

public class BookDetails {
    private long isbn;
    private String title;
    private String publicationDate;
    private String language;
    private int numOfPages;
    private String status;

    ArrayList<LinkDto> authors = new ArrayList<LinkDto>(); 
    ArrayList<LinkDto> reviews = new ArrayList<LinkDto>(); 
    
    public BookDetails(Book book){
    	this.isbn = book.getIsbn();
    	this.title = book.getTitle();
    	this.publicationDate = book.getPublicationDate();
    	this.language = book.getLanguage();
    	this.numOfPages = book.getNumOfPages();
    	this.status = book.getStatus();
    	
    	for(int i=0; i < book.authors.size(); i++){
    		this.authors.add(new LinkDto("view-author", "/books/" + book.getIsbn() + "/authors/" + book.authors.get(i).getId(),"GET"));
    	}
    	
    	for(int i=0; i < book.reviews.size(); i++){
    		this.reviews.add(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + book.reviews.get(i).getId(),"GET"));
    	}
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
    	if (status.isEmpty()) 
    		this.status = "available";
    	else
    		this.status = status;
    }

    
    //get the authors of the book
    public ArrayList<LinkDto> getAuthors(){
    	return authors;
    }
     
     
    //get the reviews for a book
    public ArrayList<LinkDto> getReviews(){
    	return reviews;
    }
	
   
}
