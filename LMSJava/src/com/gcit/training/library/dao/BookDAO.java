package com.gcit.training.library.dao;

import java.sql.Connection;


public class BookDAO extends BaseDAO {
	public BookDAO(Connection conn )
	{
		this.conn = conn;
	}
}
