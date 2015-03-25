package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
	
	public List<?> read(String query, Object[] vals) throws Exception {

			PreparedStatement stmt = conn.prepareStatement(query);
			int count = 1;
			if(vals != null) {
				for (Object obj : vals) {
					stmt.setObject(count, obj);
					count++;
				}
			}
			ResultSet rs = stmt.executeQuery();
			
			return mapResult(rs);

		} 

	
	public abstract List<?> mapResult(ResultSet rs) throws SQLException;


}