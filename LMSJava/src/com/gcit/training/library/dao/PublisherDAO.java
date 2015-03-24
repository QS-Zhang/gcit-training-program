package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gcit.training.library.domain.Publisher;

public class PublisherDAO {
	public void create(Publisher pub) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)");
		stmt.setString(1, pub.getPublisherName());
		stmt.setString(2, pub.getPublisherAddress());
		stmt.setString(3, pub.getPublisherPhone());
		
		stmt.execute();
	}
	
	public void update(Publisher pub) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?");
		stmt.setString(1, pub.getPublisherName());
		stmt.setString(2, pub.getPublisherAddress());
		stmt.setString(3, pub.getPublisherPhone());
		stmt.setInt(4, pub.getPublisherId());
		
		stmt.execute();
	}

	public void delete(Publisher pub) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("delete from tbl_publisher where publisherId = ?");
		stmt.setInt(1, pub.getPublisherId());
		
		stmt.execute();
	}
}
