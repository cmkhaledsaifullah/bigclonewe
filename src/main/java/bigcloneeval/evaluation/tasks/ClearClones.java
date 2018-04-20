package bigcloneeval.evaluation.tasks;

import java.sql.SQLException;


import bigcloneeval.evaluation.database.Clones;
import bigcloneeval.evaluation.database.Tool;
import bigcloneeval.evaluation.database.Tools;

public class ClearClones {
	
	public static void deleteClones(long toolid) {
			long id=-1;
			try {
				id = toolid;
				Tool tool = Tools.getTool(id);
				if(tool == null) throw new IllegalArgumentException();
				int num = Clones.clearClones(id);
				System.out.println(num);
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
