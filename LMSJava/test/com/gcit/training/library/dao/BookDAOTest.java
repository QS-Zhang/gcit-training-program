package com.gcit.training.library.dao;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Publisher;

public class BookDAOTest {
	@Test
	public void testCreate() {
		Book book = new Book();
		book.setTitle("Some new book name");
		Publisher pub = new Publisher();
		pub.setPublisherId(2); // check availability of PublisherId in mySQL
		book.setPublisher(pub);
		
		try {
			new BookDAO().create(book);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Book create failed!"); 
		}
	}

	@Test
	public void testUpdate() {
		Book book = new Book();
		book.setBookId(2);
		book.setTitle("Some other name");
		Publisher pub = new Publisher();
		pub.setPublisherId(3);
		book.setPublisher(pub);
		try {
			new BookDAO().update(book);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Book update failed!"); 
		}
	}

	@Test
	public void testDelete() {
		Book book = new Book();
		book.setBookId(3);
		try {
			new BookDAO().delete(book);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Book delete failed!"); 
		}
	}
}
