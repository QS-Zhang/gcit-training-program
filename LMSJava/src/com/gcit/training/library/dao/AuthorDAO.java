package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gcit.training.library.domain.Author;

public class AuthorDAO {

	public void create(Author author) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("insert into tbl_author (authorName) values (?)");
		stmt.setString(1, author.getAuthorName());
		
		stmt.execute();
	}
	
	public void update(Author author) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("update tbl_author set authorName = ? where authorId = ?");
		stmt.setString(1, author.getAuthorName());
		stmt.setInt(2, author.getAuthorId());
		
		stmt.execute();
	}

	public void delete(Author author) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("delete from tbl_author where authorId = ?");
		stmt.setInt(1, author.getAuthorId());
		
		stmt.execute();
	}
}
