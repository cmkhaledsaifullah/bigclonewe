package bigcloneeval.evaluation.model;

public class ToolRankingEntry 
{
	private long toolId;
	private String toolName;
	private String projectType;
	private double type1;
	private double type2;
	private double type2Blind;
	private double type2Consistent;
	private double veryStronglyT3;
	private double stronglyT3;
	private double moderatelyT3;
	private double weaklyT3;
	
	
	public long getToolId() {
		return toolId;
	}
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public double getType1() {
		return type1;
	}
	public void setType1(double type1) {
		this.type1 = type1;
	}
	public double getType2() {
		return type2;
	}
	public void setType2(double type2) {
		this.type2 = type2;
	}
	public double getType2Blind() {
		return type2Blind;
	}
	public void setType2Blind(double type2Blind) {
		this.type2Blind = type2Blind;
	}
	public double getType2Consistent() {
		return type2Consistent;
	}
	public void setType2Consistent(double type2Consistent) {
		this.type2Consistent = type2Consistent;
	}
	public double getVeryStronglyT3() {
		return veryStronglyT3;
	}
	public void setVeryStronglyT3(double veryStronglyT3) {
		this.veryStronglyT3 = veryStronglyT3;
	}
	public double getStronglyT3() {
		return stronglyT3;
	}
	public void setStronglyT3(double stronglyT3) {
		this.stronglyT3 = stronglyT3;
	}
	public double getModeratelyT3() {
		return moderatelyT3;
	}
	public void setModeratelyT3(double moderatelyT3) {
		this.moderatelyT3 = moderatelyT3;
	}
	public double getWeaklyT3() {
		return weaklyT3;
	}
	public void setWeaklyT3(double weaklyT3) {
		this.weaklyT3 = weaklyT3;
	}
	
	public String show()
	{
		return toolId+" "+toolName+" "+projectType+" "+type1+" "+type2+" "+type2Blind+" "+type2Consistent+" "+veryStronglyT3+" "+stronglyT3+" "+moderatelyT3+" "+weaklyT3;
	}
	

}
