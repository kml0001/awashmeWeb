package cu.edu.cujae.backend.core.query.dto;

public class Query_ProjectListDto {

	private int id;
	private String name;
	private int participants;
	private int project_manager;
	private String created_on;
	private String closed_on;
	private Double Total_time_fund;
	private double Compliance_status;
	private int Total_issues;
	private int late_issues;
	public Query_ProjectListDto(int id, String name, int participants, int project_manager, String created_on,
			String closed_on, Double total_time_fund, double compliance_status, int total_issues, int late_issues) {
		super();
		this.id = id;
		this.name = name;
		this.participants = participants;
		this.project_manager = project_manager;
		this.created_on = created_on;
		this.closed_on = closed_on;
		Total_time_fund = total_time_fund;
		Compliance_status = compliance_status;
		Total_issues = total_issues;
		this.late_issues = late_issues;
	}
	public Query_ProjectListDto() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParticipants() {
		return participants;
	}
	public void setParticipants(int participants) {
		this.participants = participants;
	}
	public int getProject_manager() {
		return project_manager;
	}
	public void setProject_manager(int project_manager) {
		this.project_manager = project_manager;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getClosed_on() {
		return closed_on;
	}
	public void setClosed_on(String closed_on) {
		this.closed_on = closed_on;
	}
	public Double getTotal_time_fund() {
		return Total_time_fund;
	}
	public void setTotal_time_fund(Double total_time_fund) {
		Total_time_fund = total_time_fund;
	}
	public double getCompliance_status() {
		return Compliance_status;
	}
	public void setCompliance_status(double compliance_status) {
		Compliance_status = compliance_status;
	}
	public int getTotal_issues() {
		return Total_issues;
	}
	public void setTotal_issues(int total_issues) {
		Total_issues = total_issues;
	}
	public int getLate_issues() {
		return late_issues;
	}
	public void setLate_issues(int late_issues) {
		this.late_issues = late_issues;
	}
	
}
