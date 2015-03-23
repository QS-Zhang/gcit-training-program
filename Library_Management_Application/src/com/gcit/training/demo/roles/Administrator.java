package com.gcit.training.demo.roles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.training.demo.resource.Author;
import com.gcit.training.demo.resource.Book;
import com.gcit.training.demo.resource.Borrower;
import com.gcit.training.demo.resource.Branch;
import com.gcit.training.demo.resource.Publisher;

public class Administrator {

	private List<Book> bookList;
	private List<Branch> branchList;
	private List<Author> authorList;
	private List<Publisher> publisherList;
	private List<Borrower> borrowerList;
	private Scanner scan = new Scanner(System.in);

	public void process(int option) {

		switch (option) {
		case 1:
			process_book();
			break;
		case 2:
			process_author();
			break;
		case 3:
			process_publisher();
			break;
		case 4:
			process_branch();
			break;
		case 5:
			process_borrower();
			break;
		case 6:
			process_override_dueDate();
		default:
			break;

		}

	}

	public void process_book() {
		System.out
				.println("1) Add Book\n2)Update Book\n3)Delete Book\n4)Quit To Previous\n");
		int option = scan.nextInt();
		if (option == 1) {
			System.out.println("Please select a publisher for your book: ");
			publisherList = getAllPublishers();
			System.out.println(publisherList.size() + 1 + ") Quit To Previous");

			int index = scan.nextInt();
			if (index < publisherList.size() + 1) {
				Publisher pub = publisherList.get(index - 1);
				System.out.println("Please select an author for your book: ");

				authorList = getAllAuthors();
				System.out
						.println(authorList.size() + 1 + ") Quit To Previous");
				int index2 = scan.nextInt();
				if (index < authorList.size() + 1) {
					Author au = authorList.get(index2 - 1);

					System.out.println("Please input a title for the book:");
					String title = scan.next();
					Book bk = new Book(title, 0, pub.getPublisherId());
					addBook(bk, true);
					bk.setBookId(getBook(bk.getTitle()).getBookId());
					addBook_Author(bk, au);

				} else {
					process_book();
				}

			} else {
				process_book();
			}
		} else if (option == 2) {
			System.out
					.println("Please select a book that you want to update: ");
			bookList = getAllBooks();
			System.out.println(bookList.size() + 1 + ") Quit To Previous");

			int index = scan.nextInt();
			if (index < bookList.size() + 1) {
				Book book = bookList.get(index - 1);
				System.out.println("Please select a publisher for your book: ");
				publisherList = getAllPublishers();
				System.out.println(publisherList.size() + 1
						+ ") Quit To Previous");
				if (index < publisherList.size() + 1) {
					Publisher pub = publisherList.get(index - 1);
					System.out
							.println("Please select an author for your book: ");

					authorList = getAllAuthors();
					System.out.println(authorList.size() + 1
							+ ") Quit To Previous");
					int index2 = scan.nextInt();
					if (index < authorList.size() + 1) {
						Author au = authorList.get(index2 - 1);
						System.out
								.println("Please input a title for the book:");
						String title = scan.next();
						book.setTitle(title);
						book.setPubId(pub.getPublisherId());
						updateBook(book, true);
						updateBook_Author(book, au);
					}

				}
			}
		} else if (option == 3) {
			System.out
					.println("Please select a book that you want to delete: ");
			bookList = getAllBooks();
			System.out.println(bookList.size() + 1 + ") Quit To Previous");

			int index = scan.nextInt();
			if (index < bookList.size() + 1) {
				Book book = bookList.get(index - 1);
				deleteBook(book);
			}
		}

	}

	public void process_override_dueDate() {
		System.out.println("Please select a borrower first: ");
		borrowerList = getAllBorrowers();
		System.out.println(borrowerList.size() + 1 + ") Quit To Previous");

		int index = scan.nextInt();
		if (index < borrowerList.size() + 1) {
			Borrower borr = borrowerList.get(index - 1);
			overrideDueDate(borr);

		}
	}

	public void process_branch() {
		System.out
				.println("Pretty simple.Check out implemented add/update/delete branch method\n");
	}

	public void process_author() {
		System.out
				.println("Pretty simple.Check out implemented add/update/delete author methods\n");
	}

	public void process_publisher() {
		System.out
				.println("Pretty simple.Check out implemented add/update/delete publisher methods\n");
	}

	public void process_borrower() {
		System.out
				.println("Pretty simple.Check out implemented add/update/delete borrower method");
	}

	public void addBook(Book bk, boolean hasPub) {

		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			if (hasPub) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO tbl_book (title,pubId) VALUES(?,?)");

				stmt.setString(1, bk.getTitle());
				stmt.setInt(2, bk.getPubId());
				stmt.executeUpdate();
			} else {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO tbl_book(tile) VALUES(?)");
				stmt.setString(1, bk.getTitle());
				stmt.executeUpdate();
			}

			System.out
					.println("Successfully adding a new book but the book still needs author (and publisher)\n");

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void addBook_Author(Book book, Author au) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("insert into tbl_book_authors(bookId,authorId) values(?,?)");

			stmt.setInt(1, book.getBookId());
			stmt.setInt(2, au.getAuthorId());
			stmt.execute();
			System.out
					.println("Successfully adding book_author connection of the new book\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateBook_Author(Book book, Author au) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_book_authors set authorId = ? where bookId = ?");

			stmt.setInt(1, au.getAuthorId());

			stmt.setInt(2, book.getBookId());
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateBook(Book bk, boolean hasPub) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			if (hasPub) {
				PreparedStatement stmt = conn
						.prepareStatement("update tbl_book set title =?, pubId = ? where bookId = ? ");

				stmt.setString(1, bk.getTitle());
				stmt.setInt(2, bk.getPubId());
				stmt.setInt(3, bk.getBookId());
				stmt.executeUpdate();

			} else {
				PreparedStatement stmt = conn
						.prepareStatement("update tbl_book set title =? where bookId = ? ");

				stmt.setString(1, bk.getTitle());

				stmt.setInt(2, bk.getBookId());
				stmt.executeUpdate();
			}
			System.out.println("Successfully updating a book!");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void deleteBook(Book bk) {
		delelteBook_Loans(bk);
		deleteBook_Copies(bk);
		deleteBook_Authors(bk);
		deleteBook_Genres(bk);
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book where bookId = ?");

			stmt.setInt(1, bk.getBookId());
			stmt.executeUpdate();

			System.out.println("Deleting a book successfully\n");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void delelteBook_Loans(Book bk) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_loans where bookId = ?");

			stmt.setInt(1, bk.getBookId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBook_Copies(Book bk) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_copies where bookId = ?");

			stmt.setInt(1, bk.getBookId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBook_Authors(Book bk) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_authors where bookId = ?");

			stmt.setInt(1, bk.getBookId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBook_Genres(Book bk) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_genres where bookId = ?");

			stmt.setInt(1, bk.getBookId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public List<Author> getAllAuthors() {
		authorList = new ArrayList<Author>();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_author");

			ResultSet rs = stmt.executeQuery();

			int index = 1;

			while (rs.next()) {
				System.out.print(index++ + ") ");
				authorList.add(new Author(rs.getString("authorName"), rs
						.getInt("authorId")));
				System.out.println(rs.getString("authorName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return authorList;
	}

	public List<Publisher> getAllPublishers() {
		publisherList = new ArrayList<Publisher>();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_publisher");

			ResultSet rs = stmt.executeQuery();

			int index = 1;

			while (rs.next()) {
				System.out.print(index++ + ") ");
				publisherList.add(new Publisher(rs.getInt("publisherId"), rs
						.getString("publisherName"), rs
						.getString("publisherAddress"), rs
						.getString("publisherPhone")));
				System.out.println(rs.getString("publisherName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return publisherList;
	}

	public List<Borrower> getAllBorrowers() {
		borrowerList = new ArrayList<Borrower>();

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_borrower");

			ResultSet rs = stmt.executeQuery();

			int index = 1;

			while (rs.next()) {
				System.out.print(index++ + ") ");
				borrowerList.add(new Borrower(rs.getInt("cardNo"), rs
						.getString("name"), rs.getString("address"), rs
						.getString("phone")));
				System.out.println(rs.getString("name") + " "
						+ rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return borrowerList;
	}

	public Book getBook(String title) {
		Book bk = new Book();
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");
			PreparedStatement stmt = conn
					.prepareStatement("select * from tbl_book where title = ?");

			stmt.setString(1, title);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bk.setTitle(rs.getString("title"));
				bk.setBookId(rs.getInt("bookId"));
				bk.setPubId(rs.getInt("pubId"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return bk;
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

	public void addAuthor(Author au) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO tbl_author(authorName) VALUES(?)");

			stmt.setString(1, au.getAuthorName());
			stmt.executeUpdate();

			System.out.println("Successfully adding a new author!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void updateAuthor(Author au) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_author set authorName =? where authorId = ? ");

			stmt.setString(1, au.getAuthorName());
			stmt.setInt(2, au.getAuthorId());
			stmt.executeUpdate();

			System.out.println("Successfully updating an author!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteAtuhor(Author au) {
		deleteBook_Author(au);
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_author where au");

			stmt.setString(1, au.getAuthorName());
			stmt.executeUpdate();

			System.out.println("Successfully deleting an author!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBook_Author(Author au) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_authors where authorId = ?");

			stmt.setInt(1, au.getAuthorId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void addPublisher(Publisher pub) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO tbl_publisher(publisherName,publisherAddress,publisherPhone) VALUES(?,?,?)");

			stmt.setString(1, pub.getPublisherName());
			stmt.setString(2, pub.getPublisherAddress());
			stmt.setString(3, pub.getPublisherPhone());

			stmt.executeUpdate();

			System.out.println("Successfully adding a publisher");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void updatePublisher(Publisher pub) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_publisher set publisherName =?, publisherAddress =?,publisherPhone=? where publisherId = ? ");

			stmt.setString(1, pub.getPublisherName());
			stmt.setString(2, pub.getPublisherAddress());
			stmt.setString(3, pub.getPublisherPhone());
			stmt.setInt(4, pub.getPublisherId());
			stmt.executeUpdate();

			System.out.println("Successfully updating a publisher!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deletePublisher(Publisher pub) {
		updatePublisher_Book(pub);
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_publisher where publisherId = ?");

			stmt.setInt(1, pub.getPublisherId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void updatePublisher_Book(Publisher pub) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_book set pubId = NULL where pubId = ?");

			stmt.setInt(1, pub.getPublisherId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void addBorrower(Borrower borr) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO tbl_borrower(name,address,phone) VALUES(?,?,?)");

			stmt.setString(1, borr.getName());
			stmt.setString(2, borr.getAddress());
			stmt.setString(3, borr.getPhone());

			stmt.executeUpdate();

			System.out.println("Successfully adding a borrower");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void updateBorrower(Borrower borr) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_borrower set name =?, address =?,phone=? where cardNo = ? ");

			stmt.setString(1, borr.getName());
			stmt.setString(2, borr.getAddress());
			stmt.setString(3, borr.getPhone());
			stmt.setInt(4, borr.getCardNo());

			stmt.executeUpdate();

			System.out.println("Successfully updating a borrower!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBorrower(Borrower borr) {
		deleteBorrower_Loan(borr);
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_borrower where cardNo = ?");

			stmt.setInt(1, borr.getCardNo());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBorrower_Loan(Borrower borr) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_loans where CardNo = ?");

			stmt.setInt(1, borr.getCardNo());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void addBranch(Branch branch) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO tbl_branch(branchName,branchAddress) VALUES(?,?)");

			stmt.setString(1, branch.getBranchName());
			stmt.setString(2, branch.getBranchAddress());

			stmt.executeUpdate();

			System.out.println("Successfully adding a branch");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void udpateBranch(Branch branch) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_branch set branchName =?, branchAddress =? where branchId = ? ");

			stmt.setString(1, branch.getBranchName());
			stmt.setString(2, branch.getBranchAddress());
			stmt.setInt(3, branch.getBranchId());

			stmt.executeUpdate();

			System.out.println("Successfully updating a branch!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBranch(Branch branch) {
		deleteBranch_Loan(branch);
		deleteBranch_Copies(branch);
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_branch where branchId = ?");

			stmt.setInt(1, branch.getBranchId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBranch_Loan(Branch branch) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_loans where branchId = ?");

			stmt.setInt(1, branch.getBranchId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void deleteBranch_Copies(Branch branch) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("delete from tbl_book_copies where branchId = ?");

			stmt.setInt(1, branch.getBranchId());
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void updateBookDueDate(int cardNo, Book book, Branch branch,
			String date) {
		try {

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library", "root", "");

			PreparedStatement stmt = conn
					.prepareStatement("update tbl_book_loans set dueDate = ? where cardNo = ? and bookId = ? and branchId = ?");

			stmt.setString(1, date);
			stmt.setInt(2, cardNo);
			stmt.setInt(3, book.getBookId());
			stmt.setInt(4, branch.getBranchId());

			stmt.executeUpdate();

			System.out.println("Successfully overriding a due date!");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void overrideDueDate(Borrower borr) {
		bookList = new ArrayList<Book>();
		branchList = new ArrayList<Branch>();
		System.out
				.println("Please select book title and brancName you want to override its due date");
		getBooksCheckOutByUser(borr, branchList, bookList);
		System.out.println(bookList.size() + 1 + ") Quit to Previous");
		int option = scan.nextInt();
		if (option < bookList.size() + 1) {
			Book book = bookList.get(option - 1);
			Branch branch = branchList.get(option - 1);
			System.out.println("Please enter a new due date: ");
			String date = scan.next();
			updateBookDueDate(borr.getCardNo(), book, branch, date);
		}
	}

	public void getBooksCheckOutByUser(Borrower borr, List<Branch> branchList,
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

			stmt.setInt(1, borr.getCardNo());
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
