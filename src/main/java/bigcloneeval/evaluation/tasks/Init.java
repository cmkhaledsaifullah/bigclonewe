package bigcloneeval.evaluation.tasks;

import java.sql.SQLException;

import bigcloneeval.evaluation.database.Tools;

public class Init {
	public static void main(String args[]) throws SQLException {
		Tools.init();
	}
}
