package com.gcit.training.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.dao.BookCopiesDAO;
import com.gcit.training.library.dao.BookLoanDAO;
import com.gcit.training.library.dao.BorrowerDAO;
import com.gcit.training.library.domain.BookCopies;
import com.gcit.training.library.domain.BookCopiesId;
import com.gcit.training.library.domain.BookLoan;
import com.gcit.training.library.domain.Borrower;

public class BorrowerService extends BaseService {
	public void addBookLoan(BookLoan bl) throws Exception {
		if (bl != null) {
			if (bl.getBook() != null && bl.getBranch() != null
					&& bl.getBorrower() != null) {
				Connection conn = getConnection();
				try {
					new BookLoanDAO(conn).create(bl);
					BookCopies bc = new BookCopies();

					bc.setBook(bl.getBook());
					bc.setBranch(bl.getBranch());
					
					checkOutBookCopies(bc, conn);
					
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
				} finally {
					conn.close();
					conn = null;
				}
			}

		}
	}
	
	public void createBookCopies(BookCopies bc) throws Exception{
		
		if (bc != null)
		{
			if(bc.getBook()!=null && bc.getBranch()!=null)
			{
				Connection conn = getConnection();
				BookCopies newBc = new BookCopies();
				try{
					BookCopiesId bcId = new BookCopiesId();
					bcId.setBookId(bc.getBook().getBookId());
					bcId.setBranchId(bc.getBranch().getBranchId());
					newBc = new BookCopiesDAO(conn).getOne(bcId);
					
					newBc.setNoOfCopies(bc.getNoOfCopies());
					new BookCopiesDAO(conn).create(newBc);
					conn.commit();
				}catch(SQLException e)
				{
					conn.rollback();
					e.printStackTrace();
				}
			}
		}
	}
	
	public void checkOutBookCopies(BookCopies bc, Connection conn) throws Exception
	{
		if (bc != null)
		{
			if(bc.getBook()!=null && bc.getBranch()!=null)
			{
				
				try{
					BookCopies newBc = new BookCopies();
					newBc = new BookCopiesDAO(conn).getOne(bc.getId());
					
					newBc.setNoOfCopies(bc.getNoOfCopies() - 1);
					new BookCopiesDAO(conn).update(newBc);
					conn.commit();
				}catch(SQLException e)
				{
					conn.rollback();
					e.printStackTrace();
				}
			}
		}
	}

	public void returnBookCopies(BookCopies bc, Connection conn) throws Exception
	{
		if (bc != null)
		{
			if(bc.getBook()!=null && bc.getBranch()!=null)
			{
				
				try{
					BookCopies newBc = new BookCopies();
					newBc = new BookCopiesDAO(conn).getOne(bc.getId());
					
					newBc.setNoOfCopies(bc.getNoOfCopies() + 1);
					new BookCopiesDAO(conn).update(newBc);
					conn.commit();
				}catch(SQLException e)
				{
					conn.rollback();
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<Integer> getAllCardNo() throws Exception
	{
		Connection conn =  getConnection();
		List<Integer> cardNoList = new ArrayList<Integer>();
		try{
			List<Borrower> borrList= new BorrowerDAO(conn).getAll();
			for(Borrower borr : borrList)
			{
				cardNoList.add(borr.getCardNo());
			}
			return cardNoList;
		}finally{
			conn.close();
			conn = null;
		}
	}
	
	public void deleteBookLoan(BookLoan bl) throws Exception {
		if (bl != null) {
			if (bl.getBook() != null && bl.getBranch() != null
					&& bl.getBorrower() != null) {
				Connection conn = getConnection();
				try {
					new BookLoanDAO(conn).delete(bl);
					BookCopies bc = new BookCopies();

					bc.setBook(bl.getBook());
					bc.setBranch(bl.getBranch());
					
					returnBookCopies(bc, conn);
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
				} finally {
					conn.close();
					conn = null;
				}
			}

		}
	}
	
}
