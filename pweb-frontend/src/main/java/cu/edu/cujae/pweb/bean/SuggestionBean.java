package cu.edu.cujae.pweb.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.SuggestionDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.SuggestionService;

@Component
@ManagedBean
@ViewScoped
public class SuggestionBean{

    private List<SuggestionDto> suggestions;

    private SuggestionDto selectedSuggestion;

    private List<SuggestionDto> selectedSuggestions;
    
    private List<UserDto> selectedMembers;
    
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

	@PostConstruct
    public void init() {
    	suggestions = suggestions == null? suggestionService.getSuggestions(): suggestions;
    }

    public List<SuggestionDto> getSuggestions() {
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

    public void saveSuggestion() {
//        if (this.selectedSuggestion.getId() == null) {
//            this.selectedSuggestion.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
//            this.suggestions.add(this.selectedSuggestion);
//            suggestionService.createSuggestion(selectedSuggestion);
//            suggestions = suggestionService.getSuggestions();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SuggestionDto Added"));
//        }
//        else {
//        	suggestionService.updateSuggestion(selectedSuggestion);
//        	suggestions = suggestionService.getSuggestions();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SuggestionDto Updated"));
//        }
//
//        PrimeFaces.current().executeScript("PF('manageSuggestionDtoDialog').hide()");
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-suggestions");
    }

    public void deleteSuggestionDto() {
        this.suggestions.remove(this.selectedSuggestion);
        this.selectedSuggestions.remove(this.selectedSuggestion);
        this.selectedSuggestion = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SuggestionDto Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-suggestions");
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

}
