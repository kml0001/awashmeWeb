package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.dto.SuggestionDto;

public interface SuggestionService {

    List<SuggestionDto> getSuggestion();

    SuggestionDto getSuggestionById(int id);

    int createSuggestion(SuggestionDto sugerencia);

    int updateSuggestion(SuggestionDto updatedSugerencia);

    int deleteSuggestion(int id);
}