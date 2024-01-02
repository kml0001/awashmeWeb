package cu.edu.cujae.pweb.dto;



public class ProjectDto {

	private int id;

	private String created_on;

	private String updated_on;
	
	private String name;
	private String description;
	private String status;
	private Boolean is_public; 
	private int project_manager;
	private String closed_on;
	
	public ProjectDto(int id, String created_on, String updated_on, String name, String description, String status,
			Boolean is_public, int project_manager ,String closed_on) {
		super();
		this.id = id;
		this.created_on = created_on;
		this.updated_on = updated_on;
		this.name = name;
		this.description = description;
		this.status = status;
		this.is_public = is_public;
		this.project_manager = project_manager;
		this.closed_on = closed_on;
	}
	
	public ProjectDto() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIs_public() {
		return is_public;
	}
	public void setIs_public(Boolean is_public) {
		this.is_public = is_public;
	}
	public int getProject_manager() {
		return project_manager;
	}
	public void setProject_manager(int project_manager) {
		this.project_manager = project_manager;
	}

	public String getClosed_on() {
		return closed_on;
	}

	public void setClosed_on(String closed_on) {
		this.closed_on = closed_on;
	}

	
}