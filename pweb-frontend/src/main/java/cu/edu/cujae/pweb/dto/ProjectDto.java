package cu.edu.cujae.pweb.dto;

import java.util.List;

public class ProjectDto {

	private	String id;
	private String name; 
	private String description;
	private List<UserDto> members;
	
	
	public ProjectDto() {
		super();
	}
	
	
	
	public ProjectDto(String name, String description, List<UserDto> members) {
		super();
		this.id = null;
		this.name = name;
		this.description = description;
		this.members = members;
	}



	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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



	public List<UserDto> getMembers() {
		return members;
	}



	public void setMembers(List<UserDto> members) {
		this.members = members;
	}


	
	
	
	
}
