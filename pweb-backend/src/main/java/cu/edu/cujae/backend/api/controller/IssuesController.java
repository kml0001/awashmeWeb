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

import cu.edu.cujae.backend.core.dto.IssueDto;
import cu.edu.cujae.backend.service.IssuesService;


@RestController
@RequestMapping("/api/v1/issues")
public class IssuesController {

	@Autowired
    private IssuesService service;
	
	    @GetMapping("")
	    public ResponseEntity<List<IssueDto>> getIssues() {
	        List<IssueDto> issues = service.getIssues();
	        System.out.println(issues.get(0).getPersona_asignada());
	        return ResponseEntity.ok(issues);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Object> getIssueById(@PathVariable int id) {
	        IssueDto issue = service.getIssueById(id);

	        if (issue != null) {
	            return ResponseEntity.ok(issue);
	        } else {
	        	
	            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
	        }
	    }

	    @PostMapping("")
	    public ResponseEntity<Object> createIssue(@RequestBody IssueDto issue) {
	        int created_id = service.createIssue(issue);
	        if(created_id != -1)
	        	return ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada");
	        else {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body("La tarea ya existe");
	        }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Object> updateIssue(@PathVariable int id, @RequestBody IssueDto updatedIssue) {
	        int id_created = service.updateIssue(id, updatedIssue);
	        if (id_created != -1) {
	            return ResponseEntity.ok("Tarea actualizada");
	        } else {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteIssue(@PathVariable int id) {
	        int delete_id = service.deleteIssue(id);
	        if(delete_id != -1)
	        	return ResponseEntity.ok("Tarea eliminada");
	        else {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
	        }
	    }
	
	
}
