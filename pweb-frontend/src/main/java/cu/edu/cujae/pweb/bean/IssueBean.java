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

import cu.edu.cujae.pweb.dto.IssueDto;
import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.IssueService;
import cu.edu.cujae.pweb.service.ProjectService;
import cu.edu.cujae.pweb.service.UserService;

@Component
@ManagedBean
@ViewScoped
public class IssueBean{

    private List<IssueDto> issues;

    private IssueDto selectedIssue;

    private List<IssueDto> selectedIssues;
    
    private List<ProjectDto> projects;

    private ProjectDto selectedProject;
    
    private List<UserDto> users;
    
    private UserDto selectedUser;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private IssueService issueService;
    
    @Autowired
    private ProjectService projectService;
    @PostConstruct
    public void init() {
    	issues = issues == null? issueService.getIssues(): issues;
    	projects = projects == null? projectService.getProjects(): projects;
    	users = users == null? userService.getUsers(): users;
    	System.out.println("sssss" + users);
    }

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
    }

    public void saveIssue() {
    	System.out.println("rarara");
        if (String.valueOf(this.selectedIssue.getId()) == null) {
            this.selectedIssue.setId(Integer.valueOf(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9)));
            this.issues.add(this.selectedIssue);
            issueService.createIssue(selectedIssue);
            issues = issueService.getIssues();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDto Added"));
            System.out.println("entro al if");
        }
        else {
        	issueService.updateIssue(selectedIssue);
        	issues = issueService.getIssues();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDto Updated"));
            System.out.println("entro al else");
        }

        PrimeFaces.current().executeScript("PF('manageIssueDtoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-issues");
    }

    public void deleteIssueDto() {
        this.issues.remove(this.selectedIssue);
        this.selectedIssues.remove(this.selectedIssue);
        this.selectedIssue = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDto Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-issues");
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

}
