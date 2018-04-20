package bigcloneeval.evaluation.service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.io.Files;

import bigcloneeval.evaluation.model.ToolRankingEntry;
import bigcloneeval.evaluation.tasks.DeleteTool;
import bigcloneeval.evaluation.tasks.EvaluateTool;
import bigcloneeval.evaluation.tasks.ImportClones;
import bigcloneeval.evaluation.tasks.Init;
import bigcloneeval.evaluation.tasks.RegisterTool;

@Service
public class CommandExecution 
{
	
	public int executeInit() throws IOException, InterruptedException
	{
		try {
			Init.main(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;	
	}
	
	public long executeRegisterTool(String toolName,String toolDescription) throws IOException, InterruptedException
	{
		String[] args = new String[2];
		args[0] = toolName;
		args[1] = toolDescription;
		long id = RegisterTool.register(args);
		return id;
	}
	
	public long executeImport(String toolId, String filename)
	{
		String[] args = new String[2];
		args[0] = toolId;
		args[1] = filename;
		return ImportClones.importClone(args);
	}
	
	public String executeEvaluate(String[] args)
	{
		String time = EvaluateTool.evaluateProcess(args);
		copyReport(args[0],args[12]);
		return time;
		//testCopy(args[0],args[12]);
		//return "Hello World";
	}
	
	
	public void copyReport(String toolid, String outfilename)
	{ 
		try {
			Files.copy(new File(outfilename), new File(toolid+".report"));
		} catch (IOException e) {
			System.out.println("Error while copying the report file in Command Execution Controller");
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private void testCopy(String toolid, String outfilename)
	{
		try {
			BufferedWriter bw =new BufferedWriter(new FileWriter(new File(outfilename)));
			bw.write("Hello World");
			bw.close();
			Files.copy(new File(outfilename), new File(toolid+".report"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteToolReport(Long toolid, String filename)
	{
		DeleteTool.deleteTool(toolid);
		System.out.println("The Tool with Id: "+ toolid + " is Succesfully deleted");
		Path path = Paths.get(toolid+".report").toAbsolutePath();
		File file = path.toFile();
		if(file.delete())
		{
			System.out.println(path+" is successfully Deleted");
		}
		else
		{
			System.out.println(path+" is failed to Deleted");
		}
		path = Paths.get(filename).toAbsolutePath();
		File mainfile = path.toFile();
		
		if(mainfile.delete())
		{
			System.out.println(path+" is successfully Deleted");
		}
		else
		{
			System.out.println(path+" is failed to Deleted");
		}
	}
	
	public File createRankingFile(String projectType)
	{
		List<ToolRankingEntry> rankingdata = RankingService.getreports(projectType);
		File file = new File("bigCloneEvalWebRanking"+projectType+".csv");
		BufferedWriter bw = null;
		try {
			bw =new BufferedWriter(new FileWriter(file));
			bw.write("Tool Id,Tool Name,Type-1,Type-2,Type-2 (consistent),Type-2 (bind),Very-Strongly Type-3,Strongly Type-3,Moderatly Type-3,Weakly Type-3/Type-4");
			for(ToolRankingEntry entry:rankingdata)
			{
				bw.newLine();
				String s= entry.getToolId()+","+entry.getToolName()+","+entry.getType1()+","+entry.getType2()+","+entry.getType2Consistent()+","+entry.getType2Blind()+","+entry.getVeryStronglyT3()+","+entry.getStronglyT3()+","+entry.getModeratelyT3()+","+entry.getWeaklyT3();
				bw.write(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

}
