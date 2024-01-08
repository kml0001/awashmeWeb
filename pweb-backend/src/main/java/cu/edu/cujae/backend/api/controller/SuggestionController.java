package cu.edu.cujae.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cu.edu.cujae.backend.core.dto.SuggestionDto;
import cu.edu.cujae.backend.service.SuggestionService;

@RestController
@RequestMapping("/api/v1/suggestions")
public class SuggestionController {

	
	
	@Autowired
    private SuggestionService service;
	
    @GetMapping("/")
    public ResponseEntity<List<SuggestionDto>> getSuggestions() {
        
        List<SuggestionDto> suggestionss = service.getSuggestion();
        return ResponseEntity.ok(suggestionss);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSuggestionById(@PathVariable int id) {
        // Obtener una suggestions específica de la base de datos por su ID
    	SuggestionDto suggestions = service.getSuggestionById(id);

        if (suggestions != null) {
            return ResponseEntity.ok(suggestions);
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la suggestions no existe");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createSuggestion(@RequestBody SuggestionDto suggestions) {
        
        int id = service.createSuggestion(suggestions);
        switch (id) {
    	case 1:
    		return ResponseEntity.status(HttpStatus.CREATED).body("Sugerencia creada");
    	default:
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    	}
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateSuggestion(@RequestBody SuggestionDto updatedSuggestion) {
        System.out.println("entra a editar en el backedn: " + updatedSuggestion.getId());
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//    	
//    	SuggestionDto suggestions = service.getSuggestionById(id);
//    	
//    	if(!principal.getRoleList().contains("Admin") &&  principal.getId().equals(String.valueOf(suggestions.getAuthor_id())) ) {
//    		 return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reune los privilegios para realizar la operacion .Solo puede editar sus sugerencias");
//    	}
        int id = service.updateSuggestion(updatedSuggestion);
        switch (id) {
    	case 1:
    		return ResponseEntity.status(HttpStatus.CREATED).body("Sugerencia actualizada");
    	case 2:
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La sugerencia no existe");
    	default:
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSuggestion(@PathVariable int id) {
     
    	
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//    	
//    	SuggestionDto suggestions = service.getSuggestionById(id);
//    	
//    	if(!principal.getRoleList().contains("Admin") &&  principal.getId().equals(String.valueOf(suggestions.getAuthor_id())) ) {
//    		
//    	}
    	
    	int delete_id = service.deleteSuggestion(id);
    	if(delete_id != -1)
    		 return ResponseEntity.ok("Suggestion eliminada");
    	else {
    		 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
    	}
    }


}