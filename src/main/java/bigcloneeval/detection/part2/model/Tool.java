package bigcloneeval.detection.part2.model;


import java.util.List;


public class Tool 
{
	long id;
	String name;
	String description;
	List<String> toolFile;
	String toolRunnerFile;
	
	public Tool() {
		super();
	}
	public Tool(long id) {
		super();
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getToolFile() {
		return toolFile;
	}
	public void setToolFile(List<String> toolFIle) {
		this.toolFile = toolFIle;
	}
	public String getToolRunnerFile() {
		return toolRunnerFile;
	}
	public void setToolRunnerFile(String toolRunnerFile) {
		this.toolRunnerFile = toolRunnerFile;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	

}
