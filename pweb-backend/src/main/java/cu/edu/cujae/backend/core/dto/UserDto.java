package cu.edu.cujae.backend.core.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UserDto {
	

		@ApiModelProperty(hidden = true)
		private int id;
		private String username;
		private String fullname; 
		private String mail;
		private String passwd;
		private List<RoleDto> RoleList;	
		public UserDto(int id, String username, String fullname, String mail, String passwd,
				List<RoleDto> RoleList) {
			super();
			this.id = id;
			this.username = username;
			this.fullname = fullname;
			this.mail = mail;
			this.passwd = passwd;
			this.RoleList = RoleList;
		}
		public UserDto(){
			this.RoleList = new ArrayList<>();
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
		public String getfullname() {
			return fullname;
		}
		public void setfullname(String lastname) {
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
			return RoleList;
		}
		public void SetRoles(List<RoleDto> RoleList) {
			this.RoleList = RoleList;
		}

		
}
