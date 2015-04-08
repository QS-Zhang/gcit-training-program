package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public abstract class BaseDAO<T> {

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
	
	public abstract List<?> mapResult(ResultSet rs) throws SQLException; 
	
	public abstract List<?> mapFirstLevelResult(ResultSet rs) throws SQLException;

	public List<?> read(String query) throws SQLException {
		return read(query, null);
	}


	public List<?> read(String query, Object[] vals) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		if(vals != null)
			loopStatement(vals, stmt);
		ResultSet rs = stmt.executeQuery();
		return mapResult(rs);
	}
	
	public int count(String query) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) 
			return rs.getInt(1);
		else
			return 0;
	}

	public List<?> readFirstLevel(String query) throws SQLException{
		return readFirstLevel(query,null);
	}
	
	public List<?> readFirstLevel(String query, Object[] vals) throws SQLException{
		PreparedStatement stmt = conn.prepareStatement(query);
		if(vals != null)
			loopStatement(vals, stmt);
		ResultSet rs = stmt.executeQuery();
		return mapFirstLevelResult(rs);
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
	
	

}