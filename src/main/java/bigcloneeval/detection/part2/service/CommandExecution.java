package bigcloneeval.detection.part2.service;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import bigcloneeval.detection.part2.tasks.DetectClones;
import bigcloneeval.detection.part2.tasks.ListTools;

@Service
public class CommandExecution 
{
	public List<String> listofTools()
	{
		
		return ListTools.noninteractive();
	}
	
	public int executeDetectTool(String toolFileRunnerPath,String outputFilePath, String scartcDirPath, String maxfiles) throws IOException, InterruptedException
	{
		try
		{
			String[] args = new String[4];
			args[0] = toolFileRunnerPath;
			args[1] = outputFilePath;
			args[2] = scartcDirPath;
			args[3] = maxfiles;
			DetectClones.main(args);
			return 1;
		}
		catch(Exception e)
		{
			return 0;
		}
	}

}
