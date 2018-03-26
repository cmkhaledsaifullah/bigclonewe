package bigcloneeval.tasks;

import java.sql.SQLException;

import bigcloneeval.database.Tools;

public class Init {
	public static void main(String args[]) throws SQLException {
		Tools.init();
	}
}
