package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gcit.training.library.domain.Borrower;

public class BorrowerDAO {
	public void create(Borrower borr) throws SQLException
	{
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");

		PreparedStatement ps = conn.prepareStatement("insert into tbl_borrower(name,address,phone) values(?,?,?)");

		
		ps.setString(1, borr.getBorrowerName());
		ps.setString(2, borr.getBorrowerAddress());
		ps.setString(3,borr.getBorrowerPhone());

		ps.execute();
	}

	public void update(Borrower borr) throws SQLException
	{
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");

		PreparedStatement ps = conn.prepareStatement("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?");


		ps.setString(1, borr.getBorrowerName());
		ps.setString(2, borr.getBorrowerAddress());
		ps.setString(3,borr.getBorrowerPhone());
		ps.setInt(4, borr.getCardNo());

		ps.execute();
	}
	
	public void delete(Borrower borr) throws SQLException
	{
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");

		PreparedStatement ps = conn.prepareStatement("delete from tbl_borrower where cardNo = ?");

		ps.setInt(1,borr.getCardNo());
		
		ps.execute();
	}
}
