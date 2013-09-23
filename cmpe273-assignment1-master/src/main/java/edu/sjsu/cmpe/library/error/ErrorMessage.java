package edu.sjsu.cmpe.library.error;

public class ErrorMessage {
	
	
	public String getBookNotFoundErrorMessage (){
		return "Book not found. Please try again!";
	}
	
	public String getReviewNotFoundErrorMessage(){
		return "Review not found. Please try again!";
	}
	
	public String getAuthorNotFoundErrorMessage(){
		return "Author not found. Please try again!";
	}
	
	public String getNoReviewsErrorMessage(){
		return "No reviews for this book!";
	}

	public String getInvalidRatingMessage(){
		return "The review entered is invalid. Please enter rating between 1 and 5.";
	}

	public String getInvalidCommentMessage(){
		return "The review entered is invalid. Please enter comments.";
	}
	
	public String getInvalidTitleMessage(){
		return "Please enter a valid title for the book";
	}
	
	public String getInvalidPublicationDateMessage(){
		return "Please enter a valid publication date for the book";
	}
	
	public String getInvalidAuthorsMessage(){
		return "Please enter a valid author for the book";
	}
	
	public String getInvalidStatusMessage(){
		return "Please enter a valid book status: available, lost, checked-in or in-queue";
	}
}
