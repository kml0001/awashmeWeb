package cu.edu.cujae.backend.core.dto;

public class MembersDto {

	
	private int project_id;
	private int user_id;
	
	public MembersDto(int project_id, int user_id) {
		super();
		this.project_id = project_id;
		this.user_id = user_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	

	
	
}
