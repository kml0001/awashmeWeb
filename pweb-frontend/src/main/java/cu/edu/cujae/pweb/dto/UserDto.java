package cu.edu.cujae.pweb.dto;

import java.util.ArrayList;
import java.util.List;


public class UserDto {
	
	private int id;
	private String username;
	private String fullname; 
	private String mail;
	private String passwd;
	private List<RoleDto> roleList;	
	public UserDto(int id, String username, String fullname, String mail, String passwd,
			List<RoleDto> roleList) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.mail = mail;
		this.passwd = passwd;
		this.roleList = roleList;
	}
	public UserDto(){
		this.roleList = new ArrayList<>();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String lastname) {
		this.fullname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public List<RoleDto> getRoles() {
		return roleList;
	}
	public void setRoles(List<RoleDto> RoleList) {
		this.roleList = RoleList;
	}

	
}
