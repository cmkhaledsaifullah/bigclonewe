
package bigcloneeval.evaluation.model;

public class ToolReportEntry 
{
	private long toolId;
	private int functionId;
	private String cloneType;
	private String projectType;
	private double recallValue;
	
	
	
	
	public long getToolId() {
		return toolId;
	}
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	public int getFunctionId() {
		return functionId;
	}
	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}
	public String getCloneType() {
		return cloneType;
	}
	public void setCloneType(String cloneType) {
		this.cloneType = cloneType;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public double getRecallValue() {
		return recallValue;
	}
	public void setRecallValue(double recallValue) {
		this.recallValue = recallValue;
	}
	
	public String show()
	{
		return toolId+" " +functionId+" "+cloneType+" "+projectType+" "+recallValue;
		
	}
	

}
