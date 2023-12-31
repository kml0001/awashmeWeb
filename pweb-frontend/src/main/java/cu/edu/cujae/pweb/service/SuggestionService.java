package cu.edu.cujae.pweb.service;

import java.util.List;

import cu.edu.cujae.pweb.dto.SuggestionDto;

public interface SuggestionService {
	List<SuggestionDto> getSuggestions();
	SuggestionDto getSuggestionById(String SuggestionId);
	void createSuggestion(SuggestionDto Suggestion);
	void updateSuggestion(SuggestionDto Suggestion);
	void deleteSuggestion(String id);
}
