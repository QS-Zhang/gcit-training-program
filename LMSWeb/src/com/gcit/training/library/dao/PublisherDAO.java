package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Publisher publisher) throws SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhone() });
	}

	public void update(Publisher publisher) throws SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhone(),
						publisher.getPublisherId() });
	}

	public void delete(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> getAll() throws SQLException {
		return (List<Publisher>) read("select * from tbl_publisher");
	}

	public Publisher getOne(int publisherId) throws SQLException {
		List<Publisher> list = (List<Publisher>) read(
				"select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Publisher> mapResult(ResultSet rs) throws SQLException {
		List<Publisher> list = new ArrayList<Publisher>();
		BookDAO bkDAO = new BookDAO(conn);

		while (rs.next()) {
			Publisher a = new Publisher();
			a.setPublisherId(rs.getInt("publisherId"));
			a.setPublisherName(rs.getString("publisherName"));
			a.setPublisherAddress(rs.getString("publisherAddress"));
			a.setPublisherPhone(rs.getString("publisherPhone"));

			List<Book> bookList = (List<Book>) bkDAO.readFirstLevel(
					"select * from tbl_book where pubId = ?",
					new Object[] { a.getPublisherId() });

			a.setBooks(bookList);
			list.add(a);
		}
		return list;
	}

	@Override
	public List<Publisher> mapFirstLevelResult(ResultSet rs)
			throws SQLException {
		List<Publisher> pubList = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));

			pubList.add(pub);

		}
		return pubList;
	}

	public List<Publisher> getPublisherByName(String publisherToSearch) throws SQLException {
		publisherToSearch = "%" + publisherToSearch + "%";

		return (List<Publisher>) read("select * from tbl_publisher where publisherName like ?",
				new Object[] { publisherToSearch });
	}

	public List<Publisher> page(int pageNo) throws SQLException {
		return (List<Publisher>) read("select * from tbl_publisher LIMIT " + (pageNo - 1)
				* 5 + ",5");
	}

	public int count() throws SQLException {
		return count("select count(*) from tbl_publisher");
	}

}
