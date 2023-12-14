package cu.edu.cujae.pweb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

import cu.edu.cujae.pweb.dto.IssueDto;

@ManagedBean
@ViewScoped
public class IssueBean implements Serializable{

    private List<IssueDto> issues;

    private IssueDto selectedIssue;

    private List<IssueDto> selectedIssues;
    
    @PostConstruct
    public void init() {
    	issues=new ArrayList<>();
    	issues.add(new IssueDto("1", "Project 1", "Develop", "Subject 1", "", "Normal", "User 1", 1));
    	issues.add(new IssueDto("2", "Project 2", "Bug", "Subject 2", "", "Urgent", "User 2", 2));
    	issues.add(new IssueDto("3", "Project 3", "Develop", "Subject 3", "", "Normal", "User 3", 3));
    	issues.add(new IssueDto("4", "Project 4", "Bug", "Subject 4", "", "Normal", "User 4", 1));
    	issues.add(new IssueDto("5", "Project 5", "Develop", "Subject 5", "", "Low", "User 1", 4));
    	issues.add(new IssueDto("6", "Project 6", "Feature", "Subject 6", "", "Normal", "User 5", 2));
    	issues.add(new IssueDto("7", "Project 7", "Develop", "Subject 7", "", "Normal", "User 4", 1));
    	issues.add(new IssueDto("8", "Project 8", "Develop", "Subject 8", "", "High", "User 1", 2));
    	issues.add(new IssueDto("9", "Project 9", "Training", "Subject 9", "", "Normal", "User 1", 3));
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
        if (this.selectedIssue.getId() == null) {
            this.selectedIssue.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.issues.add(this.selectedIssue);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDto Added"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDto Updated"));
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

}
