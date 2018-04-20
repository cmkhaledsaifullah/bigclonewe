package bigcloneeval.evaluation.tasks;

import java.sql.SQLException;
//import java.util.Scanner;

import bigcloneeval.evaluation.database.Tools;

public class RegisterTool {
	
	
	public static long register(String args[]) {
			String desc = args[1];
			String name = args[0];
			
			long id = -1;
			try {
				id = Tools.addTool(name, desc);
				System.out.println(id);
			} catch (SQLException e) {
				System.err.println("\tSome error occured with the database connection or interaction.");
				System.err.println("\tPlease try a fresh copy of the datbase, and report the error to.");
				System.err.println("\tthe developers.");
				e.printStackTrace(System.err);
				System.exit(-1);
			}
			return id;
	}
	
}
