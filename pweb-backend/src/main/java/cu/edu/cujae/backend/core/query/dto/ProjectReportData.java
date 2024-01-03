package cu.edu.cujae.backend.core.query.dto;

import java.util.Date;

public class ProjectReportData {
    private String name;
    private int members;
    private String username;
    private Date createdOn;
    private Date closedOn;
    private double totalHoursReported;
    private double averageDoneRatio;
    private int totalIssues;
    private int delayedIssues;
	public ProjectReportData(String name, int members, String username, Date createdOn, Date closedOn,
			double totalHoursReported, double averageDoneRatio, int totalIssues, int delayedIssues) {
		super();
		this.name = name;
		this.members = members;
		this.username = username;
		this.createdOn = createdOn;
		this.closedOn = closedOn;
		this.totalHoursReported = totalHoursReported;
		this.averageDoneRatio = averageDoneRatio;
		this.totalIssues = totalIssues;
		this.delayedIssues = delayedIssues;
	}
	
	public ProjectReportData() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMembers() {
		return members;
	}
	public void setMembers(int members) {
		this.members = members;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getClosedOn() {
		return closedOn;
	}
	public void setClosedOn(Date closedOn) {
		this.closedOn = closedOn;
	}
	public double getTotalHoursReported() {
		return totalHoursReported;
	}
	public void setTotalHoursReported(double totalHoursReported) {
		this.totalHoursReported = totalHoursReported;
	}
	public double getAverageDoneRatio() {
		return averageDoneRatio;
	}
	public void setAverageDoneRatio(double averageDoneRatio) {
		this.averageDoneRatio = averageDoneRatio;
	}
	public int getTotalIssues() {
		return totalIssues;
	}
	public void setTotalIssues(int totalIssues) {
		this.totalIssues = totalIssues;
	}
	public int getDelayedIssues() {
		return delayedIssues;
	}
	public void setDelayedIssues(int delayedIssues) {
		this.delayedIssues = delayedIssues;
	}
	
    
}
