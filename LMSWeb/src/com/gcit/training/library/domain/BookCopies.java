package com.gcit.training.library.domain;

public class BookCopies extends AbstractDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7711102722272648904L;
	private BookCopiesId id;
	
	private Book book;
	private LibraryBranch branch;
	private int noOfCopies;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public LibraryBranch getBranch() {
		return branch;
	}
	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}
	public int getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	public BookCopiesId getId() {
		return id;
	}
	public void setId(BookCopiesId id) {
		this.id = id;
	}
	
	
	
}
