package bigcloneeval.evaluation.cloneMatchingAlgorithms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bigcloneeval.evaluation.database.Clone;
import bigcloneeval.evaluation.database.Function;
import bigcloneeval.evaluation.database.Functions;
import bigcloneeval.evaluation.database.ToolsDB;

public class ExactMatcher implements CloneMatcher {
	
	private long toolid;
	
	private Connection conn;
	private PreparedStatement stmt;
	
	public String toString() {
		return "Exact Match";
	}

	public ExactMatcher(long toolid, String init) throws SQLException {
		this.toolid = toolid;
		if(!init.trim().equals("")) {
			throw new IllegalArgumentException("Does not take any parameters.");
		}
		init();
	}
	
	private void init() throws SQLException {
		String sql = "SELECT 1 FROM " + CloneMatcher.getTableName(this.toolid) + " where type1 = ? and name1 = ? and startline1 = ? and endline1 = ? and type2 = ? and name2 = ? and startline2 = ? and endline2 = ?";
		this.conn = ToolsDB.getConnection();
		this.stmt = conn.prepareStatement(sql);
	}
	
	@Override
	public boolean isDetected(Clone clone) throws SQLException {
		Clone alt = new Clone(clone.getFunction_id_two(), clone.getFunction_id_one());
		return isDetected_helper(clone) || isDetected_helper(alt);
	}
	
	private boolean isDetected_helper(Clone clone) throws SQLException {
		boolean retval = false;
		Function f1 = Functions.get(clone.getFunction_id_one());
		Function f2 = Functions.get(clone.getFunction_id_two());
		
		stmt.setString(1, f1.getType());
		stmt.setString(2, f1.getName());
		stmt.setInt(3, f1.getStartline());
		stmt.setInt(4, f1.getEndline());
		
		stmt.setString(5, f2.getType());
		stmt.setString(6, f2.getName());
		stmt.setInt(7, f2.getStartline());
		stmt.setInt(8, f2.getEndline());
		
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			retval = true;
		}
		rs.close();
		
		return retval;
	}

	@Override
	public void close() throws SQLException {
		stmt.close();
		conn.close();
		stmt = null;
		conn = null;
	}
	
}
