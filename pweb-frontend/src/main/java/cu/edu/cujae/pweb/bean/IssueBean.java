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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.IssueDto;
import cu.edu.cujae.pweb.service.IssueService;

@Component
@ManagedBean
@ViewScoped
public class IssueBean implements Serializable{

    private List<IssueDto> issues;

    private IssueDto selectedIssue;

    private List<IssueDto> selectedIssues;
    
    @Autowired
    private IssueService issueService;
    
    @PostConstruct
    public void init() {
    	issues = issues == null? issueService.getIssues(): issues;
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
            issueService.createIssue(selectedIssue);
            issues = issueService.getIssues();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("IssueDto Added"));
        }
        else {
        	issueService.updateIssue(selectedIssue);
        	issues = issueService.getIssues();
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
