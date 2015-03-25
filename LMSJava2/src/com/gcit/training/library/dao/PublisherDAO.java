package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gcit.training.library.domain.Publisher;

public class PublisherDAO extends BaseDAO {

	public PublisherDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Publisher publisher) throws SQLException {
		save("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)",
				new Object[] { publisher.getPublisherName() });
	}

	public void update(Publisher publisher) throws SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where authorId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherId() });
	}

	public void delete(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}



	@Override
	public List<?> mapResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
