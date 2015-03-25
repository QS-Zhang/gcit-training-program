package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.domain.Author;

public class AuthorDAO extends BaseDAO {

	public AuthorDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Author author) throws SQLException {
		save("insert into tbl_author (authorName) values (?)",
				new Object[] { author.getAuthorName() });
	}

	public void update(Author author) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void delete(Author author) throws SQLException {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}
}
