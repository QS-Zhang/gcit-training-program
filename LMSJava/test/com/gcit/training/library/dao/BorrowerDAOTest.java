package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.gcit.training.library.domain.Borrower;

public class BorrowerDAOTest {

	@Test
	public void testCreate() {
		
		Borrower borrower = new Borrower();
		borrower.setBorrowerAddress("Borrower new address");
		borrower.setBorrowerName("Borrower new name");
		borrower.setBorrowerPhone("Borrower new phone");
		try
		{			
			new BorrowerDAO().create(borrower);

		}catch(SQLException e)
		{
			fail("Failed Creating a borrower");
			e.printStackTrace();
		}
		
	}

	@Test
	public void testUpdate() {
		
		Borrower borrower = new Borrower();
		borrower.setBorrowerAddress("Borrower updated address");
		borrower.setBorrowerName("Borrower updated name");
		borrower.setBorrowerPhone("Borrower update phone");
		borrower.setCardNo(1);
		try {
			new BorrowerDAO().update(borrower);
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Failed Updating a borrower");
		}
		
	}

	@Test
	public void testDelete() {
		Borrower borrower = new Borrower();
		borrower.setCardNo(7);
		
		try {
			new BorrowerDAO().delete(borrower);
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Failed Deleting a borrower");
		}
		
	}

}
