package cu.edu.cujae.backend.core.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class ProjectDto {

	@ApiModelProperty(hidden = true)
	private int id;
	@ApiModelProperty(hidden = true)
	private String created_on;
	@ApiModelProperty(hidden = true)
	private String updated_on;
	
	
	
	
	
	
	private String name;
	private String identifier;
	private String description;
	private String status;
	private String is_public; 
	private String inherit_members;
	
	
	
	private String project_type;
	
	public ProjectDto(String name, String identifier, String description, String status, String is_public,
			String inherit_members,  String project_type) {
		System.out.println("ASDSADASDDD");
		System.out.println(is_public);
		this.id = -1;
		this.name = name;
		this.identifier = identifier;
		this.description = description;
		this.status = status;
		this.is_public = is_public;
		this.inherit_members = inherit_members;
		this.created_on = new Date().toString();
		this.updated_on = new Date().toString();
		this.project_type = project_type;
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
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
	public String get_public() {
		return is_public;
	}
	public void setpublic(String is_public) {
		this.is_public = is_public;
	}
	public String getInherit_members() {
		return inherit_members;
	}
	public void setInherit_members(String inherit_members) {
		this.inherit_members = inherit_members;
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
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	
	
}
