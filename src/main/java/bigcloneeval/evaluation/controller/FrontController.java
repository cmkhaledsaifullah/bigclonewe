package bigcloneeval.evaluation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import bigcloneeval.evaluation.database.Tool;
import bigcloneeval.evaluation.database.Tools;
import bigcloneeval.evaluation.model.Ranking;
import bigcloneeval.evaluation.model.ToolRankingEntry;
import bigcloneeval.evaluation.service.CloneReportService;
import bigcloneeval.evaluation.service.CommandExecution;
import bigcloneeval.evaluation.service.RankingService;
import bigcloneeval.evaluation.service.StorageService;
import bigcloneeval.evaluation.tasks.ClearClones;
import bigcloneeval.evaluation.tasks.ListTools;

@Controller
public class FrontController 
{
	@Autowired
	StorageService storageService;
	Tool tool = new Tool();
	@Autowired
	CommandExecution ce = new CommandExecution();
	
	@Autowired
	CloneReportService clonereportservice = new CloneReportService();

	
	String outputfileName = null;
	 
	 
	 
	 
	@GetMapping("/cloneranking/home")
	 public String index(ModelMap map)
	 {
		/*
		try {
			ce.executeInit();
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
		}
		ToolReport.init();
		*/
		List<Tool> tools = ListTools.getTools();
		
		map.addAttribute("tools",tools);
		
		List<ToolRankingEntry> rankingAll = RankingService.getreports("All");
		List<ToolRankingEntry> rankingInter = RankingService.getreports("Inter");
		List<ToolRankingEntry> rankingIntra = RankingService.getreports("Intra");
		
		Ranking bestAll = RankingService.bestValue(rankingAll);
		Ranking bestInter = RankingService.bestValue(rankingInter);
		Ranking bestIntra = RankingService.bestValue(rankingIntra);
			
		map.addAttribute("rankingall", rankingAll);
		map.addAttribute("rankinginter", rankingInter);
		map.addAttribute("rankingintra", rankingIntra);
		map.addAttribute("bestall", bestAll);
		map.addAttribute("bestinter", bestInter);
		map.addAttribute("bestintra", bestIntra);
		
		return "index";
	 }
	
	 @PostMapping("/downloadall")
	 @ResponseBody
	 public ResponseEntity<Resource> downloadAllReport() 
	 {
		File report = ce.createRankingFile("All");
		Resource file = storageService.loadFile(report.getName());
		 return ResponseEntity.ok()
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				 .body(file);
		
	 }
	 @PostMapping("/downloadinter")
	 @ResponseBody
	 public ResponseEntity<Resource> downloadInterReport() 
	 {
		File report = ce.createRankingFile("Inter");
		Resource file = storageService.loadFile(report.getName());
		 return ResponseEntity.ok()
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				 .body(file);
		
	 }
	 @PostMapping("/downloadintra")
	 @ResponseBody
	 public ResponseEntity<Resource> downloadIntraReport() 
	 {
		File report = ce.createRankingFile("Intra");
		Resource file = storageService.loadFile(report.getName());
		 return ResponseEntity.ok()
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				 .body(file);
		
	 }
	
	
	@GetMapping("/evaluation")
	 public String evaluationForm()
	 {
		 return "evaluate";
	 }
	
	
	@PostMapping("/evaluationresult")
	 public String evaluation(@RequestParam("toolname") String toolname, @RequestParam("description") String tooldescription, @RequestParam("toolFile") MultipartFile toolfile, @RequestParam("output") String output,@RequestParam("simtype") String simtype,@RequestParam("clonematcher") String clonematcher,@RequestParam("minsim") String minsim,@RequestParam("minline") String minline,@RequestParam("maxline") String maxline,@RequestParam("minpretty") String minpretty,@RequestParam("maxpretty") String maxpretty,@RequestParam("mintoken") String mintoken,@RequestParam("maxtoken") String maxtoken,@RequestParam("minjudgement") String minjudgement,@RequestParam("mincon") String mincon,ModelMap model)
	 {
		List<Tool> tools = ListTools.getTools();
		
		model.addAttribute("tools",tools);
		
		if(toolname.isEmpty())
			System.out.println("Enter Tool Name");
		else if(tooldescription.isEmpty())
			System.out.println("Enter Tool Description");
		else if(toolfile.isEmpty())
			System.out.println("Enter Clone result file");
		else if(output.isEmpty())
			System.out.println("Enter Output File Name");
		else
		{
			this.outputfileName = output;
			try 
			{
				storageService.store(toolfile);
				System.out.println("You successfully uploaded " + Paths.get("upload-dir").toAbsolutePath()+"/"+toolfile.getOriginalFilename() + "!");

			} 
			catch (Exception e) 
			{
				System.out.println("FAIL to upload " + Paths.get("upload-dir").toAbsolutePath()+"/"+toolfile.getOriginalFilename() + "!");
			}
			tool.setName(toolname);
			tool.setDescription(tooldescription);

			try 
			{
				long id = ce.executeRegisterTool(toolname, tooldescription);
				
				if(id > 0)
				{
					System.out.println("Registration Completed!!!!");
					tool.setId(id);
				}
				else
				{
					System.out.println("Registration failed with Exception: 0");
				}
				String toolId = tool.getId()+"";
				long numClones = ce.executeImport(toolId, toolfile.getOriginalFilename());
				if(numClones > 0)
				{
					System.out.println("Clones are succesfully imported. Number of CLones are:" + numClones);
				}
				else
				{
					System.out.println("Import Clone failed with Exception: "+numClones);
				}
				String time = evaluatFunction(toolId,simtype,clonematcher,minline,maxline,mintoken,maxtoken,minpretty,maxpretty,minjudgement,mincon,minsim,output);
				model.addAttribute("statement", time);
				model.addAttribute("candidatetool", tool);
			} 
			catch (IOException | InterruptedException e) 
			{
				System.out.println(e);
			}
		}
		
		
		return "evaluationresult";
	 }
	
	private String evaluatFunction(String id, String simtype, String matcher, String minline,String maxline,String mintoken, String maxtoken, String minpretty, String maxpretty, String minjudges, String mincon, String minsim, String output)
	{
		String[] args = new String[13];
		args[0] = id;
		args[1] = simtype;
		args[2] = matcher;
		args[3] = minline;
		args[4] = maxline;
		args[5] = mintoken;
		args[6] = maxtoken;
		args[7] = minpretty;
		args[8] = maxpretty;
		args[9] = minjudges;
		args[10] = mincon;
		args[11] = minsim;
		args[12] = output;
		
		String time =ce.executeEvaluate(args);
		return time;
	}
	

	 @GetMapping("/download/{toolId}")
	 public void getFile(@RequestParam(value = "isstore", required = false)String isstore, @PathVariable("toolId") Long toolId,HttpServletResponse response) 
	 {
		
		Resource file = storageService.loadFile(outputfileName);
		response.setHeader("Content-disposition", "attachment; filename=" + file.getFilename());
		FileInputStream in = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			in = new FileInputStream(file.getFilename());
			IOUtils.copy(in,out);
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClearClones.deleteClones(toolId);
		System.out.println("Clones of "+ toolId +" are successfully deleted!!!");
		if(isstore != null)
		 {
			 try {
					CloneReportService.cloneReportConversion(outputfileName,tool.getId());
					System.out.println("Report is Inserted!!!");
					CloneReportService.getCloneReport(tool.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }
		 else
		 {
			 ce.deleteToolReport(toolId, outputfileName);
		 }
		
	 }
	 
	 
 
	 
	 @GetMapping("/tool/{toolId}")
	 public String toolPage(@PathVariable("toolId") Long toolId, ModelMap model)
	 {
		List<Tool> tools = ListTools.getTools();
		model.addAttribute("tools",tools);
		
		 try {
			Tool tool = Tools.getTool(toolId);
			model.addAttribute("candidatetool",tool);
		} catch (SQLException e) {
			System.out.println("Exception while catching path param: "+ toolId);
			e.printStackTrace();
		}
		 return "toolpage";
	 }
	 
	 
	 @PostMapping("/downloadreport/{toolId}")
	 @ResponseBody
	 public ResponseEntity<Resource> getReport(@PathVariable("toolId") Long toolId) 
	 {
		Resource file = storageService.loadFile(toolId+".report");
		 return ResponseEntity.ok()
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				 .body(file);
		
	 }
	@GetMapping("/about")
	public String aboutUs(ModelMap model)
	{
		List<Tool> tools = ListTools.getTools();		
		model.addAttribute("tools",tools);
		return "aboutus";
	}
	
	
	@PostMapping("/reportSubmission")
	 public String reportSubmission(@RequestParam("toolname") String toolname, @RequestParam("description") String tooldescription, @RequestParam("toolFile") MultipartFile toolfile,ModelMap model)
	 {
		List<Tool> tools = ListTools.getTools();		
		model.addAttribute("tools",tools);
		try 
		{
			storageService.store(toolfile);
			System.out.println("You successfully uploaded " + Paths.get("upload-dir").toAbsolutePath()+File.separator+toolfile.getOriginalFilename() + "!");

		} 
		catch (Exception e) 
		{
			System.out.println("FAIL to upload " + Paths.get("upload-dir").toAbsolutePath()+File.separator+toolfile.getOriginalFilename() + "!");
		}
		tool.setName(toolname);
		tool.setDescription(tooldescription);
		
		try 
		{
			long id = ce.executeRegisterTool(toolname, tooldescription);
			
			if(id > 0)
			{
				System.out.println("Registration Completed!!!!");
				tool.setId(id);
			}
			else
			{
				System.out.println("Registration failed with Exception: 0");
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(e);
		}
		try {
			CloneReportService.cloneReportConversion(Paths.get("upload-dir").toAbsolutePath()+File.separator+toolfile.getOriginalFilename(),tool.getId());
			System.out.println("Report is Inserted!!!");
			CloneReportService.getCloneReport(tool.getId());
			ce.copyReport(tool.getId()+"", Paths.get("upload-dir").toAbsolutePath()+File.separator+toolfile.getOriginalFilename());
		} catch (Exception e) {
			System.err.println("Error occurs while storing report in front controll!!!");
			e.printStackTrace();
		}
		
		return "redirect:/about";
	 }
	
	
	 @GetMapping("/loginform")
	 public String login()
	 {
		 return "login";
	 }
	 
	 @PostMapping("/login")
	 public String loginControl(@RequestParam("username") String username, @RequestParam(value = "remember", required = false)String idrates)
	 {
		 if(idrates != null)
			 System.out.println("Result IS: "+idrates);
		 return "redirect:/loginform";
	}
}
