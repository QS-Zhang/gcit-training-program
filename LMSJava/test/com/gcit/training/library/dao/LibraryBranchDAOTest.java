package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.gcit.training.library.domain.LibraryBranch;

public class LibraryBranchDAOTest {

	@Test
	public void testCreate() {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName("Some new branch name");
		branch.setBranchAddress("Some new branch address");
		
		
		try {
			new LibraryBranchDAO().create(branch);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("branch create failed!"); 
		}
		
	}

	@Test
	public void testUpdate() {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName("Some updated branch name");
		branch.setBranchAddress("Some updated branch address");
		branch.setBranchId(1);
		
		
		try {
			new LibraryBranchDAO().update(branch);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("branch updating failed!"); 
		}
	}

	@Test
	public void testDelete() {
		LibraryBranch branch = new LibraryBranch();
		
		branch.setBranchId(6);
		
		
		try {
			new LibraryBranchDAO().delete(branch);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("branch create failed!"); 
		}
	}

}
