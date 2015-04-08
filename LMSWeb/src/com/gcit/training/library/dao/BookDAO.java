package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Genre;
import com.gcit.training.library.domain.Publisher;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Book book) throws SQLException {
		int bookId = -1;
		if (book.getPublisher() == null) {
			bookId = saveReturnGen(
					"insert into tbl_book (title, pubId) values (?,?)",
					new Object[] { book.getTitle(), null });
		} else {
			bookId = saveReturnGen(
					"insert into tbl_book (title, pubId) values (?,?)",
					new Object[] { book.getTitle(),
							book.getPublisher().getPublisherId() });
		}

		if (book.getGenres() != null && book.getGenres().size() > 0) {
			for (Genre g : book.getGenres()) {
				save("insert into tbl_book_genres(bookId,genre_id) values(?,?)",
						new Object[] { bookId, g.getGenreId() });
			}
		}

		if (book.getAuthors() != null && book.getAuthors().size() > 0) {
			for (Author a : book.getAuthors()) {
				save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
						new Object[] { bookId, a.getAuthorId() });
			}
		}

	}

	public void update(Book book) throws SQLException {
		save("update tbl_book set title = ? where bookId = ?", new Object[] {
				book.getTitle(), book.getBookId() });

		// Update Publisher
		if (book.getPublisher() != null) {
			save("update tbl_book set pubId = ? where bookId = ?",
					new Object[] { book.getPublisher().getPublisherId(),
							book.getBookId() });
		}
		else
		{
			save("update tbl_book set pubId = ? where bookId = ?",
			new Object[] { null,
					book.getBookId() });
		}
	}

	public void delete(Book book) throws SQLException {
		save("delete from tbl_book where bookId = ?",
				new Object[] { book.getBookId() });
	}

	public Book getOne(int bookId) throws SQLException {

		List<Book> bookList = (List<Book>) read(
				"select * from tbl_book where bookId = ?",
				new Object[] { bookId });
		if (bookList != null && bookList.size() > 0) {
			return bookList.get(0);
		} else {
			return null;
		}
	}

	public List<Book> getAll() throws SQLException {
		return (List<Book>) read("select * from tbl_book");
	}

	@Override
	public List<Book> mapResult(ResultSet rs) throws SQLException {
		List<Book> list = new ArrayList<Book>();

		AuthorDAO aDAO = new AuthorDAO(conn);
		PublisherDAO pDAO = new PublisherDAO(conn);
		GenreDAO gDAO = new GenreDAO(conn);

		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));

			List<Author> authorList = (List<Author>) aDAO
					.readFirstLevel("select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
							new Object[] { b.getBookId() });

			List<Genre> gList = (List<Genre>) gDAO
					.readFirstLevel("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)",
							new Object[] { b.getBookId() });

			b.setAuthors(authorList);
			b.setGenres(gList);
			b.setPublisher(pDAO.getOne(rs.getInt("pubId")));

			list.add(b);
		}
		return list;
	}
	
	
	@Override
	public List<Book> mapFirstLevelResult(ResultSet rs) throws SQLException {
		List<Book> bookList = new ArrayList<Book>();
		while(rs.next())
		{
			Book book = new Book();
			Publisher pub = null;
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			int pubId = rs.getInt("pubId");
			if(!rs.wasNull())
			{
				pub = new Publisher();
				pub.setPublisherId(pubId);
				book.setPublisher(pub);
			}
			bookList.add(book);
			
		}
		return bookList;
	}

	public List<Book> getBooksByName(String bookToSearch) throws SQLException {
	
		bookToSearch = "%" + bookToSearch + "%";
		
		return (List<Book>) read(
				"select * from tbl_book where title like ?",
				new Object[] { bookToSearch });
	}


	public List<Book> page(int pageNo) throws SQLException {
		return (List<Book>) read("select * from tbl_book LIMIT " + (pageNo-1)*5 + ",5");
	}

	public int count() throws SQLException {
		return count("select count(*) from tbl_book");
	}
}
