package bigcloneeval.evaluation.tasks;

import java.sql.SQLException;

import bigcloneeval.evaluation.database.Tool;
import bigcloneeval.evaluation.database.Tools;

public class DeleteTool {

	
	public static void deleteTool(Long args) 
	{

			long id=-1;
			try {
				id = args;
				Tool tool = Tools.getTool(id);
				if(tool == null) throw new IllegalArgumentException();
				Tools.deleteToolAndData(id);
			} catch (SQLException e) {
				System.err.println("\tSome error occured with the database connection or interaction.");
				System.err.println("\n\tPlease try a fresh copy of the datbase, and report the error to.");
				System.err.println("\n\tthe developers.");
				e.printStackTrace(System.err);
				System.exit(-1);
			} catch (NumberFormatException e) {
				System.err.println("\tInvalid tool identifier value.");
				System.exit(-1);
			} catch (IllegalArgumentException e) {
				System.err.println("\tNo tool exists with the ID" + id + " .");
				System.exit(-1);
			}
	}
	
	
}
