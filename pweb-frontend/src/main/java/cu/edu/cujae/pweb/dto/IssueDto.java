package cu.edu.cujae.pweb.dto;


public class IssueDto {

	private int id;
	private String subject; 
	private String description;
	private boolean is_private;
	private double done_ratio;
	private String closed_on;
	private String due_date;
	private String start_date;
	private String update_on;
	private String created_om;
	private double estimated_hours;
	private int project_id;
	private int author_id ;
	private int asigned_to_id ;
	
	public IssueDto() {
		super();
	}

	public IssueDto(int id, String subject, String description, boolean is_private, double done_ratio, String closed_on,
			String due_date, String start_date, String update_on, String created_om, double estimated_hours, int project_id,
			int author_id, int asigned_to_id) {
		super();
		this.id = id;
		this.subject = subject;
		this.description = description;
		this.is_private = is_private;
		this.done_ratio = done_ratio;
		this.closed_on = closed_on;
		this.due_date = due_date;
		this.start_date = start_date;
		this.update_on = update_on;
		this.created_om = created_om;
		this.estimated_hours = estimated_hours;
		this.project_id = project_id;
		this.author_id = author_id;
		this.asigned_to_id = asigned_to_id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean Is_private() {
		return is_private;
	}

	public void setIs_private(boolean is_private) {
		this.is_private = is_private;
	}

	public double getDone_ratio() {
		return done_ratio;
	}

	public void setDone_ratio(double done_ratio) {
		this.done_ratio = done_ratio;
	}

	public String getClosed_on() {
		return closed_on;
	}

	public void setClosed_on(String closed_on) {
		this.closed_on = closed_on;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getUpdate_on() {
		return update_on;
	}

	public void setUpdate_on(String update_on) {
		this.update_on = update_on;
	}

	public String getCreated_om() {
		return created_om;
	}

	public void setCreated_om(String created_om) {
		this.created_om = created_om;
	}

	public double getEstimated_hours() {
		return estimated_hours;
	}

	public void setEstimated_hours(double estimated_hours) {
		this.estimated_hours = estimated_hours;
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

	public int getAsigned_to_id() {
		return asigned_to_id;
	}

	public void setAsigned_to_id(int asigned_to_id) {
		this.asigned_to_id = asigned_to_id;
	}






}

