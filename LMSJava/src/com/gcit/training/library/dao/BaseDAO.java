package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseDAO {

	protected Connection conn = null;

	public BaseDAO() {
		super();
	}

	public void save(String query, Object[] vals) throws SQLException {
	
		PreparedStatement stmt = conn.prepareStatement(query);
		int count = 1;
		for(Object obj : vals)
		{
			stmt.setObject(count++, obj);
		}
		stmt.execute();
		
	}

}