package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserDto;
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
	private List<Integer> selectedRolesId;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	
	public ManageUserBean() {
		
	}
	
	@PostConstruct
    public void init() {
		this.selectedUser = null;
		this.userDto = null;
	    users = users == null ? userService.getUsers() : users;
	    System.out.println("Inicializo users: " + users);
		roles = roleService.getRoles();
		System.out.println("Inicializo roles: " + roles);
    }
	
	public void openNew() {
        this.selectedUser = new UserDto();
    }
	
	public void openForEdit() {
		List<RoleDto> userRoles = this.selectedUser.getRoleList();
//		this.selectedRoles = roles.stream().map(r -> r.getId()).toArray(Long[]::new);
	}
	
	public void saveUser() {
//    	System.out.println("rarara");
//        this.selectedIssue.setProject_id(selectedProjectid);
//        this.selectedIssue.setAssigned_to_id(selectedUserid);
//        this.selectedIssue.setAuthor_id(1);
//        
//        if (this.selectedIssue.getId() == -1) {
//            //this.selectedIssue.setId(Integer.valueOf(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9)));
//            issueService.createIssue(selectedIssue);
//            issues = issueService.getIssues();
//            
//            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_added");
//            System.out.println("entro al if");
//        }
//        else {
//        	issueService.updateIssue(selectedIssue);
//        	issues = issueService.getIssues();
//        	JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_updated");
//            System.out.println("entro al else");
//            
//        }
//
//        PrimeFaces.current().executeScript("PF('manageIssueDialog').hide()");
//        PrimeFaces.current().ajax().update("form:dt-issues");
		
		
		
		
		
        if (this.selectedUser.getId() == -1) {
//            List<RoleDto> rolesToAdd = new ArrayList<RoleDto>();
//            
//            for (RoleDto roleDto : this.selectedRoles) {
//				rolesToAdd.add(roleDto);
//			}
//            for(int i = 0; i < this.selectedRoles.length; i++) {
//            	rolesToAdd.add(roleService.getRolesById(String.valueOf(selectedRoles[i])));
//            }
        	System.out.println("Mira un rol: " + this.selectedUser.getRoleList().get(0).getRoleName());
            this.userService.createUser(this.selectedUser);
            this.users = this.userService.getUsers();
            
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_added");
        }
        else {
        	this.userService.updateUser(this.selectedUser);
        	this.users = userService.getUsers();
        	JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_updated");
        }

        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");//Este code permite cerrar el dialog cuyo id es manageUserDialog. Este identificador es el widgetVar
        PrimeFaces.current().ajax().update("form:dt-users");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
    }

	//Permite eliminar un usuario
    public void deleteUser() {
    	try {
    		this.users.remove(this.selectedUser);
            this.selectedUser = null;
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_removed");
            PrimeFaces.current().ajax().update("form:dt-users");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
		} catch (Exception e) {
			JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
		}
        
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
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public List<Integer> getSelectedRolesId() {
		return selectedRolesId;
	}

	public void setSelectedRolesId(List<Integer> selectedRolesId) {
		this.selectedRolesId = selectedRolesId;
	}

}
