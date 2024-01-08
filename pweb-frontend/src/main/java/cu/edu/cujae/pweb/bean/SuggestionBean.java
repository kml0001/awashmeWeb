package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.SuggestionDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.service.SuggestionService;
import cu.edu.cujae.pweb.utils.JsfUtils;

@Component
@ManagedBean
@ViewScoped
public class SuggestionBean{

    private List<SuggestionDto> suggestions;

    private SuggestionDto selectedSuggestion;

    private List<SuggestionDto> selectedSuggestions;
    
    private List<UserDto> selectedMembers;
    
    private List<String> urgencyList = new ArrayList<>();
    private String selectedUrgency;
    
    private List<String> importanceList = new ArrayList<>();
    private String selectedImportance;
    
    private int authorId;
    
    
    @Autowired
    private SuggestionService suggestionService;
    
    public SuggestionService getSuggestionService() {
		return suggestionService;
	}

	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	public void setSuggestions(List<SuggestionDto> suggestions) {
		this.suggestions = suggestions;
	}

    public List<SuggestionDto> getSuggestions() {
        if(this.suggestions == null){
            this.suggestions = this.suggestionService.getSuggestions();
        }
        return suggestions;
    }

    public SuggestionDto getSelectedSuggestion() {
        return selectedSuggestion;
    }

    public void setSelectedSuggestion(SuggestionDto selectedSuggestion) {
        this.selectedSuggestion = selectedSuggestion;
    }

    public List<SuggestionDto> getSelectedSuggestions() {
        return this.selectedSuggestions;
    }

    public void setSelectedSuggestions(List<SuggestionDto> selectedSuggestions) {
        this.selectedSuggestions = selectedSuggestions;
    }

    
    public List<UserDto> getSelectedMembers() {
		return selectedMembers;
	}

	public void setSelectedMembers(List<UserDto> selectedMembers) {
		this.selectedMembers = selectedMembers;
	}

	public void openNew() {
        this.selectedSuggestion = new SuggestionDto();
    }

	public void openForEdit() {							
		this.selectedUrgency = this.selectedSuggestion.getUrgency();
		this.selectedImportance = this.selectedSuggestion.getImportance();
	}
	
    public void saveSuggestion() {
    	this.selectedSuggestion.setAuthor_id(CurrentUserUtils.getUserId());
    	this.selectedSuggestion.setUrgency(this.selectedUrgency);
    	this.selectedSuggestion.setImportance(this.selectedImportance);
        if (this.selectedSuggestion.getId() == -1) {
            this.suggestionService.createSuggestion(this.selectedSuggestion);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "suggestionDto_added");
            System.out.println("entro al if");
        }
        else {
        	System.out.println("subject en el else: " + this.selectedSuggestion.getSubject());
        	this.suggestionService.updateSuggestion(this.selectedSuggestion);
        	JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "suggestionDto_updated");
            System.out.println("entro al else");
        }
        
    	this.suggestions = suggestionService.getSuggestions();
        PrimeFaces.current().executeScript("PF('manageSuggestionDialog').hide()");
        PrimeFaces.current().ajax().update("form:ac-suggestions");
    }

    public void deleteSuggestion() {
    	System.out.println(selectedSuggestion.getId());
        this.suggestionService.deleteSuggestion(String.valueOf(selectedSuggestion.getId()));
        this.suggestions = suggestionService.getSuggestions();
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "suggestionDto_deleted");
        PrimeFaces.current().ajax().update("form:ac-suggestions");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSuggestions()) {
            int size = this.selectedSuggestions.size();
            return size > 1 ? size + " suggestions selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedSuggestions() {
        return this.selectedSuggestions != null && !this.selectedSuggestions.isEmpty();
    }

    public void deleteSelectedSuggestions() {
        this.suggestions.removeAll(this.selectedSuggestions);
        this.selectedSuggestions = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SuggestionDtos Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-suggestions");
        PrimeFaces.current().executeScript("PF('dtSuggestionDtos').clearFilters()");
    }

	public List<String> getUrgencyList() {
		this.urgencyList = this.suggestionService.getUrgencyList();
		return this.urgencyList;
	}

	public void setUrgencyList(List<String> urgencyList) {
		this.urgencyList = urgencyList;
	}

	public List<String> getImportanceList() {
		this.importanceList = this.suggestionService.getImportanceList();
		return this.importanceList;
	}

	public void setImportanceList(List<String> importanceList) {
		this.importanceList = importanceList;
	}

	public String getSelectedUrgency() {
		return selectedUrgency;
	}

	public void setSelectedUrgency(String selectedUrgency) {
		this.selectedUrgency = selectedUrgency;
	}

	public String getSelectedImportance() {
		return selectedImportance;
	}

	public void setSelectedImportance(String selectedImportance) {
		this.selectedImportance = selectedImportance;
	}

	public int getAuthorId() {
		this.authorId = CurrentUserUtils.getUserId();
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
    
	public void changeListener(SuggestionDto sugg){
		System.out.println("TabChangeEvent Has Fired By ::" + sugg.getSubject());
		this.selectedSuggestion = sugg;

	}

}
