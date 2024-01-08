package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.service.RoleService;
import cu.edu.cujae.pweb.service.UserService;
import cu.edu.cujae.pweb.utils.JsfUtils;


@Component 
@ManagedBean
@ViewScoped 
public class ManageUserBean {
	
	private UserDto userDto;
	private UserDto selectedUser;
	private List<UserDto> users;
	
	private List<RoleDto> roles;
	private List<RoleDto> selectedRoles;
	private List<String> selectedRolesId;
	
	private boolean editMode;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	
	public ManageUserBean() {
		
	}
	
	public void openNew() {
        this.selectedUser = new UserDto();
        this.editMode = false;
    }
	
	public void openForEdit() {
		this.editMode = true;
		this.selectedRolesId = new ArrayList<>();
		List<RoleDto> trueRoles = this.selectedUser.getRoleList();
		for (RoleDto roleDto : trueRoles) {
			this.selectedRolesId.add(String.valueOf(roleDto.getId()));
		}
	}
	
	public void saveUser() {
		System.out.println("Probando: " + CurrentUserUtils.getUsername());
    	List<RoleDto> rolesToAdd = new ArrayList<>();
    	for (String roleDtoId : this.selectedRolesId) {
			RoleDto newRol = this.roleService.getRolesById(roleDtoId);
			rolesToAdd.add(newRol);
		}
    	System.out.println("Los roles: " + rolesToAdd);
    	this.selectedUser.setRoleList(rolesToAdd);
    	
        if (this.selectedUser.getId() == -1) {
            this.userService.createUser(this.selectedUser);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_added");
        }
        else {
        	System.out.println("entro en el else");
        	this.userService.updateUser(this.selectedUser);
        	JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_updated");
        }
        this.users = userService.getUsers();
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-users");
    }


    public void deleteUser() {
    	System.out.println(selectedUser.getId());
        this.userService.deleteUser(String.valueOf(selectedUser.getId()));
        this.users = this.userService.getUsers();
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_deleted");
        PrimeFaces.current().ajax().update("form:dt-users");
        
    }

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public UserDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<UserDto> getUsers() {
		if(this.users == null){
			this.users = this.userService.getUsers();
		}

		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public List<RoleDto> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(List<RoleDto> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<RoleDto> getRoles() {
		if(this.roles == null){
			this.roles = this.roleService.getRoles();
		}

		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public List<String> getSelectedRolesId() {
		return selectedRolesId;
	}

	public void setSelectedRolesId(List<String> selectedRolesId) {
		this.selectedRolesId = selectedRolesId;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}
