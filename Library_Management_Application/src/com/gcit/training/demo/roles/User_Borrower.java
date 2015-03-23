package com.gcit.training.demo.roles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.training.demo.resource.Book;
import com.gcit.training.demo.resource.Branch;

public class User_Borrower {
	private Scanner scan = new Scanner(System.in);
	private List<Branch> branchList;
	private List<Book> bookList;

	public boolean isValidCardNo(int cardNo) {
		try {
			// create our java preparedstatement using a sql select query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("SELECT cardNo FROM tbl_borrower WHERE cardNo= ?");

			// set the preparedstatement parameters

			ps.setInt(1, cardNo);

			// call executeUpdate to execute our sql select statement
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				return true;
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
		return false;

	}

	public int Borr1() {
		System.out.println("1) Check out a book\n" + "2) Return a Book\n"
				+ "3) Quit to Previous\n");

		int option = scan.nextInt();
		return option;
	}

	public List<Branch> getListOfBranches() {
		branchList = new ArrayList<Branch>();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_library_branch ");

			ResultSet rs = stmt.executeQuery();

			int index = 1;

			while (rs.next()) {
				System.out.print(index++ + ") ");
				branchList
						.add(new Branch(rs.getInt("branchId"), rs
								.getString("branchName"), rs
								.getString("branchAddress")));
				System.out.println(rs.getString("branchName") + ","
						+ rs.getString("branchAddress"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return branchList;
	}

	public void checkOutBook(int cardNo) {

		System.out.println("Pick the Branch you want to check out from: \n");
		branchList = getListOfBranches();
		System.out.println(branchList.size() + 1 + ") Quit to previous\n");
		int option = scan.nextInt();
		if (option < branchList.size() + 1) {
			Branch branch = branchList.get(option - 1);
			System.out.println("Pick the Book you want to check out\n");
			bookList = getExistedBooksOfBranch(branch);
			System.out.println(bookList.size() + 1 + ") Quit to previous\n");
			int option2 = scan.nextInt();

			if (option2 < bookList.size() + 1) {
				Book book = bookList.get(option2 - 1);
				updateLoan_CheckOut(book, branch, cardNo);
				updateNoOfCopiesOfBook_CheckOut(book, branch);

			}

		}

	}

	public List<Book> getExistedBooksOfBranch(Branch branch) {
		bookList = new ArrayList<Book>();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select bk.title, bk.bookId,bk.pubId "
							+ "from tbl_book bk join tbl_book_copies bkc on bk.bookId = bkc.bookId "
							+ "join tbl_library_branch lb on lb.branchId = bkc.branchId "
							+ "where lb.branchId = ? and bkc.noOfCopies > 0");
			stmt.setInt(1, branch.getBranchId());
			ResultSet rs = stmt.executeQuery();

			int index = 1;

			while (rs.next()) {
				System.out.print(index++ + ") ");
				bookList.add(new Book(rs.getString("title"), rs
						.getInt("bookId"), rs.getInt("pubId")));
				System.out.println(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return bookList;
	}

	public void updateLoan_CheckOut(Book book, Branch branch, int cardNo) {
		try {
			// create our java preparedstatement using a sql update query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO tbl_book_loans(bookId,branchId,cardNo,dateOut,dueDate) values"
							+ "(?,?,?,CURDATE(),DATE_ADD(CURDATE() , INTERVAL 1 WEEK))");

			// set the preparedstatement parameters

			ps.setInt(1, book.getBookId());
			ps.setInt(2, branch.getBranchId());
			ps.setInt(3, cardNo);

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
	}

	public void updateLoan_Return(Book book, Branch branch, int cardNo) {
		try {
			// create our java preparedstatement using a sql update query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("delete from tbl_book_loans where bookId=? and branchId=? and cardNo=?");

			// set the preparedstatement parameters

			ps.setInt(1, book.getBookId());
			ps.setInt(2, branch.getBranchId());
			ps.setInt(3, cardNo);

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
	}

	public void updateNoOfCopiesOfBook_CheckOut(Book book, Branch branch) {
		try {
			// create our java preparedstatement using a sql update query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("UPDATE tbl_book_copies SET noOfCopies = noOfCopies - 1 WHERE branchId = ? AND bookId = ?");

			// set the preparedstatement parameters

			ps.setInt(1, branch.getBranchId());
			ps.setInt(2, book.getBookId());

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
	}

	public void updateNoOfCopiesOfBook_Return(Book book, Branch branch) {
		try {
			// create our java preparedstatement using a sql update query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 WHERE branchId = ? AND bookId = ?");

			// set the preparedstatement parameters

			ps.setInt(1, branch.getBranchId());
			ps.setInt(2, book.getBookId());

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
	}

	public void returnBook(int cardNo) {
		bookList = new ArrayList<Book>();
		branchList = new ArrayList<Branch>();
		System.out
				.println("Please select book title and brancName you want the book to be returned");
		getBooksCheckOutByUser(cardNo, branchList, bookList);
		System.out.println(bookList.size() + 1 + ") Quit to Previous");
		int option = scan.nextInt();
		if (option < bookList.size() + 1) {
			Book book = bookList.get(option - 1);
			Branch branch = branchList.get(option - 1);
			updateLoan_Return(book, branch, cardNo);
			updateNoOfCopiesOfBook_Return(book, branch);
		}

	}

	public void getBooksCheckOutByUser(int cardNo, List<Branch> branchList,
			List<Book> bookList) {

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select title, branchName,bk.bookId,bk.pubId,bl.branchId,branchAddress "
							+ "from tbl_book bk join tbl_book_loans bl on bk.bookId = bl.bookId "
							+ "join tbl_library_branch lb on bl.branchId = lb.branchId "
							+ "join tbl_borrower borr on bl.cardNo = borr.cardNo "
							+ "where bl.cardNo = ?");

			stmt.setInt(1, cardNo);
			ResultSet rs = stmt.executeQuery();

			int index = 1;

			while (rs.next()) {
				System.out.print(index++ + ") ");
				bookList.add(new Book(rs.getString("title"), rs
						.getInt("bookId"), rs.getInt("pubId")));
				branchList
						.add(new Branch(rs.getInt("branchId"), rs
								.getString("branchName"), rs
								.getString("branchAddress")));
				System.out.println(rs.getString("title")
						+ " -----Checked Out From------"
						+ rs.getString("branchName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}
