package bigcloneeval.detection.part2.tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.text.WordUtils;

import bigcloneeval.detection.part2.database.Tool;
import bigcloneeval.detection.part2.database.Tools;

@SuppressWarnings("deprecation")
public class ListTools {
	
	
	public static List<String> noninteractive() {
		List<String> toolInfo = new ArrayList<String>();
		try {
			List<Tool> tools = Tools.getTools();
			for(Tool tool : tools) {
				toolInfo.add(tool.getId()+"");
				toolInfo.add(tool.getName());
				toolInfo.add(tool.getDescription());
			}
		} catch (SQLException e) {
			System.err.println("Some problem with the database!");
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		return toolInfo;
	}
	
	public static void interactive() {
		try {
			System.out.println();
			System.out.println(":::::::::::::::::::::::::: BigCloneEval - List Tools :::::::::::::::::::::::::");
			System.out.println();
			List<Tool> tools = Tools.getTools();
			if(tools.size() != 0) {
				for(Tool tool : tools) {
					String description = WordUtils.wrap(tool.getDescription(), 75, "\n    ", true);
					System.out.println("[" + tool.getId() + "] - " + tool.getName());
					System.out.println("    " + description);
					System.out.println("");
				}
			} else {
				System.out.println("\tNo tools have been added.");
				System.out.println("");
			}
			System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println();
		} catch (SQLException e) {
			System.out.println("\tERROR: Problem with database connection or schema.");
			System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println();
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
