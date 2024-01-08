package cu.edu.cujae.pweb.dto;

public class UserReportDto {
	
	
	private String username;
    private String lastname;
    private String mail;
    private int totalProject;
    private int hoursReported;
    private int delayedIssues;
	public UserReportDto(String username, String lastname, String mail, int totalProject, int hoursReported,
                         int delayedIssues) {
		super();
		this.username = username;
		this.lastname = lastname;
		this.mail = mail;
		this.totalProject = totalProject;
		this.hoursReported = hoursReported;
		this.delayedIssues = delayedIssues;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getTotalProject() {
		return totalProject;
	}
	public void setTotalProject(int totalProject) {
		this.totalProject = totalProject;
	}
	public int getHoursReported() {
		return hoursReported;
	}
	public void setHoursReported(int hoursReported) {
		this.hoursReported = hoursReported;
	}
	public int getDelayedIssues() {
		return delayedIssues;
	}
	public void setDelayedIssues(int delayedIssues) {
		this.delayedIssues = delayedIssues;
	}

	
}
