package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Publisher;

public class BookDAOTest {

	private Connection conn;

	@Before
	public void init() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/library", "root", "");
			conn.setAutoCommit(false);
		} catch(Exception e) {
			e.printStackTrace();
			fail("No connection!");
		}
	}



	@Test
	public void testCreate() throws SQLException {
		try {
			Book book = new Book();
			book.setTitle("Some new book name");
			Publisher pub = new Publisher();
			pub.setPublisherId(2); // check availability of PublisherId in mySQL
			book.setPublisher(pub);
			new BookDAO(conn).create(book);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			fail("Book create failed!"); 
		}

	}

	@Test
	public void testUpdate() throws SQLException {
		try {
			Book book = new Book();
			book.setTitle("Some updated book name");
			Publisher pub = new Publisher();
			pub.setPublisherId(2); // check availability of PublisherId in mySQL
			book.setPublisher(pub);
			new BookDAO(conn).create(book);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			fail("Book update failed!"); 
		}
	}

	@Test
	public void testDelete() throws SQLException {

		try {
			Book book = new Book();
			book.setBookId(5);
			new BookDAO(conn).delete(book);
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			fail("Book get failed!"); 
		}
	}
	

	@Test
	public void testGetBook() throws Exception {
		try {
			Book book = new Book();
			book.setBookId(4);
			new BookDAO(conn).getBook(book);
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			fail("Book get failed!"); 
		}
	}

}
