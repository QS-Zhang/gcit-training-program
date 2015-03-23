package com.gcit.training.demo.resource;

public class Book {

	public Book(String title, int bookId, int pubId) {
		super();
		this.title = title;
		this.bookId = bookId;
		this.pubId = pubId;
	}

	public Book() {
		// TODO Auto-generated constructor stub
	}

	private String title;

	private int bookId;

	private int pubId;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
