package cu.edu.cujae.backend.core.dto;





import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class IssueDto {

	@ApiModelProperty(hidden = true)
	private int id;
	@ApiModelProperty(hidden = true)
	private Date created_on;
	@ApiModelProperty(hidden = true)
	private Date update_on;
	
	
	@ApiModelProperty(hidden = true)
	private String assigned_to_name;
	@ApiModelProperty(hidden = true)
	private String project_name;
	@ApiModelProperty(hidden = true)
	private String author_name;
	
	
	
	private Date closed_on;
	private String subject; 

	
	private double done_ratio;
	private Date due_date;

	
	
	private int project_id;
	private int author_id ;
	private int assigned_to_id ;

	private double hours_reported;
	
	
	
	
	public IssueDto() {
		super();
	}

	public IssueDto(int id, String subject, String description, boolean is_private, double done_ratio, Date closed_on,
			Date due_date, Date start_date, Date update_on, Date created_on, double estimated_hours, int project_id,
			int author_id, int asigned_to_id , String type ,double hours_reported ,String user_name ,String project_name ,String author_name) {
		super();
		this.id = id;
		this.subject = subject;
		this.done_ratio = done_ratio;
		this.closed_on = closed_on;
		this.due_date = due_date;

		this.update_on = update_on;
		this.created_on = created_on;

		this.project_id = project_id;
		this.author_id = author_id;
		this.assigned_to_id = asigned_to_id;

		this.hours_reported = hours_reported;
		this.assigned_to_name = user_name;
		this.project_name = project_name;
		this.author_name =  author_name;
	}
	
	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}



	public String getAssigned_to_name() {
		return assigned_to_name;
	}

	public void setAssigned_to_name(String assigned_to_name) {
		this.assigned_to_name = assigned_to_name;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getDone_ratio() {
		return done_ratio;
	}

	public void setDone_ratio(double done_ratio) {
		this.done_ratio = done_ratio;
	}

	public Date getClosed_on() {
		return closed_on;
	}

	public void setClosed_on(Date closed_on) {
		this.closed_on = closed_on;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}


	public Date getUpdate_on() {
		return update_on;
	}

	public void setUpdate_on(Date update_on) {
		this.update_on = update_on;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	public int getAssigned_to_id() {
		return assigned_to_id;
	}

	public void setAssigned_to_id(int asigned_to_id) {
		this.assigned_to_id = asigned_to_id;
	}

	public double getHours_reported() {
		return hours_reported;
	}

	public void setHours_reported(double hours_reported) {
		this.hours_reported = hours_reported;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	
	
	


}
