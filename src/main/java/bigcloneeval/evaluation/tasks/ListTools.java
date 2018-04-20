package bigcloneeval.evaluation.tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import bigcloneeval.evaluation.database.Tool;
import bigcloneeval.evaluation.database.Tools;

public class ListTools {
	
	
	public static List<Tool> getTools() {
		List<Tool> tools = new ArrayList<Tool>();
		try {
			tools = Tools.getTools();
		} catch (SQLException e) {
			System.err.println("Some problem with the database!");
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		return tools;
	}
	
}
