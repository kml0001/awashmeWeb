package cu.edu.cujae.backend.core.query.dto;

import java.util.List;

import cu.edu.cujae.backend.core.dto.RoleDto;

public class Query_UserListDto {
	
	
	private String username;
	private String lastname; 
	private String mail;
	private List<RoleDto> RoleList;	
	private double Total_hours;
	private int late_issues;
	
	
	
	public Query_UserListDto(String username, String lastname, String mail, List<RoleDto> roleList, double total_hours,
			int late_issues) {
		super();
		this.username = username;
		this.lastname = lastname;
		this.mail = mail;
		RoleList = roleList;
		Total_hours = total_hours;
		this.late_issues = late_issues;
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
	public List<RoleDto> getRoleList() {
		return RoleList;
	}
	public void setRoleList(List<RoleDto> roleList) {
		RoleList = roleList;
	}
	public double getTotal_hours() {
		return Total_hours;
	}
	public void setTotal_hours(double total_hours) {
		Total_hours = total_hours;
	}
	public int getLate_issues() {
		return late_issues;
	}
	public void setLate_issues(int late_issues) {
		this.late_issues = late_issues;
	}
	
	
	
	
	
}
