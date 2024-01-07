package cu.edu.cujae.pweb.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.IssueDto;
import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.security.UserPrincipal;
import cu.edu.cujae.pweb.service.IssueService;
import cu.edu.cujae.pweb.service.ProjectService;
import cu.edu.cujae.pweb.service.UserService;

import cu.edu.cujae.pweb.utils.JsfUtils;

@Component
@ManagedBean
@ViewScoped
public class IssueBean{

    private List<IssueDto> issues;

    private IssueDto selectedIssue;

    private List<IssueDto> selectedIssues;
    
    private List<ProjectDto> projects;

    private ProjectDto selectedProject;
    
    private int selectedProjectid;
    
    private List<UserDto> users;
    
    private UserDto selectedUser;
    
    private int selectedUserid;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private IssueService issueService;
    
    @Autowired
    private ProjectService projectService;
   

    public List<IssueDto> getIssues() {
        return issues;
    }

    public IssueDto getSelectedIssue() {
        return selectedIssue;
    }

    public void setSelectedIssue(IssueDto selectedIssue) {
        this.selectedIssue = selectedIssue;
    }

    public List<IssueDto> getSelectedIssues() {
        return selectedIssues;
    }

    public void setSelectedIssues(List<IssueDto> selectedIssues) {
        this.selectedIssues = selectedIssues;
    }

    public void openNew() {
        this.selectedIssue = new IssueDto();
        this.selectedProject = null;
        this.selectedProjectid = -1;
        this.selectedUser = null;
        this.selectedUserid = -1;
        
        issues = issueService.getIssues();
        projects = projectService.getProjects();
        users = userService.getUsers();
        
        
        
        
    }
    
	public void openForEdit() {							
		this.selectedProjectid = this.selectedIssue.getProject_id();
		this.selectedUserid = this.selectedIssue.getAssigned_to_id();
	}

    public void saveIssue() {  
    	
        this.selectedIssue.setProject_id(this.selectedProjectid);
        this.selectedIssue.setAssigned_to_id(this.selectedUserid);
        this.selectedIssue.setAuthor_id(CurrentUserUtils.getUserId());
        
        if (this.selectedIssue.getId() == -1) {
            issueService.createIssue(selectedIssue);
            issues = issueService.getIssues();
            
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_added");
          
        }
        else {
        	issueService.updateIssue(selectedIssue);
        	issues = issueService.getIssues();
        	JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_updated");
         
            
        }

        PrimeFaces.current().executeScript("PF('manageIssueDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-issues");
    }

    public void deleteIssue() {
    
        this.issueService.deleteIssue(String.valueOf(selectedIssue.getId()));
        issues = issueService.getIssues();
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_deleted");
        PrimeFaces.current().ajax().update("form:dt-issues");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedIssues()) {
            int size = this.selectedIssues.size();
            return size > 1 ? size + " issues selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedIssues() {
        return this.selectedIssues != null && !this.selectedIssues.isEmpty();
    }

    public void deleteSelectedIssues() {
        this.issues.removeAll(this.selectedIssues);
        this.selectedIssues = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDtos Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-issues");
        PrimeFaces.current().executeScript("PF('dtIssueDtos').clearFilters()");
    }

	public ProjectDto getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(ProjectDto selectedProject) {
		this.selectedProject = selectedProject;
	}

	public List<ProjectDto> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}

	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public void setIssues(List<IssueDto> issues) {
		this.issues = issues;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public UserDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}

	public int getSelectedProjectid() {
		return selectedProjectid;
	}

	public void setSelectedProjectid(int selectedProjectid) {
		this.selectedProjectid = selectedProjectid;
	}

	public int getSelectedUserid() {
		return selectedUserid;
	}

	public void setSelectedUserid(int selectedUserid) {
		this.selectedUserid = selectedUserid;
	}

}
