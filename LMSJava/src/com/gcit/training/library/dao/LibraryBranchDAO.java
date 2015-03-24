package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gcit.training.library.domain.LibraryBranch;

public class LibraryBranchDAO {
	public void create(LibraryBranch branch) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("insert into tbl_library_branch (branchName,branchAddress) values (?,?)");
		stmt.setString(1, branch.getBranchName());
		stmt.setString(2,branch.getBranchAddress());
		
		stmt.execute();
	}
	
	public void update(LibraryBranch branch) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?");
		stmt.setString(1, branch.getBranchName());
		stmt.setString(2, branch.getBranchAddress());
		stmt.setInt(3, branch.getBranchId());
		
		stmt.execute();
	}

	public void delete(LibraryBranch branch) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/library", "root", "");
		
		PreparedStatement stmt = conn.prepareStatement("delete from tbl_library_branch where branchId = ?");
		stmt.setInt(1, branch.getBranchId());
		
		stmt.execute();
	}
}
