package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;

public class BookDAO extends BaseDAO {

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

		if (book.getAuthors() != null && book.getAuthors().size() > 0) {
			for (Author a : book.getAuthors()) {
				save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
						new Object[] { bookId, a.getAuthorId() });
			}
		}
	}

	public void update(Book book) throws SQLException {
		
		save("update tbl_book set title = ? where bookId = ?" , new Object[]{book.getTitle(), book.getBookId()});
		
		//Update Publisher
		if(book.getPublisher() !=null)
		{
			save("update tbl_book set pubID = ? where bookId = ?", new Object[]{book.getPublisher().getPublisherId(), book.getBookId()});
		}
	}

	public void delete(Book book) throws SQLException {
		//Cascade
		
		save("delete from tbl_book where bookId = ?", new Object[]{book.getBookId()});
		
	}
	
	public List<Book> readAll(String query, Object[] vals) throws SQLException
	{
		
		List<Book> bookList = new ArrayList<Book>();
		
		return bookList;
	}
}
