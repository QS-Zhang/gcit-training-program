package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.BookCopies;
import com.gcit.training.library.domain.BookCopiesId;
import com.gcit.training.library.domain.LibraryBranch;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn)
	{
		this.conn = conn;
	}
	public void create(BookCopies bc) throws SQLException {

		save("insert into tbl_book_copies set bookId = ?, branchId = ?, noOfCopies = ?",
				new Object[] { bc.getBook().getBookId(), bc.getBranch().getBranchId(),
						bc.getNoOfCopies() });

	}

	public void update(BookCopies bc) throws SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bc.getNoOfCopies(), bc.getBook().getBookId(),
						bc.getBranch().getBranchId() });

	}

	public void delete(BookCopies bc) throws SQLException {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bc.getBook().getBookId(),
						bc.getBranch().getBranchId() });
	}

	public List<BookCopies> getAll() throws SQLException {
		List<BookCopies> blList = (List<BookCopies>) read("select * from tbl_book_copies");
		return blList;
	}

	public BookCopies getOne(BookCopiesId id) throws SQLException {

		List<BookCopies> blList = (List<BookCopies>) read(
				"select * from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { id.getBookId(), id.getBranchId() });
		if (blList != null && blList.size() > 0) {
			return blList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<?> mapResult(ResultSet rs) throws SQLException {
		List<BookCopies> bcList = new ArrayList<BookCopies>();
		BookDAO bDAO = new BookDAO(conn);
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);

		while (rs.next()) {
			BookCopies bc = new BookCopies();
			Book bk = bDAO.getOne(rs.getInt("bookId"));

			LibraryBranch lb = lbDAO.getOne(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			bc.setBook(bk);
			bc.setBranch(lb);

			bcList.add(bc);
		}

		return bcList;
	}
	@Override
	public List<?> mapFirstLevelResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
