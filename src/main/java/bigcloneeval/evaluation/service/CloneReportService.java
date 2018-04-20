package bigcloneeval.evaluation.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import bigcloneeval.evaluation.database.ToolReport;
import bigcloneeval.evaluation.model.ToolRangeReportEntry;
import bigcloneeval.evaluation.model.ToolReportEntry;

@Service
public class CloneReportService 
{
	static long id;
	static int functionId;
	
	public static void cloneReportConversion(String filename, long toolid) throws Exception
	{
		functionId = 0;
		//ToolReport.init();
		//System.out.println("Table Initialization Complete!!!");
		Path filepath = Paths.get(filename).toAbsolutePath();
		BufferedReader br = new BufferedReader(new FileReader(new File(filepath.toString())));
		int flag=0;
		String s;
		while((s=br.readLine())!=null)
		{
			if(flag == 0)
			{
				id = toolid;
				if(id != 0)
					flag = 1;
			}
			
			int temp = getFunctionId(s);
			if(temp != 0)
				functionId = temp;
			
			if(s.contains("-- Recall Per Clone Type (type: numDetected / numClones = recall) --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Inter-Project Recall Per Clone Type (type: numDetected / numClones = recall)  --"))
						break;
					
					createEntry(s,"Type-1:","All");
					createEntry(s,"Type-2:","All");
					createEntry(s,"Type-2 (blind):","All");
					createEntry(s,"Type-2 (consistent):","All");
					createEntry(s,"Very-Strongly Type-3:","All");
					createEntry(s,"Strongly Type-3:","All");
					createEntry(s,"Moderatly Type-3:","All");
					createEntry(s,"Weakly Type-3/Type-4:","All");
						
				}
			}
			if(s.contains("-- Inter-Project Recall Per Clone Type (type: numDetected / numClones = recall)  --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Intra-Project Recall Per Clone Type (type: numDetected / numClones = recall) --"))
						break;
					
					createEntry(s,"Type-1:","Inter");
					createEntry(s,"Type-2:","Inter");
					createEntry(s,"Type-2 (blind):","Inter");
					createEntry(s,"Type-2 (consistent):","Inter");
					createEntry(s,"Very-Strongly Type-3:","Inter");
					createEntry(s,"Strongly Type-3:","Inter");
					createEntry(s,"Moderatly Type-3:","Inter");
					createEntry(s,"Weakly Type-3/Type-4:","Inter");
						
				}
			}
			
			if(s.contains("-- Intra-Project Recall Per Clone Type (type: numDetected / numClones = recall) --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Type-3 Recall per 5% Region ([start,end]: numDetected / numClones = recall)  --"))
						break;
					
					createEntry(s,"Type-1:","Intra");
					createEntry(s,"Type-2:","Intra");
					createEntry(s,"Type-2 (blind):","Intra");
					createEntry(s,"Type-2 (consistent):","Intra");
					createEntry(s,"Very-Strongly Type-3:","Intra");
					createEntry(s,"Strongly Type-3:","Intra");
					createEntry(s,"Moderatly Type-3:","Intra");
					createEntry(s,"Weakly Type-3/Type-4:","Intra");
						
				}
			}
			if(s.contains("-- Type-3 Recall per 5% Region ([start,end]: numDetected / numClones = recall)  --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Type-3 Inter-Project Recall per 5% Region--"))
						break;
					if(s.equals(""))
						continue;
					createRangeEntry(s,"All",1);
						
				}
			}
			if(s.contains("-- Type-3 Inter-Project Recall per 5% Region--"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Type-3 Intra-Project Recall per 5% Region--"))
						break;
					if(s.equals(""))
						continue;
					createRangeEntry(s,"Inter",1);
						
				}
			}
			
			if(s.contains("-- Type-3 Intra-Project Recall per 5% Region--"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Type-3 Recall Per Minimum Similarity --"))
						break;
					if(s.equals(""))
						continue;
					createRangeEntry(s,"Intra",1);
						
				}
			}
			if(s.contains("-- Type-3 Recall Per Minimum Similarity --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Type-3 Inter-Project Recall Per Minimum Similarity --"))
						break;
					if(s.equals(""))
						continue;
					createRangeEntry(s,"All",0);
						
				}
			}
			
			if(s.contains("-- Type-3 Inter-Project Recall Per Minimum Similarity --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("-- Type-3 Intra-Project Recall Per Minimum Similarity --"))
						break;
					if(s.equals(""))
						continue;
					createRangeEntry(s,"Inter",0);
						
				}
			}
			if(s.contains("-- Type-3 Intra-Project Recall Per Minimum Similarity --"))
			{
				while((s=br.readLine()) != null)
				{
					if(s.contains("================================================================================"))
						break;
					if(s.equals(""))
						continue;
					createRangeEntry(s,"Intra",0);
						
				}
			}
				
		}
	}
	
	public static void getCloneReport(Long id) throws SQLException
	{
		List<ToolReportEntry> results = ToolReport.getToolReport(id);
		System.out.println(id+" "+results.size());
		List<ToolRangeReportEntry> regionResults = ToolReport.getToolRegionReport(id);
		System.out.println(regionResults.size());
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	private static int getFunctionId(String s)
	{
		int id=0;
		if(s.contains("id: "))
		{
			String[] token = s.split(" ");
			for(int i=0;i<token.length;i++)
			{
				if(isInteger(token[i]))
					id = Integer.parseInt(token[i]);
					
			}				
		}
		return id;
		
	}
	private static void createEntry(String s, String key, String projecttype) 
	{
		if(s.contains(key))
		{
			if(key.equals("Strongly Type-3:") && s.contains("Very-Strongly Type-3"))
			{
				//false
			}
			else
			{
				String[] token = s.split(" ");
				String cloneType = key.substring(0, key.length()-1);
				double recallValue = Double.parseDouble(token[token.length-1]);
				try {
					ToolReport.addToolReport(id, functionId, cloneType, projecttype, recallValue);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	private static void createRangeEntry(String s, String projecttype, int regionStatus) 
	{
		try
		{
			String[] token = s.split(" ");
			String[] startend = token[0].split(",");
			int start = Integer.parseInt(startend[0].substring(1, startend[0].length()));
			int end = Integer.parseInt(startend[1].substring(0, startend[1].length()-2));
			double recallValue = Double.parseDouble(token[token.length - 1]);
			ToolReport.addToolRegionReport(id, functionId, projecttype, regionStatus, start, end, recallValue);
			//System.out.println(id+" "+functionId+" "+projecttype+" "+regionStatus+" "+start+" "+end+" "+recallValue);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
