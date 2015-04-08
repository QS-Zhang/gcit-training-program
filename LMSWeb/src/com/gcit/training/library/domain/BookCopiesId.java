package com.gcit.training.library.domain;

import java.io.Serializable;

public class BookCopiesId implements Serializable{

	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	private int bookId;
	private int branchId;
}
