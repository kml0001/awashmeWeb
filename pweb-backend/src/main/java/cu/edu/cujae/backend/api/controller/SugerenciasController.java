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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cu.edu.cujae.backend.core.dto.SugerenciaDto;
import cu.edu.cujae.backend.service.SugerenciasService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/sugerencias")
public class SugerenciasController {

	
	
	@Autowired
    private SugerenciasService service;
	
    @GetMapping("/")
    public ResponseEntity<List<SugerenciaDto>> getSugerencias() {
        
        List<SugerenciaDto> sugerencias = service.getSugerencias();
        return ResponseEntity.ok(sugerencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSugerenciaById(@PathVariable int id) {
        // Obtener una sugerencia específica de la base de datos por su ID
        SugerenciaDto sugerencia = service.getSugerenciaById(id);

        if (sugerencia != null) {
            return ResponseEntity.ok(sugerencia);
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la sugerencia no existe");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createSugerencia(@RequestBody SugerenciaDto sugerencia) {
        
        int sugerencia_id = service.createSugerencia(sugerencia);
        if(sugerencia_id != -1)
        	return ResponseEntity.status(HttpStatus.CREATED).body("Sugerencia creada");
        else
        	return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSugerencia(@PathVariable int id, @RequestBody SugerenciaDto updatedSugerencia) {
        
        int id_updated = service.updateSugerencia(id, updatedSugerencia);
        if (id_updated != -1) {
            return ResponseEntity.ok("Sugerencia actualizada");
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSugerencia(@PathVariable int id) {
     
    	int delete_id = service.deleteSugerencia(id);
    	if(delete_id != -1)
    		 return ResponseEntity.ok("Sugerencia eliminada");
    	else {
    		 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
    	}
    }


}