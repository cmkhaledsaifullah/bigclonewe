package bigcloneeval.evaluation.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bigcloneeval.evaluation.model.ToolRangeReportEntry;
import bigcloneeval.evaluation.model.ToolReportEntry;

public class ToolReport 
{
	private static void checktable() throws SQLException {
		Connection conn = ToolsDB.getConnection();
		
		//Check toolreport table
		Statement stmt = null;
		DatabaseMetaData dbm = conn.getMetaData();
		// check if "toolreport" table is there
		ResultSet tables = dbm.getTables(null, null, "TOOLREPORT", null);
		if (tables.next()) {
			// Table exists
			System.out.println("Table TOOLREPORT is present!!!");
			String sql = "DROP TABLE toolreport";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}

		
		//Check toolregionreport table
		Statement stmt1 = null;
		DatabaseMetaData dbm1 = conn.getMetaData();
		// check if "toolregionreport" table is there
		ResultSet tables1 = dbm1.getTables(null, null, "TOOLREGIONREPORT", null);
		if (tables1.next()) {
			// Table exists
			System.out.println("Table TOOLREGIONREPORT is present!!!");
			String sql = "DROP TABLE toolregionreport";
			stmt1 = conn.createStatement();
			stmt1.executeUpdate(sql);
		}
		
		conn.close();
	}
	
	public static void init() {
		try {
			checktable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "CREATE TABLE toolreport (id BIGINT  NOT NULL,"
											+ " functiontype INTEGER NOT NULL,"
											+ " clonetype VARCHAR(255) NOT NULL ,"
											+ " projecttype VARCHAR(255) NOT NULL,"
											+ " recall DOUBLE NOT NULL)";
		Connection conn;
		try {
			conn = ToolsDB.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			regionInit();
		} catch (SQLException e) {
			System.out.println("Error while Creating TOOLREPORT database!!!");
			e.printStackTrace();
		}
		
	}
	
	public static void regionInit() throws SQLException {
		String sql = "CREATE TABLE toolregionreport (id BIGINT  NOT NULL,"
											+ " functiontype INTEGER NOT NULL,"
											+ " projecttype VARCHAR(255) NOT NULL,"
											+ " regionstatus INTEGER NOT NULL,"
											+ " startindex INTEGER NOT NULL,"
											+ " endindex INTEGER NOT NULL,"
											+ " recall DOUBLE NOT NULL)";
		Connection conn = ToolsDB.getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
		conn.close();
	}
	
	public static long addToolReport(long id,int functionId,String cloneType, String projectType,double recallValue) throws SQLException {
		// Add Tool report entry
			String sql = "INSERT INTO toolreport (id, functiontype, clonetype, projecttype, recall) VALUES (?,?,?,?,?)";
			Connection conn = ToolsDB.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			stmt.setInt(2, functionId);
			stmt.setString(3, cloneType);
			stmt.setString(4, projectType);
			stmt.setDouble(5, recallValue);
			stmt.executeUpdate();
			stmt.close();			
			conn.close();
			return id;
		}



	public static long addToolRegionReport(long id,int functionId,String projectType,int regionStatus, int start, int end, double recallValue) throws SQLException {
		// Add Tool report entry
			String sql = "INSERT INTO toolregionreport (id, functiontype, projecttype, regionstatus, startindex, endindex, recall) VALUES (?,?,?,?,?,?,?)";
			Connection conn = ToolsDB.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			stmt.setInt(2, functionId);
			stmt.setString(3, projectType);
			stmt.setInt(4, regionStatus);
			stmt.setInt(5, start);
			stmt.setInt(6, end);
			stmt.setDouble(7, recallValue);
			stmt.executeUpdate();
			stmt.close();			
			conn.close();
			return id;
		}
	
	
	public static List<ToolReportEntry> getToolReport(long id) throws SQLException {
		ToolReportEntry retval = null;
		List<ToolReportEntry> results = new ArrayList<ToolReportEntry>();
		String sql = "SELECT * FROM toolreport WHERE id = ?";
		Connection conn = ToolsDB.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			retval = new ToolReportEntry();
			retval.setToolId(rs.getInt("id"));
			retval.setFunctionId(rs.getInt("functiontype"));
			retval.setCloneType(rs.getString("clonetype"));
			retval.setProjectType(rs.getString("projecttype"));
			retval.setRecallValue(rs.getDouble("recall"));
			results.add(retval);
			
			//System.out.println(retval.show());
		}
		rs.close();
		stmt.close();
		conn.close();
		return results;
	}
	
	public static List<ToolReportEntry> getToolReport(long id, String projectType) throws SQLException {
		ToolReportEntry retval = null;
		List<ToolReportEntry> results = new ArrayList<ToolReportEntry>();
		String sql = "SELECT * FROM toolreport WHERE id = ? AND functiontype = ? AND projecttype = ?";
		Connection conn = ToolsDB.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);
		stmt.setInt(2, 0);
		stmt.setString(3, projectType);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			retval = new ToolReportEntry();
			retval.setToolId(rs.getInt("id"));
			retval.setFunctionId(rs.getInt("functiontype"));
			retval.setCloneType(rs.getString("clonetype"));
			retval.setProjectType(rs.getString("projecttype"));
			retval.setRecallValue(rs.getDouble("recall"));
			results.add(retval);
			
			//System.out.println(retval.show());
		}
		rs.close();
		stmt.close();
		conn.close();
		return results;
	}
	
	public static List<Long> getToolids() throws SQLException {
		List<Long> ids = new ArrayList<Long>();
		String sql = "SELECT DISTINCT id FROM toolreport";
		Connection conn = ToolsDB.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			ids.add(rs.getLong("id"));
		}
		rs.close();
		stmt.close();
		conn.close();
		return ids;
	}
	
	
	public static List<ToolRangeReportEntry> getToolRegionReport(Long id) {
		ToolRangeReportEntry retval = new ToolRangeReportEntry();
		List<ToolRangeReportEntry> results = new ArrayList<ToolRangeReportEntry>();
		ResultSet rs = null;
		try {
		String sql = "SELECT * FROM toolregionreport WHERE id = ?";
		Connection conn = ToolsDB.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);
		rs = stmt.executeQuery();
		while(rs.next())
		{
			retval.setToolId(rs.getLong("id"));
			retval.setFunctionId(rs.getInt("functiontype"));
			retval.setProjectType(rs.getString("projecttype"));
			retval.setRegionStatus(rs.getInt("regionstatus"));
			retval.setStart(rs.getInt("startindex"));
			retval.setEnd(rs.getInt("endindex"));
			retval.setRecallValue(rs.getDouble("recall"));
			results.add(retval);
		}
		rs.close();
		stmt.close();
		conn.close();
		
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return results;
	}
	
	
	
}
