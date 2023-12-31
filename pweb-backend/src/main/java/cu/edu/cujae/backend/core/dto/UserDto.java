package cu.edu.cujae.backend.core.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UserDto {
	

		@ApiModelProperty(hidden = true)
		private int id;
		private String firstname;
		private String lastname; 
		private String mail;
		private String passwd;
		private List<RoleDto> RoleList;	
		public UserDto(int id, String firstname, String lastname, String mail, String passwd,
				List<RoleDto> RoleList) {
			super();
			this.id = id;
			this.firstname = firstname;
			this.lastname = lastname;
			this.mail = mail;
			this.passwd = passwd;
			this.RoleList = RoleList;
		}
		public UserDto(){
			
		}
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
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
