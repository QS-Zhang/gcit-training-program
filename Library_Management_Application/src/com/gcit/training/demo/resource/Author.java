package com.gcit.training.demo.resource;

public class Author {

	public Author(String authorName, int authorId) {
		super();
		this.authorName = authorName;
		this.authorId = authorId;
	}

	private String authorName;
	private int authorId;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

}
