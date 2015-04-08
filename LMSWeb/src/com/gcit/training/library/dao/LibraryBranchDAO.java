package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.library.domain.LibraryBranch;
import com.gcit.training.library.domain.Publisher;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {

	public LibraryBranchDAO(Connection connection) {
		this.conn = connection;
	}

	public void create(LibraryBranch b) throws SQLException {
		save("Insert into tbl_library_branch(branchName, branchAddress) values(?,?)",
				new Object[] { b.getBranchName(),b.getBranchAddress() });
	}

	public void update(LibraryBranch b) throws SQLException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ? ",
				new Object[] { b.getBranchName(),b.getBranchAddress(),b.getBranchId() });

	}

	public void delete(LibraryBranch b) throws SQLException {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { b.getBranchId() });
	}

	public LibraryBranch getOne(int branchId) throws SQLException {

		List<LibraryBranch> branchList = (List<LibraryBranch>) read(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId });
		if (branchList != null && branchList.size() > 0) {
			return branchList.get(0);
		} else {
			return null;
		}
	}

	public List<LibraryBranch> getAll() throws SQLException {
		return (List<LibraryBranch>) read("select * from tbl_library_branch");
	}

	@Override
	public List<?> mapResult(ResultSet rs) throws SQLException {
		List<LibraryBranch> list = new ArrayList<LibraryBranch>();
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			lb.setBranchId(rs.getInt("branchId"));
			list.add(lb);
		}

		return list;
	}

	@Override
	public List<?> mapFirstLevelResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LibraryBranch> getBranchByName(String branchToSearch) throws SQLException {
		branchToSearch = "%" + branchToSearch + "%";

		return (List<LibraryBranch>) read("select * from tbl_library_branch where branchName like ?",
				new Object[] { branchToSearch });
	}

	public List<LibraryBranch> page(int pageNo) throws SQLException {
		return (List<LibraryBranch>) read("select * from tbl_library_branch LIMIT " + (pageNo - 1)
				* 5 + ",5");
	}

	public int count() throws SQLException {
		return count("select count(*) from tbl_library_branch");
	}


}
