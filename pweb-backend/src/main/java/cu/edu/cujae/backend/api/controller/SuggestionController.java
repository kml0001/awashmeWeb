package cu.edu.cujae.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import cu.edu.cujae.backend.core.dto.SuggestionDto;
import cu.edu.cujae.backend.core.security.UserPrincipal;
import cu.edu.cujae.backend.service.SuggestionService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/suggestion")
public class SuggestionController {

	
	
	@Autowired
    private SuggestionService service;
	
    @GetMapping("/")
    public ResponseEntity<List<SuggestionDto>> getSuggestions() {
        
        List<SuggestionDto> suggestions = service.getSuggestion();
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSuggestionById(@PathVariable int id) {
        // Obtener una suggestion específica de la base de datos por su ID
    	SuggestionDto suggestion = service.getSuggestionById(id);

        if (suggestion != null) {
            return ResponseEntity.ok(suggestion);
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la suggestion no existe");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createSuggestion(@RequestBody SuggestionDto suggestion) {
        
        int suggestion_id = service.createSuggestion(suggestion);
        if(suggestion_id != -1)
        	return ResponseEntity.status(HttpStatus.CREATED).body("Suggestion creada");
        else
        	return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSuggestion(@PathVariable int id, @RequestBody SuggestionDto updatedSuggestion) {
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    	
    	SuggestionDto suggestion = service.getSuggestionById(id);
    	
    	if(!principal.getRoleList().contains("Admin") &&  principal.getId().equals(String.valueOf(suggestion.getAuthor_id())) ) {
    		 return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reune los privilegios para realizar la operacion .Solo puede editar sus sugerencias");
    	}
    	
    	
        int id_updated = service.updateSuggestion(id, updatedSuggestion);
        if (id_updated != -1) {
            return ResponseEntity.ok("Suggestion actualizada");
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSuggestion(@PathVariable int id) {
     
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    	
    	SuggestionDto suggestion = service.getSuggestionById(id);
    	
    	if(!principal.getRoleList().contains("Admin") &&  principal.getId().equals(String.valueOf(suggestion.getAuthor_id())) ) {
    		
    	}
    	
    	int delete_id = service.deleteSuggestion(id);
    	if(delete_id != -1)
    		 return ResponseEntity.ok("Suggestion eliminada");
    	else {
    		 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
    	}
    }


}