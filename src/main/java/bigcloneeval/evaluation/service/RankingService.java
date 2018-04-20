package bigcloneeval.evaluation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bigcloneeval.evaluation.database.Tool;
import bigcloneeval.evaluation.database.ToolReport;
import bigcloneeval.evaluation.database.Tools;
import bigcloneeval.evaluation.model.Ranking;
import bigcloneeval.evaluation.model.ToolRankingEntry;
import bigcloneeval.evaluation.model.ToolReportEntry;

public class RankingService 
{
	
	public static List<ToolRankingEntry> getreports(String projectType)
	{
		List<ToolReportEntry> returnvalues = new ArrayList<ToolReportEntry>();
		List<Long> toolids = new ArrayList<Long>();
		List<ToolRankingEntry> rankingdata = new ArrayList<ToolRankingEntry>();
		try {
			toolids = ToolReport.getToolids();
		} catch (SQLException e) {
			System.out.println("Error is Getting all tool id in Ranking service");
			e.printStackTrace();
		}
		
		for(long id:toolids)
		{
			List<ToolReportEntry> currenttool = new ArrayList<ToolReportEntry>();
			try {
				currenttool = ToolReport.getToolReport(id,projectType);
				rankingdata.add(getRankingEntry(id,currenttool,projectType));
			} catch (SQLException e) {
				System.out.println("Error is toolreport retrival in Ranking Service");
				e.printStackTrace();
			}
			returnvalues.addAll(currenttool);
		}
		
		return rankingdata;
	}
	
	private static ToolRankingEntry getRankingEntry(long toolId, List<ToolReportEntry> currenttool,String projectType)
	{
		ToolRankingEntry returnEntry= new ToolRankingEntry();
		
		Tool tool = null;
		try {
			tool = Tools.getTool(toolId);
		} catch (SQLException e) {
			System.out.println("Error is Ranking data Creation in Ranking Service");
			e.printStackTrace();
		}
		returnEntry.setToolId(toolId);
		returnEntry.setToolName(tool.getName());
		
		for(ToolReportEntry entry:currenttool)
		{
			returnEntry.setProjectType(projectType);
			if(entry.getCloneType().contains("Type-1"))
				returnEntry.setType1(entry.getRecallValue());
			if(entry.getCloneType().equals("Type-2"))
				returnEntry.setType2(entry.getRecallValue());
			if(entry.getCloneType().equals("Type-2 (blind)"))
				returnEntry.setType2Blind(entry.getRecallValue());
			if(entry.getCloneType().equals("Type-2 (consistent)"))
				returnEntry.setType2Consistent(entry.getRecallValue());
			if(entry.getCloneType().contains("Very-Strongly Type-3"))
				returnEntry.setVeryStronglyT3(entry.getRecallValue());
			if(entry.getCloneType().contains("Strongly Type-3"))
				returnEntry.setStronglyT3(entry.getRecallValue());
			if(entry.getCloneType().contains("Moderatly Type-3"))
				returnEntry.setModeratelyT3(entry.getRecallValue());
			if(entry.getCloneType().contains("Weakly Type-3/Type-4"))
				returnEntry.setWeaklyT3(entry.getRecallValue());
		}
		//System.out.println(returnEntry.show());
		return returnEntry;
	}
	
	
	public static Ranking bestValue(List<ToolRankingEntry> rankingData)
	{
		Ranking rk = new Ranking();
		double type1 = -1;
		double type2 = -1;
		double type2Blind = -1;
		double type2Consistent = -1;
		double veryStronglyT3 = -1;
		double stronglyT3 = -1;
		double moderatelyT3 = -1;
		double weaklyT3 = -1;
		for(ToolRankingEntry entry:rankingData)
		{
			if(entry.getType1() > type1)
			{
				rk.setType1(entry.getToolId());
				type1 = entry.getType1();
			}
			if(entry.getType2() > type2)
			{
				rk.setType2(entry.getToolId());
				type2 = entry.getType2();
			}
			if(entry.getType2Blind() > type2Blind)
			{
				rk.setType2Blind(entry.getToolId());
				type2Blind = entry.getType2Blind();
			}
			if(entry.getType2Consistent() > type2Consistent)
			{
				rk.setType2Consistent(entry.getToolId());
				type2Consistent = entry.getType2Consistent();
			}
			if(entry.getVeryStronglyT3() > veryStronglyT3)
			{
				rk.setVeryStronglyT3(entry.getToolId());
				veryStronglyT3 = entry.getVeryStronglyT3();
			}
			if(entry.getStronglyT3() > stronglyT3)
			{
				rk.setStronglyT3(entry.getToolId());
				stronglyT3 = entry.getStronglyT3();
			}
			if(entry.getModeratelyT3() > moderatelyT3)
			{
				rk.setModeratelyT3(entry.getToolId());
				moderatelyT3 = entry.getModeratelyT3();
			}
			if(entry.getWeaklyT3() > weaklyT3)
			{
				rk.setWeaklyT3(entry.getToolId());
				weaklyT3 = entry.getWeaklyT3();
			}
		}
		
		return rk;
	}

}
