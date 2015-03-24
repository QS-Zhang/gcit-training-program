package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gcit.training.library.domain.Book;


public class BookDAO {
	public void create(Book book) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("insert into tbl_book (title,pubId) values (?,?)");
		stmt.setString(1, book.getTitle());
		stmt.setInt(2, book.getPublisher().getPublisherId());
		
		stmt.execute();
	}
	
	public void update(Book book) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		//CASCADE
		
		PreparedStatement stmt = conn.prepareStatement("update tbl_book set title = ?, pubId = ? where bookId = ?");
		
		stmt.setString(1, book.getTitle());
		stmt.setInt(2, book.getBookId());
		stmt.setInt(3, 4);
		
		stmt.execute();
	}

	public void delete(Book book) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		
		//CASCADE
		
		PreparedStatement stmt = conn.prepareStatement("delete from tbl_book where bookId = ?");
		stmt.setInt(1, book.getBookId());
		
		stmt.execute();
	}
}
