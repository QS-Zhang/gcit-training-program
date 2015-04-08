package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Genre;


public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Genre genre) throws SQLException {
		save("insert into tbl_genre (genre_name) values (?)",
				new Object[] { genre.getGenreName() });
	}

	public void update(Genre genre) throws SQLException {
		save("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void delete(Genre genre) throws SQLException {
		save("delete from tbl_genre where genre_id = ?",
				new Object[] { genre.getGenreId() });
	}

	public List<Genre> getAll() throws SQLException {
		return (List<Genre>) read("select * from tbl_genre");
	}

	public Genre getOne(int genre_Id) throws SQLException {
		List<Genre> list = (List<Genre>) read(
				"select * from tbl_genre where genre_Id = ?",
				new Object[] { genre_Id });

		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Genre> mapResult(ResultSet rs) throws SQLException {
		
		List<Genre> genreList = new ArrayList<Genre>();
		BookDAO bkDAO = new BookDAO(conn);

		while(rs.next())
		{
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			
			List<Book> bookList = (List<Book>) bkDAO.readFirstLevel("select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_Id = ?)",
					new Object[] {genre.getGenreId()});
			
			genre.setBooks(bookList);
			
			genreList.add(genre);

		}
		return genreList;
	}

	@Override
	public List<?> mapFirstLevelResult(ResultSet rs) throws SQLException {
		List<Genre> genreList = new ArrayList<Genre>();
		while(rs.next())
		{
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genreList.add(genre);
		}
		return genreList;
	}
	public List<Genre> getGenresByName(String genreToSearch)
			throws SQLException {
		genreToSearch = "%" + genreToSearch + "%";
		return (List<Genre>) read(
				"select * from tbl_genre where genre_name like ?",
				new Object[] { genreToSearch });
	}

	public List<Genre> page(int pageNo) throws SQLException {
		return (List<Genre>) read("select * from tbl_genre LIMIT " + (pageNo-1)*5 + ",5");
	}

	public int count() throws SQLException {
		return count("select count(*) from tbl_genre");
	}



}
