package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.gcit.training.library.domain.Author;

public class AuthorDAOTest {

	@Test
	public void testCreate() {
		Author author = new Author();
		author.setAuthorName("Some new author");
		try {
			new AuthorDAO().create(author);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Author create failed!"); 
		}
	}

	@Test
	public void testUpdate() {
		Author author = new Author();
		author.setAuthorId(1);
		author.setAuthorName("Some other name");
		try {
			new AuthorDAO().update(author);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Author update failed!"); 
		}
	}

	@Test
	public void testDelete() {
		Author author = new Author();
		author.setAuthorId(1);
		try {
			new AuthorDAO().delete(author);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Author delete failed!"); 
		}
	}

}
