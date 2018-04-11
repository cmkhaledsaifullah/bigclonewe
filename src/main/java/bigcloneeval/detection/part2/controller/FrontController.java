package bigcloneeval.detection.part2.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import bigcloneeval.detection.part2.model.Tool;
import bigcloneeval.detection.part2.service.StorageService;
import bigcloneeval.detection.part2.service.CommandExecution;

@Controller
public class FrontController 
{
	@Autowired
	StorageService storageService;
	Tool tool = new Tool();
	@Autowired
	CommandExecution ce = new CommandExecution();
	
	
	String outputfileName=null;
	
	
	 @GetMapping("/detect")
	 public String index(ModelMap model)
	 {
		 List<String> toolInfo = ce.listofTools();
		 tool.setId(Long.parseLong(toolInfo.get(0)));
		 tool.setName(toolInfo.get(1));
		 tool.setDescription(toolInfo.get(2));
		 System.out.println(toolInfo);
		 model.addAttribute("tool",tool);
		 return "index";
	 }

	 @PostMapping("/result")
	 public String generatingResult(@RequestParam("toolRunner") String toolRunner, @RequestParam("output") String output, @RequestParam("scratchdir") String scratchdir,@RequestParam("maxfile") String maxfile,ModelMap model)
	 {
		 this.outputfileName = output;
		
		try {
			int status= ce.executeDetectTool(toolRunner, output, scratchdir, maxfile);
			if(status == 1)
				System.out.println("Detection complete");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "result";
	 }	
	 
	 
	 @GetMapping("/download")
	 @ResponseBody
	 public ResponseEntity<Resource> getFile() 
	 {
		 Resource file = storageService.loadFile(outputfileName);
		 return ResponseEntity.ok()
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				 .body(file);
		
	 }

}
