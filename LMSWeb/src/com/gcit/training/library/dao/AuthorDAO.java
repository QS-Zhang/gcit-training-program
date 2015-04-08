package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Publisher;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Author author) throws SQLException {
		save("insert into tbl_author (authorName) values (?)",
				new Object[] { author.getAuthorName() });
	}

	public void update(Author author) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void delete(Author author) throws SQLException {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}


	public List<Author> getAll() throws SQLException {
		return (List<Author>) read("select * from tbl_author");
	}

	public Author getOne(int authorId) throws SQLException {
		List<Author> list = (List<Author>) read(
				"select * from tbl_author where authorId = ?",
				new Object[] { authorId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Author> mapResult(ResultSet rs) throws SQLException {
		List<Author> authorList = new ArrayList<Author>();
		BookDAO bkDAO = new BookDAO(conn);

		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));

			List<Book> bookList = (List<Book>) bkDAO
					.readFirstLevel(
							"select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)",
							new Object[] { author.getAuthorId() });

			author.setBooks(bookList);

			authorList.add(author);

		}
		return authorList;
	}

	@Override
	public List<Author> mapFirstLevelResult(ResultSet rs) throws SQLException {
		List<Author> authorList = new ArrayList<Author>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));

			authorList.add(author);
		}
		return authorList;
	}
	public List<Author> getAuthorsByName(String authorToSearch)
			throws SQLException {
		authorToSearch = "%" + authorToSearch + "%";
		return (List<Author>) read(
				"select * from tbl_author where authorName like ?",
				new Object[] { authorToSearch });
	}

	public List<Author> page(int pageNo) throws SQLException {
		return (List<Author>) read("select * from tbl_author LIMIT " + (pageNo-1)*5 + ",5");
	}

	public int count() throws SQLException {
		return count("select count(*) from tbl_author");
	}
}
