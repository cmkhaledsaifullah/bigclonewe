package bigcloneeval.detection.service;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import bigcloneeval.tasks.DetectClones;
import bigcloneeval.tasks.Init;
import bigcloneeval.tasks.RegisterTool;

@Service
public class CommandExecution 
{
	
	public int executeInit() throws IOException, InterruptedException
	{
		try {
			Init.main(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	public int executeDetectTool(String toolFileRunnerPath,String outputFilePath, int maxfiles, String scartcDirPath) throws IOException, InterruptedException
	{
		String[] args = new String[4];
		args[0] = toolFileRunnerPath;
		args[1] = outputFilePath;
		args[2] = scartcDirPath;
		args[3] = maxfiles+"";
		DetectClones.main(args);
		return 0;
	}

}
