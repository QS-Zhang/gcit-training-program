package com.gcit.training.library.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.dao.BookCopiesDAO;
import com.gcit.training.library.dao.LibraryBranchDAO;
import com.gcit.training.library.domain.BookCopies;
import com.gcit.training.library.domain.LibraryBranch;

public class LibrarianService extends BaseService{
	public void updateBranch(LibraryBranch branch) throws Exception
	{
		Connection conn = getConnection();
		if(branch != null)
		{
			if(branch.getBranchName() !=null && branch.getBranchAddress() !=null)
			{
				try{
					new LibraryBranchDAO(conn).create(branch);
					conn.commit();
				}
				catch(SQLException e)
				{
					conn.rollback();
					
				}finally
				{
					conn.close();
					conn = null;
				}
			}
		}else
		{
			throw new Exception("invalid Branch");
		}
	}
	
	public void updateCopiesToBranch(BookCopies bc) throws Exception
	{

		Connection conn = getConnection();
		
		try{
			new BookCopiesDAO(conn).update(bc);
			conn.commit();
			
			
		}catch (SQLException e)
		{
			conn.rollback();
		}
		finally{
			conn.close();
			conn = null;
		}
	}
	
	public int getCopiesOfBranch(BookCopies bc) throws Exception
	{
		Connection conn = getConnection();
		
		try{
			BookCopies newbc = new BookCopiesDAO(conn).getOne(bc.getId());
			conn.commit();
			return newbc.getNoOfCopies();
			
		}catch (SQLException e)
		{
			conn.rollback();
		}
		finally{
			conn.close();
			conn = null;
		}
		return 0;
		
	}
}
