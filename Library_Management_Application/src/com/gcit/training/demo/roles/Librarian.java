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

public class Librarian {
	private List<Book> bookList;
	private List<Branch> branchList;
	private int numOfCopies;
	private Scanner scan;

	public List<Branch> getListOfBranches() {
		branchList = new ArrayList<Branch>();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_library_branch");

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

	public void updateBranch(Branch branch) {
		try {
			// create our java preparedstatement using a sql update query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?");

			// set the preparedstatement parameters
			ps.setString(1, branch.getBranchName());
			ps.setString(2, branch.getBranchAddress());
			ps.setInt(3, branch.getBranchId());

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
	}

	public List<Book> getAllBooks() {
		bookList = new ArrayList<Book>();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_book");

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

	public void updateBookCopiesInBranch(Book book, Branch branch,
			int numOfCopies) {

		try {
			// create our java preparedstatement using a sql update query
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement ps = conn
					.prepareStatement("UPDATE tbl_book_copies SET noOfCopies = ? WHERE branchId = ? AND bookId = ?");

			// set the preparedstatement parameters

			ps.setInt(1, numOfCopies);
			ps.setInt(2, branch.getBranchId());
			ps.setInt(3, book.getBookId());

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			// log the exception
			se.printStackTrace();
			;
		}
	}

	public int findBookCopiesOfBranch(Book book, Branch branch) {
		numOfCopies = 0;
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select noOfCopies from tbl_book_copies where bookId =? and branchId =?");
			stmt.setInt(1, book.getBookId());
			stmt.setInt(2, branch.getBranchId());
			ResultSet rs = stmt.executeQuery();

			int count = 1;
			while (rs.next()) {
				System.out.println(count++);
				System.out.println("Existing number of copies: "
						+ rs.getInt("noOfCopies"));
				numOfCopies = rs.getInt("noOfCopies");

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return numOfCopies;
	}

	public int Lib1() {
		int option;
		System.out.println("1) Enter Branch you manage\n"
				+ "2) Quite to previous\n");
		scan = new Scanner(System.in);
		option = scan.nextInt();
		return option;

	}

	public Branch lib2() {
		int option;
		branchList = getListOfBranches();
		System.out.println(branchList.size() + 1 + ") Quit To Previous");
		scan = new Scanner(System.in);
		Branch branch;
		option = scan.nextInt();
		if (option <= branchList.size()) {
			branch = branchList.get(option - 1);
		} else {
			branch = null;
		}
		return branch;
	}

	public boolean lib3(Branch branch) {
		System.out.println("1) Update the details of the Library\n"
				+ "2) Add copies of Book to the Branch\n"
				+ "3) Quit to previous");
		scan = new Scanner(System.in);
		int option = scan.nextInt();
		if (option == 1) {
			System.out
					.println("Please enter new branch name or enter N/A for no change: \n");
			String name = scan.next();
			System.out
					.println("Please enter new branch address or enter N/A for no change: \n");
			String address = scan.next();
			if (!name.equals("N/A") || !address.equals("N/A")) {
				if (name.equals("N/A")) {
					branch.setBranchAddress(address);
				} else {
					if (address.equals("N/A")) {
						branch.setBranchName(name);
					} else {
						branch.setBranchName(name);
						branch.setBranchAddress(address);
					}
				}
				updateBranch(branch);
				lib3(branch);
			} else
				lib3(branch);

		} else if (option == 2) {
			System.out
					.println("Pick the Book you want to add copies of, to your branch: \n");
			bookList = getAllBooks();
			System.out.println(bookList.size() + 1
					+ "Quit to cancel operation ");

			int subOption = scan.nextInt();
			if (subOption <= bookList.size()) {
				System.out
						.println("How many numberOfCopies do you want this book to have ");
				numOfCopies = scan.nextInt();
				Book book = bookList.get(subOption - 1);
				updateBookCopiesInBranch(book, branch, numOfCopies);
				lib3(branch);
			} else {
				lib3(branch);
			}
		} else {
			return false;
		}

		return true;

	}
}
