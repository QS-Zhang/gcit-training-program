package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.Borrower;
import com.gcit.training.library.domain.Publisher;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(Borrower b) throws SQLException {
		save("Insert into tbl_borrower(name, address, phone) values(?,?,?)",
				new Object[] { b.getBorrowerName(), b.getBorrowerAddress(),
						b.getBorrowerPhone() });
	}

	public void update(Borrower b) throws SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ? ",
				new Object[] { b.getBorrowerName(), b.getBorrowerAddress(),
						b.getBorrowerPhone(), b.getCardNo() });

	}
	
	public void delete(Borrower b) throws SQLException{
		save("delete from tbl_borrower where cardNo = ?",new Object[]{b.getCardNo()});
	}
	
	public Borrower getOne(int cardNo) throws SQLException
	{
		
		List<Borrower> borrowerList = (List<Borrower>) read("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo});
		if(borrowerList != null && borrowerList.size()>0)
		{
			return borrowerList.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public List<Borrower> getAll() throws SQLException
	{
		return (List<Borrower>) read("select * from tbl_borrower");
	}
	@Override
	public List<?> mapResult(ResultSet rs) throws SQLException {
		List<Borrower> list = new ArrayList<Borrower>();
		while (rs.next()) {
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setBorrowerName(rs.getString("name"));
			b.setBorrowerAddress(rs.getString("address"));
			b.setBorrowerPhone(rs.getString("phone"));

			list.add(b);
		}

		return list;
	}

	@Override
	public List<?> mapFirstLevelResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Borrower> getBorrowerByName(String borrowerToSearch) throws SQLException {
		borrowerToSearch = "%" + borrowerToSearch + "%";

		return (List<Borrower>) read("select * from tbl_borrower where name like ?",
				new Object[] { borrowerToSearch });
	}

	public List<Borrower> page(int pageNo) throws SQLException {
		return (List<Borrower>) read("select * from tbl_borrower LIMIT " + (pageNo - 1)
				* 5 + ",5");
	}

	public int count() throws SQLException {
		return count("select count(*) from tbl_borrower");
	}

	

}
