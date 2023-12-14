package cu.edu.cujae.pweb.dto;

import java.util.Date;

public class IssueDto {
	private String id;
	private String project;
	private String type;
	private String subject;
	private String description;
	private String priority;
	private String user;
	private Date startDate;
	private Date dueDate;
	private int estimatedTime;

	
	
	public IssueDto(String id, String project, String type, String subject, String description, String priority,
			String user, int estimatedTime) {
		super();
		this.id = id;
		this.project = project;
		this.type = type;
		this.subject = subject;
		this.description = description;
		this.priority = priority;
		this.user = user;
		this.estimatedTime = estimatedTime;
	}

	public IssueDto() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	
}
