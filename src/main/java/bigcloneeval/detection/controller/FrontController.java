package bigcloneeval.detection.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bigcloneeval.detection.model.Tool;
import bigcloneeval.detection.service.CommandExecution;
import bigcloneeval.detection.service.StorageService;

@Controller
public class FrontController 
{
	@Autowired
	StorageService storageService;
	Tool tool = new Tool();
	@Autowired
	CommandExecution ce = new CommandExecution();
	
	
	 @GetMapping("/home")
	 public String index()
	 {
		 return "index";
	 }

	 @PostMapping("/summary")
	 public String initialization(@RequestParam("toolname") String toolname, @RequestParam("description") String description, @RequestParam("toolFile") List<MultipartFile> toolFile,@RequestParam("toolRunnerFile") MultipartFile toolRunnerFile,ModelMap model)
	 {
		List<String> files = new ArrayList<String>();
		for (MultipartFile file : toolFile) 
		{
			try 
			{

				storageService.store(file);
				System.out.println("You successfully uploaded " + Paths.get("upload-dir").toAbsolutePath()+"/"+file.getOriginalFilename() + "!");
				files.add(Paths.get("upload-dir").toAbsolutePath()+"/"+file.getOriginalFilename());

			} 
			catch (Exception e) 
			{
				System.out.println("FAIL to upload " + Paths.get("upload-dir").toAbsolutePath()+"/"+file.getOriginalFilename() + "!");
			}
		}
		try 
		{

			storageService.store(toolRunnerFile);
			System.out.println("You successfully uploaded " + Paths.get("upload-dir").toAbsolutePath()+"/"+toolRunnerFile.getOriginalFilename() + "!");
			tool.setToolRunnerFile(Paths.get("upload-dir").toAbsolutePath()+"/"+toolRunnerFile.getOriginalFilename());

		} 
		catch (Exception e) 
		{
			System.out.println("FAIL to upload " + Paths.get("upload-dir").toAbsolutePath()+"/"+toolRunnerFile.getOriginalFilename() + "!");
		}
		tool.setName(toolname);
		tool.setDescription(description);
		tool.setToolFile(files);
		model.addAttribute("tool",tool);
		
		
		try {
			int i = ce.executeInit();
			
			if(i == 0)
			{
				System.out.println("Initialization Completed!!!!");
			}
			else
			{
				System.out.println("Initialization failed with Exception: "+i);
			}
			
			long id = ce.executeRegisterTool(toolname, description);
			
			if(id > 0)
			{
				System.out.println("Registration Completed!!!!");
				tool.setId(id);
			}
			else
			{
				System.out.println("Registration failed with Exception: "+i);
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		
		return "hello";
	 }
	 
	 @PostMapping("/detect")
	 public String detect(@RequestParam("maxfile") String output, @RequestParam("maxfile") String maxfile, @RequestParam("scratchdir") String scratchdir,ModelMap model)
	 {
			
		return "detect";
		 
	 }
	 

}
