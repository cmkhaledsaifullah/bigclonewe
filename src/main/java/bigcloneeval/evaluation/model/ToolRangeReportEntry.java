
package bigcloneeval.evaluation.model;

public class ToolRangeReportEntry 
{
	private long toolId;
	private int functionId;
	private String projectType;
	private int regionStatus;
	private int start;
	private int end;
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
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public int getRegionStatus() {
		return regionStatus;
	}
	public void setRegionStatus(int regionStatus) {
		this.regionStatus = regionStatus;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public double getRecallValue() {
		return recallValue;
	}
	public void setRecallValue(double recallValue) {
		this.recallValue = recallValue;
	}
	
	

	
	

}
