package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.BookLoan;
import com.gcit.training.library.domain.Borrower;
import com.gcit.training.library.domain.Genre;
import com.gcit.training.library.domain.LibraryBranch;

public class BookLoanDAO extends BaseDAO<BookLoan> {

	public BookLoanDAO(Connection conn) {
		this.conn = conn;
	}

	public void create(BookLoan bookLoan) throws SQLException {

		save("insert into tbl_book_loans set bookId = ?, branchId = ?, cardNo = ?, dateOut = ?, dueDate = ?",
				new Object[] { bookLoan.getBook().getBookId(),
						bookLoan.getBranch().getBranchId(),
						bookLoan.getBorrower().getCardNo(),bookLoan.getDateOut(),bookLoan.getDateDue() });

	}

	public void update(BookLoan bookLoan) throws SQLException {
		save("update tbl_book_loans set dueDate = ? where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] {bookLoan.getDateDue(), bookLoan.getBook().getBookId(),
						bookLoan.getBranch(),
						bookLoan.getBorrower().getCardNo() });

	}

	public void delete(BookLoan bookLoan) throws SQLException {
		save("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(),bookLoan.getBorrower().getCardNo() });
	}

	public List<BookLoan> getAll() throws SQLException {
		List<BookLoan> blList = (List<BookLoan>) read("select * from tbl_book_loans");
		return blList;
	}

	public BookLoan getOne(int bookId, int branchId, int cardNo)
			throws SQLException {

		List<BookLoan> blList = (List<BookLoan>) read(
				"select * from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookId, branchId, cardNo });
		if (blList != null && blList.size() > 0) {
			return blList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<?> mapResult(ResultSet rs) throws SQLException {
		List<BookLoan> bookLoanList = new ArrayList<BookLoan>();
		BookDAO bkDAO = new BookDAO(conn);
		BorrowerDAO borrDAO = new BorrowerDAO(conn);
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
		while (rs.next()) {
			BookLoan bl = new BookLoan();
			bl.setDateOut(rs.getDate("dateOut"));
			bl.setDateDue(rs.getDate("dueDate"));

			Book book = bkDAO.getOne(rs.getInt("bookId"));
			Borrower borrower = borrDAO.getOne(rs.getInt("cardNo"));
			LibraryBranch lb = lbDAO.getOne(rs.getInt("branchId"));

			bl.setBook(book);
			bl.setBorrower(borrower);
			bl.setBranch(lb);

			bookLoanList.add(bl);
		}
		return bookLoanList;
	}

	@Override
	public List<?> mapFirstLevelResult(ResultSet rs) throws SQLException {
		
		return null;
	}

}
