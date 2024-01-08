package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.service.ProjectService;
import cu.edu.cujae.pweb.service.UserService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@ManagedBean
@ViewScoped
public class ProjectBean{

    private List<ProjectDto> projects;

    private ProjectDto selectedProject;

    private List<ProjectDto> selectedProjects;
    
    private List<String> selectedMembers;
    private List<Integer> selectedMembersId;

    private DualListModel<String> members;

    private List<UserDto> users;
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    private String[] selectedUsersId;

    public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public String[] getSelectedUsersId() {
        return selectedUsersId;
    }

    public void setSelectedUsersId(String[] selectedUsersId) {
        this.selectedUsersId = selectedUsersId;
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

    public List<Integer> getSelectedMembersId() {
        return selectedMembersId;
    }

    public void setSelectedMembersId(List<Integer> selectedMembersId) {
        this.selectedMembersId = selectedMembersId;
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

    public DualListModel<String> getMembers() {
        if(members == null){
            List<String> membersSource = new ArrayList<>();
            List<String> membersTarget = new ArrayList<>();
            for (UserDto u :
                    this.getUsers()) {
                membersSource.add(u.getUsername());
            }

            this.members = new DualListModel<>(membersSource, membersTarget);
        }

        return members;
    }

    public void setMembers(DualListModel<String> member) {
        this.members = member;
    }

    public List<String> getSelectedMembers() {
		return selectedMembers;
	}

	public void setSelectedMembers(List<String> selectedMembers) {
		this.selectedMembers = selectedMembers;
	}

    public void openForEdit(){
        List<String> sourceList = new ArrayList<>();
        List<String> targetList = new ArrayList<>();

        for (UserDto generalUser:
             users) {
            boolean isTarget = false;

            for (UserDto u:
                    selectedProject.getMembers()) {
                if(Objects.equals(generalUser.getUsername(), u.getUsername())){
                    isTarget = true;
                }
                if (isTarget){
                    targetList.add(generalUser.getUsername());
                    break;
                }
            }
            if(!isTarget){
                sourceList.add(generalUser.getUsername());
            }
        }
        this.members.setSource(sourceList);
        this.members.setTarget(targetList);
    }
    public void openNew() {
        this.selectedProject = new ProjectDto();
    }

    public void saveProject() {
        this.selectedProject.setProject_manager(CurrentUserUtils.getUserId());
        List<UserDto> usersToAdd = new ArrayList<>();
        for (String username : this.members.getTarget()) {
            UserDto newUser = this.byUserName(username, this.users);
            usersToAdd.add(newUser);
        }
        System.out.println("Los roles: " + usersToAdd);
        this.selectedProject.setMembers(usersToAdd);


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

    private UserDto byUserName(String username, List<UserDto> allUsers){
        UserDto result = null;
        for (UserDto user : allUsers) {
            if(Objects.equals(user.getUsername(), username)){
                result = user;
            }
        }
        return result;
    }
}
