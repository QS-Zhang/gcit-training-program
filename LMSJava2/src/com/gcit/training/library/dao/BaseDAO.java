package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO {

	protected Connection conn = null;

	public BaseDAO() {
		super();
	}

	public int saveReturnGen(String query, Object[] vals) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS);
		loopStatement(vals, stmt);
		stmt.execute();
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs != null && rs.next()) {
			return rs.getInt(1);
		} else {
			return -1;
		}
	}

	public void save(String query, Object[] vals) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		loopStatement(vals, stmt);
		stmt.execute();
	}

	private void loopStatement(Object[] vals, PreparedStatement stmt)
			throws SQLException {
		int loop = 1;
		for (Object obj : vals) {
			if(obj == null)
				stmt.setNull(loop, Types.NULL);
			else 
				stmt.setObject(loop, obj);

			loop++;
		}
	}
	
	public List<Object> readAll(String query, Object[] vals) throws SQLException
	{
		List<Object> objects = new ArrayList<Object>();
		
		PreparedStatement stmt = conn.prepareStatement(query);
		loopStatement(vals,stmt);
		
		ResultSet rs = stmt.executeQuery();
		
		int  loop = 0;
		while(rs.next())
		{
			objects.get(loop++).getClass();
		}
		
		
		return objects;
	}
	
	
	

}