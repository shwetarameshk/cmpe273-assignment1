package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.BookDetails;
import edu.sjsu.cmpe.library.domain.LibraryBooks;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.error.ErrorMessage;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookResource {
	
    public BookResource() {   	

    }
    
    public int getBookIndex (long isbn){
    	
    	int bookIndex = 999;
    	
    	for (int i = 0; i<LibraryBooks.libraryBooks.size(); i++){
    		if (isbn != LibraryBooks.libraryBooks.get(i).getIsbn()){
    			continue;
    		}
    		else {
    			bookIndex = i;
    			break;
    		}
     	}
    	return bookIndex;
    }
   
    //API 2 - to add a new book to library
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    @Timed(name="create-book")
    
    public Response createNewBook (@Valid BookInfo bookInfo ){
    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	
    	//Check if fields are valid
    	if (!bookInfo.checkTitle()){
    		return Response.status(420).entity(errorMsg.getInvalidTitleMessage()).build();
    	}
    	
    	if (!bookInfo.checkAuthors()){
    		return Response.status(420).entity(errorMsg.getInvalidAuthorsMessage()).build();
    	}
    	
    	if (!bookInfo.checkPublicationDate()){
    		return Response.status(420).entity(errorMsg.getInvalidPublicationDateMessage()).build();
    	}
    		
    	
    	
    	String title = bookInfo.title;
    	String publicationDate = bookInfo.publicationDate;
    	String language = bookInfo.language;
    	int numOfPages = bookInfo.numOfPages;
    	String status = bookInfo.status;
    	
    	if (!bookInfo.checkStatus(status)){
    		return Response.status(420).entity(errorMsg.getInvalidStatusMessage()).build();
    	}
    		
    	ArrayList<Author> authors = bookInfo.authors; 
    	//ArrayList<Review> reviews = bookInfo.reviews;
    	
       	long nextIsbn = LibraryBooks.nextIsbn++;
       	
    	Book book = new Book();
    	    
    	book.setIsbn(nextIsbn);
    	book.setTitle(title);
    	book.setPublicationDate(publicationDate);
    	book.setLanguage(language);
    	book.setNumOfPages(numOfPages);
    	book.setStatus(status);
	
    	for (int i = 0 ; i < authors.size(); i++){
    		book.setAuthors(authors.get(i).getName());
    	}
    	/*
    	for (int j=0; j<reviews.size();j++){
    		book.setReviews(reviews.get(j).getComment(),reviews.get(j).getRating());
    		book.updateReviewCount();
    	}*/
    	
    	//add new book to library of all books
    	LibraryBooks.libraryBooks.add(book);

        	
        	
    		LinksDto linksResponse = new LinksDto();
    		linksResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
    		linksResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
    		linksResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
    		linksResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews", "POST"));
    		    	
    		return Response.status(201).entity(linksResponse).build();
     
 
	}

    //API 3 - to view a particular book
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response getBookByIsbn(@PathParam("isbn") long isbn) {
    	
    	int bookIndex = getBookIndex(isbn);
    	    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	if (bookIndex == 999){
    		return Response.status(420).entity(errorMsg.getBookNotFoundErrorMessage()).build();
    	}
    	
    	Book book = LibraryBooks.libraryBooks.get(bookIndex);
    	
    	BookDetails bookDetails = new BookDetails(book);
	  	BookDto bookResponse = new BookDto(bookDetails);
		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
		bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
		bookResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
		bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews/", "POST"));
		if (book.checkNumReviews())
			bookResponse.addLink(new LinkDto("view-all-reviews","/books/" + book.getIsbn() + "/reviews", "GET"));
	    
		//return bookResponse;
		return Response.status(200).entity(bookResponse).build();

    }

    //API 4 - to delete a particular book
    @DELETE
    @Path("/{isbn}")
    @Timed (name = "delete-book")
    public Response deleteBookByIsbn (@PathParam ("isbn") long isbn){
    	
    	int bookIndex = getBookIndex(isbn);
    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	if (bookIndex == 999){
    		return Response.status(420).entity(errorMsg.getBookNotFoundErrorMessage()).build();
    	}
    		
    	   
    	LibraryBooks.libraryBooks.remove(bookIndex);
    	LinksDto links = new LinksDto();
    	links.addLink(new LinkDto("create-book", "/books", "POST"));

    	return Response.ok(links).build();
    	
    }

    //API 5 - to update status for a book
    @PUT
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response updateBookByIsbn (@PathParam ("isbn") long isbn, @QueryParam ("status") String status){
    	
    	int bookIndex = getBookIndex(isbn);
    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	if (bookIndex == 999){
    		return Response.status(420).entity(errorMsg.getBookNotFoundErrorMessage()).build();
    	}
    	   	
    	Book book = LibraryBooks.libraryBooks.get(bookIndex);
    	book.setStatus(status);
    	
    	if (!book.checkStatus(status)){
    		return Response.status(420).entity(errorMsg.getInvalidStatusMessage()).build();
    	}
    	
    	
		LinksDto linksResponse = new LinksDto();
		linksResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
		linksResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
		linksResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
		linksResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews", "POST"));
		
		if (book.checkNumReviews())
			linksResponse.addLink(new LinkDto("view-all-reviews","/books/" + book.getIsbn() + "/reviews", "GET"));
		
	
		//Build a 201 Response and add the bookResponse to it and return it.
		return Response.status(200).entity(linksResponse).build();
    }

	//API 6 - to create a review for a book
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{isbn}/reviews")
	@Timed(name="create-review")
	
	public Response createNewReview (@PathParam ("isbn") long isbn, Review review){
		
		int bookIndex;
		ErrorMessage errorMsg = new ErrorMessage();
		int reviewIndex = 0;
		int rating = review.getRating();		
		String comment = review.getComment();
		if (!review.checkRating()){
			return Response.status(420).entity(errorMsg.getInvalidRatingMessage()).build();
		}

		if (!review.checkComment()){
			return Response.status(420).entity(errorMsg.getInvalidCommentMessage()).build();
		}
		
		bookIndex = getBookIndex(isbn);		
    	
    	if (bookIndex == 999){
    		return Response.status(420).entity(errorMsg.getBookNotFoundErrorMessage()).build();
    	}
		
		Book book = LibraryBooks.libraryBooks.get(bookIndex);
		reviewIndex = book.getReviews().size();
		book.setReviews(comment, rating);
		LinksDto linksResponse = new LinksDto();
		linksResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + book.getReviews().get(reviewIndex).getId() ,"GET"));
		
		return Response.status(201).entity(linksResponse).build();
				
	}

    //API 7 - to view a particular review for a book
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-review")
    public Response viewReviewById (@PathParam ("isbn") long isbn, @PathParam ("id") int id){
    	
    	int bookIndex = getBookIndex(isbn);
    	int reviewIndex = 999;
    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	if (bookIndex == 999){
    		return Response.status(420).entity(errorMsg.getBookNotFoundErrorMessage()).build();
    	}
    	   	
    	Book book = LibraryBooks.libraryBooks.get(bookIndex);
    	ArrayList<Review> reviews = book.getReviews();
    	
    	for (int i = 0; i < reviews.size(); i++){
    		if (id != reviews.get(i).getId()){
    			continue;
    		}
    		else {
    			reviewIndex = i;
    			break;
    		}
     	}
    	
    	if (reviewIndex == 999){
    		return Response.status(420).entity(errorMsg.getReviewNotFoundErrorMessage()).build();
    	}
    	
    	Review review = reviews.get(reviewIndex);
    	ReviewDto reviewResponse = new ReviewDto (review);
    	reviewResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + review.getId() ,"GET"));
		
		return Response.status(200).entity(reviewResponse).build();
    	
    }

    //API 8 - to view all reviews for a book
    @GET
    @Path("/{isbn}/reviews")
    @Timed (name="view-all-reviews")
    public Response viewAllReviews (@PathParam ("isbn") long isbn){
    	
    	int bookIndex = getBookIndex(isbn);
    	   	
    	Book book = LibraryBooks.libraryBooks.get(bookIndex);
    	ArrayList<Review> reviews = book.getReviews();
    	ReviewsDto reviewResponse = new ReviewsDto(reviews);
    	    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	if(!book.checkNumReviews()){
    		return Response.status(420).entity(errorMsg.getNoReviewsErrorMessage()).build();
    	}
	  	
    	return Response.status(200).entity(reviewResponse).build();
    }

    
    //API 9 - to view a particular author of a book
    @GET
    @Path ("{isbn}/authors/{id}")
    @Timed (name="view-author")
    public Response getAuthorById (@PathParam ("isbn") long isbn, @PathParam ("id") long id){
    	
    	int bookIndex = getBookIndex(isbn);
    	int authorIndex = 999;
    	
    	ErrorMessage errorMsg = new ErrorMessage();
    	if (bookIndex == 999){
    		return Response.status(420).entity(errorMsg.getBookNotFoundErrorMessage()).build();
    	}
    	
    	Book book = LibraryBooks.libraryBooks.get(bookIndex);
    	ArrayList<Author> authors = book.getAuthors();
    	
    	for (int i = 0 ; i< authors.size(); i++){
    		if (id != authors.get(i).getId()){
    			continue;
    		}
    		else
    		{
    			authorIndex = i;
    			break;
    		}
    	}
    	
    	if (authorIndex==999){
    		return Response.status(420).entity(errorMsg.getAuthorNotFoundErrorMessage()).build();
    	}
    		Author author = authors.get(authorIndex);
        	AuthorDto authorResponse = new AuthorDto (author);
        	authorResponse.addLink(new LinkDto("view-author", "/books/" + book.getIsbn() + "/authors/" + author.getId() ,"GET"));
    		
    		return Response.status(200).entity(authorResponse).build();
    	}

    //API 10 - to view all authors of a book
    @GET
    @Path("/{isbn}/authors")
    @Timed (name="view-all-authors")
    public Response viewAllAuthors (@PathParam ("isbn") long isbn){
    	
    	int bookIndex = getBookIndex(isbn);
    	   	
    	Book book = LibraryBooks.libraryBooks.get(bookIndex);
    	ArrayList<Author> authors = book.getAuthors();
    	AuthorsDto authorResponse = new AuthorsDto(authors);
	
    	return Response.status(200).entity(authorResponse).build();
    }
    
    	
    }


