package com.gcit.training.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.training.library.dao.AuthorDAO;
import com.gcit.training.library.dao.BookDAO;
import com.gcit.training.library.dao.BookLoanDAO;
import com.gcit.training.library.dao.BorrowerDAO;
import com.gcit.training.library.dao.GenreDAO;
import com.gcit.training.library.dao.LibraryBranchDAO;
import com.gcit.training.library.dao.PublisherDAO;
import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.BookLoan;
import com.gcit.training.library.domain.Borrower;
import com.gcit.training.library.domain.Genre;
import com.gcit.training.library.domain.LibraryBranch;
import com.gcit.training.library.domain.Publisher;

public class AdministratorService extends BaseService {

	public void createInventory(Object obj) throws Exception {
		if (obj instanceof Author) {
			createAuthor((Author) obj);
		} else if (obj instanceof Publisher) {
			createPublisher((Publisher) obj);
		} else if (obj instanceof Book) {
			createBook((Book) obj);
		} else if (obj instanceof Borrower) {
			createBorrower((Borrower) obj);
		} else if (obj instanceof LibraryBranch) {
			createLibraryBranch((LibraryBranch) obj);
		}

	}

	public void updateInventory(Object obj) throws Exception {
		if (obj instanceof Author) {
			updateAuthor((Author) obj);
		} else if (obj instanceof Book) {
			updateBook((Book) obj);
		} else if (obj instanceof Publisher) {
			updatePublisher((Publisher) obj);
		} else if (obj instanceof Borrower) {
			updateBorrower((Borrower) obj);
		} else if (obj instanceof LibraryBranch) {
			updateLibraryBranch((LibraryBranch) obj);

		}
	}

	public void deleteInventory(Object obj) throws Exception {
		if (obj instanceof Author) {
			deleteAuthor((Author) obj);
		} else if (obj instanceof Book) {
			deleteBook((Book) obj);
		} else if (obj instanceof Publisher) {
			deletePublisher((Publisher) obj);
		} else if (obj instanceof Borrower) {
			deleteBorrower((Borrower) obj);
		} else if (obj instanceof LibraryBranch) {
			deleteLibraryBranch((LibraryBranch) obj);

		}
	}

	private void createBook(Book obj) throws Exception {
		if (obj != null) {
			if (obj.getTitle() == null || obj.getTitle().length() > 45) {
				throw new Exception(
						"Book Title cannot be blank and more than 45 chars");
			} else if (obj.getAuthors() == null || obj.getAuthors().size() == 0
					|| obj.getGenres() == null || obj.getGenres().size() == 0) {
				throw new Exception(
						"Book must have at least one author and one genre!");
			}
			Connection conn = getConnection();
			BookDAO bookDAO = new BookDAO(conn);
			try {
				bookDAO.create(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.close();
				conn = null;
			}
		}
	}

	private void createAuthor(Author obj) throws Exception {
		if (obj != null) {
			if (obj.getAuthorName() == null
					|| obj.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be blank and more than 45 characters!");
			}

			Connection conn = getConnection();
			AuthorDAO authorDAO = new AuthorDAO(conn);
			try {
				authorDAO.create(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.close();
				conn = null;
			}

		} else {
			throw new Exception("Author cannot be null or empty!");
		}

	}

	// Search
	public List<Author> searchAuthorByName(String authorToSearch)
			throws Exception {
		Connection conn = getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(conn);
			return aDAO.getAuthorsByName(authorToSearch);
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Book> searchBookByName(String bookToSearch) throws Exception {
		Connection conn = getConnection();
		try {
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.getBooksByName(bookToSearch);
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Publisher> searchPublisherByName(String publisherToSearch)
			throws Exception {
		Connection conn = getConnection();
		try {
			PublisherDAO pDAO = new PublisherDAO(conn);
			return pDAO.getPublisherByName(publisherToSearch);
		} finally {
			conn.close();
			conn = null;
		}
	}
	
	public List<Borrower> searchBorrowerByName(String borrowerToSearch) throws Exception
	{
		Connection conn = getConnection();
		try {
			BorrowerDAO pDAO = new BorrowerDAO(conn);
			return pDAO.getBorrowerByName(borrowerToSearch);
		} finally {
			conn.close();
			conn = null;
		}
	}
	
	public List<LibraryBranch> searchBranchByName(String branchToSearch) throws Exception
	{
		Connection conn = getConnection();
		try {
			LibraryBranchDAO pDAO = new LibraryBranchDAO(conn);
			return pDAO.getBranchByName(branchToSearch);
		} finally {
			conn.close();
			conn = null;
		}
	}
	// Page
	public List<Author> pageAuthors(int pageNo) throws Exception {
		Connection conn = getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(conn);
			return aDAO.page(pageNo);
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Book> pageBooks(int pageNo) throws Exception {
		Connection conn = getConnection();
		try {
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.page(pageNo);
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Publisher> pagePublisher(int pageNo) throws Exception {
		Connection conn = getConnection();
		try {
			PublisherDAO pDAO = new PublisherDAO(conn);
			return pDAO.page(pageNo);
		} finally {
			conn.close();
			conn = null;
		}
	}
	
	public List<Borrower> pageBorrower(int pageNo) throws Exception{
		Connection conn = getConnection();
		try {
			BorrowerDAO pDAO = new BorrowerDAO(conn);
			return pDAO.page(pageNo);
		} finally {
			conn.close();
			conn = null;
		}
	}
	
	public List<LibraryBranch> pageBranch(int pageNo) throws Exception{
		Connection conn = getConnection();
		try {
			LibraryBranchDAO pDAO = new LibraryBranchDAO(conn);
			return pDAO.page(pageNo);
		} finally {
			conn.close();
			conn = null;
		}
	}
	// Count
	public int countAuthors() throws Exception {
		Connection conn = getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(conn);
			return aDAO.count();
		} finally {
			conn.close();
			conn = null;
		}
	}	
	public int countBooks() throws Exception{
		Connection conn = getConnection();
		try {
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.count();
		} finally {
			conn.close();
			conn = null;
		}
	}
	public int countPublishers() throws Exception
	{
		Connection conn = getConnection();
		try {
			PublisherDAO pDAO = new PublisherDAO(conn);
			return pDAO.count();
		} finally {
			conn.close();
			conn = null;
		}
	}
	public int countBorrowers() throws Exception
	{
		Connection conn = getConnection();
		try {
			BorrowerDAO pDAO = new BorrowerDAO(conn);
			return pDAO.count();
		} finally {
			conn.close();
			conn = null;
		}
	}
	public int countBranches() throws Exception{
		Connection conn = getConnection();
		try {
			LibraryBranchDAO pDAO = new LibraryBranchDAO(conn);
			return pDAO.count();
		} finally {
			conn.close();
			conn = null;
		}
	}
	public int countGenres() throws Exception
	{
		Connection conn = getConnection();
		try {
			GenreDAO pDAO = new GenreDAO(conn);
			return pDAO.count();
		} finally {
			conn.close();
			conn = null;
		}
	}
	
	//Get One
	public Book getOneBook(int bookId) throws Exception {
		Connection conn = getConnection();
		try {
			BookDAO aDAO = new BookDAO(conn);
			return aDAO.getOne(bookId);
		} finally {
			conn.close();
			conn = null;
		}
	}

	public Publisher getOnePublisher(int publisherId) throws Exception {
		Connection conn = getConnection();
		try {
			PublisherDAO aDAO = new PublisherDAO(conn);
			List<Publisher> publisherList = (List<Publisher>) aDAO.getOne(publisherId);

			return publisherList.get(0);
		} finally {
			conn.close();
			conn = null;
		}
	}
	
	private void createPublisher(Publisher obj) throws Exception {
		if (obj != null) {
			if (obj.getPublisherName().length() == 0
					|| obj.getPublisherName().length() > 45) {
				throw new Exception(
						"Publisher Name cannot be blank and more than 45 chars");
			} else if (obj.getPublisherAddress().length() == 0
					|| obj.getPublisherAddress().length() > 45) {
				throw new Exception(
						"Publisher Address cannot be blank and more than 45 chars");
			} else if (obj.getPublisherPhone().length() == 0
					|| obj.getPublisherPhone().length() > 20) {
				throw new Exception(
						"Publisher Phone cannot be blank and more than 20 chars");
			}

			Connection conn = getConnection();

			try {
				PublisherDAO pubDAO = new PublisherDAO(conn);
				pubDAO.create(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.close();
				conn = null;
			}
		}

	}

	private void createBorrower(Borrower obj) throws Exception {
		if (obj != null) {
			if (obj.getBorrowerName().length() == 0
					|| obj.getBorrowerName().length() > 45) {
				throw new Exception(
						"Borrower Name cannot be blank and more than 45 chars");
			} else if (obj.getBorrowerAddress().length() == 0
					|| obj.getBorrowerAddress().length() > 45) {
				throw new Exception(
						"Borrower Address cannot be blank and more than 45 chars");
			} else if (obj.getBorrowerPhone().length() == 0
					|| obj.getBorrowerPhone().length() > 20) {
				throw new Exception(
						"Borrower Phone cannot be blank and more than 20 chars");
			}

			Connection conn = getConnection();

			try {
				BorrowerDAO borrDAO = new BorrowerDAO(conn);
				borrDAO.create(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}
		}

	}

	private void createLibraryBranch(LibraryBranch obj) throws Exception {
		if (obj != null) {
			if (obj.getBranchName().length() == 0
					|| obj.getBranchName().length() > 45) {
				throw new Exception(
						"Branch Name cannot be blank or more than 45 chars");
			} else if (obj.getBranchAddress().length() == 0
					|| obj.getBranchAddress().length() > 45) {
				throw new Exception(
						"Branch Address cannot be blank or more than 45 chars");
			}

			Connection conn = getConnection();

			try {
				LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
				lbDAO.create(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}
		}

	}

	private void updateBook(Book obj) throws Exception {
		if (obj != null) {
			if (obj.getTitle() == null || obj.getTitle().length() > 45) {
				throw new Exception(
						"Book Title cannot be blank or more than 45 chars");
			} else if (obj.getAuthors() == null || obj.getAuthors().size() == 0
					|| obj.getGenres() == null || obj.getGenres().size() == 0) {
				throw new Exception(
						"Book must have at least one author and one genre!");
			}
			Connection conn = getConnection();
			BookDAO bookDAO = new BookDAO(conn);
			try {
				bookDAO.update(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.close();
				conn = null;
			}
		} else {
			throw new Exception("Book cannot be null");
		}
	}

	private void updateAuthor(Author obj) throws Exception {
		if (obj != null) {
			if (obj.getAuthorName() == null
					|| obj.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be blank and more than 45 characters!");
			}

			Connection conn = getConnection();
			AuthorDAO authorDAO = new AuthorDAO(conn);
			try {
				authorDAO.update(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.close();
				conn = null;
			}

		} else {
			throw new Exception("Author cannot be null or empty!");
		}
	}

	private void updatePublisher(Publisher obj) throws Exception {
		if (obj != null) {
			if (obj.getPublisherName() == null
					|| obj.getPublisherName().length() > 45) {
				throw new Exception(
						"Publisher Name cannot be blank and more than 45 chars");
			} else if (obj.getPublisherAddress() == null
					|| obj.getPublisherAddress().length() > 45) {
				throw new Exception(
						"Publisher Address cannot be blank and more than 45 chars");
			} else if (obj.getPublisherPhone() != null
					|| obj.getPublisherPhone().length() > 20) {
				throw new Exception(
						"Publisher Phone cannot be blank and more than 20 chars");
			}

			Connection conn = getConnection();

			try {
				PublisherDAO pubDAO = new PublisherDAO(conn);
				pubDAO.update(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.close();
				conn = null;
			}
		}
	}

	private void updateBorrower(Borrower obj) throws Exception {
		if (obj != null) {
			if (obj.getBorrowerName() == null
					|| obj.getBorrowerName().length() > 45) {
				throw new Exception(
						"Borrower Name cannot be blank and more than 45 chars");
			} else if (obj.getBorrowerAddress() == null
					|| obj.getBorrowerAddress().length() > 45) {
				throw new Exception(
						"Borrower Address cannot be blank and more than 45 chars");
			} else if (obj.getBorrowerPhone() == null
					|| obj.getBorrowerPhone().length() > 20) {
				throw new Exception(
						"Borrower Phone cannot be blank and more than 20 chars");
			}

			Connection conn = getConnection();

			try {
				BorrowerDAO borrDAO = new BorrowerDAO(conn);
				borrDAO.update(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}
		}
	}

	private void updateLibraryBranch(LibraryBranch obj) throws Exception {
		if (obj != null) {
			if (obj.getBranchName() == null
					|| obj.getBranchName().length() > 45) {
				throw new Exception(
						"Branch Name cannot be blank and more than 45 chars");
			} else if (obj.getBranchAddress() == null
					|| obj.getBranchAddress().length() > 45) {
				throw new Exception(
						"Branch Address cannot be blank and more than 45 chars");
			}

			Connection conn = getConnection();

			try {
				LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
				lbDAO.update(obj);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}
		}
	}

	private void deleteBook(Book book) throws Exception {
		Connection conn = getConnection();
		BookDAO bDAO = new BookDAO(conn);
		if (book != null && book.getBookId() > 0) {
			try {

				bDAO.delete(book);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}
		} else {
			throw new Exception("Invalid Book!");
		}
	}

	private void deleteAuthor(Author author) throws Exception {
		Connection conn = getConnection();
		AuthorDAO aDAO = new AuthorDAO(conn);
		if (author != null && author.getAuthorId() > 0) {
			try {
				aDAO.delete(author);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}

		} else {
			throw new Exception("Invalid Author!");
		}

	}

	private void deleteBorrower(Borrower borr) throws Exception {
		Connection conn = getConnection();
		BorrowerDAO aDAO = new BorrowerDAO(conn);
		if (borr != null && borr.getCardNo() > 0) {
			try {
				aDAO.delete(borr);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}

		} else {
			throw new Exception("Invalid Borrower!");
		}

	}

	private void deletePublisher(Publisher pub) throws Exception {
		Connection conn = getConnection();
		PublisherDAO aDAO = new PublisherDAO(conn);
		if (pub != null && pub.getPublisherId() > 0) {
			try {
				aDAO.delete(pub);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}

		} else {
			throw new Exception("Invalid Borrower!");
		}

	}

	private void deleteLibraryBranch(LibraryBranch lb) throws Exception {
		Connection conn = getConnection();
		LibraryBranchDAO aDAO = new LibraryBranchDAO(conn);
		if (lb != null && lb.getBranchId() > 0) {
			try {
				aDAO.delete(lb);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.close();
				conn = null;
			}

		} else {
			throw new Exception("Invalid LibraryBranch!");
		}

	}

	public List<Book> getAllBooks() throws Exception {
		Connection conn = getConnection();
		try {
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.getAll();
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Publisher> getAllPublishers() throws Exception {
		Connection conn = getConnection();
		try {
			PublisherDAO pubDAO = new PublisherDAO(conn);
			return pubDAO.getAll();
		} finally {
			conn.close();
			conn = null;
		}
	}

	public Author getOneAuthor(int authorId) throws Exception {
		Connection conn = getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(conn);
			return aDAO.getOne(authorId);
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Author> getAllAuthors() throws Exception {
		Connection conn = getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(conn);
			return aDAO.getAll();
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Borrower> getAllBorrowers() throws Exception {
		Connection conn = getConnection();
		try {
			BorrowerDAO aDAO = new BorrowerDAO(conn);
			return aDAO.getAll();
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<LibraryBranch> getAllBranches() throws Exception {
		Connection conn = getConnection();
		try {
			LibraryBranchDAO aDAO = new LibraryBranchDAO(conn);
			return aDAO.getAll();
		} finally {
			conn.close();
			conn = null;
		}
	}

	public List<Genre> getAllGenres() throws Exception {
		Connection conn = getConnection();
		try {
			GenreDAO gDAO = new GenreDAO(conn);
			return gDAO.getAll();
		} finally {
			conn.close();
			conn = null;
		}
	}

	public void updateBookLoan(BookLoan bl) throws Exception {
		if (bl != null) {
			if (bl.getBook() != null && bl.getBranch() != null
					&& bl.getBorrower() != null) {
				Connection conn = getConnection();
				try {
					new BookLoanDAO(conn).update(bl);

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
