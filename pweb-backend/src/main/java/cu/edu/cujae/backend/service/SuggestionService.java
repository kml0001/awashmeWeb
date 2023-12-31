package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.dto.Suggestion;

public interface SuggestionService {

    List<Suggestion> getSuggestion();

    Suggestion getSuggestionById(int id);

    int createSuggestion(Suggestion sugerencia);

    int updateSuggestion(int id, Suggestion updatedSugerencia);

    int deleteSuggestion(int id);
}