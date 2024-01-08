package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.ProjectService;
import cu.edu.cujae.pweb.service.UserService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@Component
@ManagedBean
@ViewScoped
public class ProjectBean{

    private List<ProjectDto> projects;

    private ProjectDto selectedProject;

    private List<ProjectDto> selectedProjects;
    
    private List<UserDto> selectedMembers;

    private DualListModel<UserDto> members;

    private List<UserDto> users;
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}

    public List<ProjectDto> getProjects() {
        if(this.projects == null){
            this.projects = this.projectService.getProjects();
        }

        return projects;
    }

    public ProjectDto getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(ProjectDto selectedProject) {
        this.selectedProject = selectedProject;
    }

    public List<ProjectDto> getSelectedProjects() {
        return this.selectedProjects;
    }

    public void setSelectedProjects(List<ProjectDto> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public DualListModel<UserDto> getMembers() {
        if(this.users == null){
            this.users = this.userService.getUsers();
        }
        List<UserDto> membersSource = this.users;
        List<UserDto> membersTarget = new ArrayList<>();

        this.members = new DualListModel<>(membersSource, membersTarget);
        return members;
    }

    public void setMembers(DualListModel<UserDto> member) {
        this.members = member;
    }

    public List<UserDto> getSelectedMembers() {
		return selectedMembers;
	}

	public void setSelectedMembers(List<UserDto> selectedMembers) {
		this.selectedMembers = selectedMembers;
	}

    public void openForEdit(){

    }
    public void openNew() {
        this.selectedProject = new ProjectDto();
    }

    public void saveProject() {
        this.selectedProject.setMembers(this.members.getTarget());

        if (this.selectedProject.getId() == -1) {
            this.projectService.createProject(selectedProject);
            this.projects = projectService.getProjects();

            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "projectDto_added");

        }
        else {
            projectService.updateProject(selectedProject);
            this.projects = projectService.getProjects();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "projectDto_updated");


        }

        PrimeFaces.current().executeScript("PF('manageProjectDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-projects");
    }

    public void deleteProjectDto() {
        this.projects.remove(this.selectedProject);
        this.selectedProjects.remove(this.selectedProject);
        this.selectedProject = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ProjectDto Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-projects");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProjects()) {
            int size = this.selectedProjects.size();
            return size > 1 ? size + " projects selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedProjects() {
        return this.selectedProjects != null && !this.selectedProjects.isEmpty();
    }

    public void deleteSelectedProjects() {
        this.projects.removeAll(this.selectedProjects);
        this.selectedProjects = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ProjectDtos Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-projects");
        PrimeFaces.current().executeScript("PF('dtProjectDtos').clearFilters()");
    }

}
