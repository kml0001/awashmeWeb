package cu.edu.cujae.pweb.bean;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.RoleService;

@Component
@ManagedBean
@ViewScoped
public class RoleBean{

    private List<RoleDto> roles;

    private RoleDto selectedRole;

    private List<RoleDto> selectedRoles;
    
    private List<UserDto> selectedMembers;
    
    @Autowired
    private RoleService roleService;
    
    public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	@PostConstruct
    public void init() {
    	roles = roles == null? roleService.getRoles(): roles;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<RoleDto> getSelectedRoles() {
        return this.selectedRoles;
    }

    public void setSelectedRoles(List<RoleDto> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    
    public List<UserDto> getSelectedMembers() {
		return selectedMembers;
	}

	public void setSelectedMembers(List<UserDto> selectedMembers) {
		this.selectedMembers = selectedMembers;
	}

	public void openNew() {
        this.selectedRole = new RoleDto();
    }

    public void saveRole() {
        if (this.selectedRole.getId() == null) {
            this.selectedRole.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.roles.add(this.selectedRole);
            roleService.createRole(selectedRole);
            roles = roleService.getRoles();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("RoleDto Added"));
        }
        else {
        	roleService.updateRole(selectedRole);
        	roles = roleService.getRoles();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("RoleDto Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageRoleDtoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
    }

    public void deleteRoleDto() {
        this.roles.remove(this.selectedRole);
        this.selectedRoles.remove(this.selectedRole);
        this.selectedRole = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("RoleDto Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedRoles()) {
            int size = this.selectedRoles.size();
            return size > 1 ? size + " roles selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedRoles() {
        return this.selectedRoles != null && !this.selectedRoles.isEmpty();
    }

    public void deleteSelectedRoles() {
        this.roles.removeAll(this.selectedRoles);
        this.selectedRoles = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("RoleDtos Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
        PrimeFaces.current().executeScript("PF('dtRoleDtos').clearFilters()");
    }

}
