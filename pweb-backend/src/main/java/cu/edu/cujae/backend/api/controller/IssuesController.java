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
import cu.edu.cujae.backend.core.security.TokenProvider;
import cu.edu.cujae.backend.core.service.IssuesServiceImp;


@RestController
@RequestMapping("/api/v1/issues")
public class IssuesController {

	@Autowired
    private IssuesServiceImp service;
	
	@Autowired
	private TokenProvider token;
	
	    @GetMapping("/")
	    public ResponseEntity<Object> getIssues() {
	    	
	    	List<IssueDto> issues = service.getIssues();
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

	    @PostMapping("/")
	    public ResponseEntity<Object> createIssue(@RequestBody IssueDto issue) {
	        int created_id = service.createIssue(issue);
	        if(created_id != -1)
	        	return ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada");
	        else {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body("La tarea ya existe");
	        }
	    }

	    @PutMapping("/")
	    public ResponseEntity<Object> updateIssue(@RequestBody IssueDto updatedIssue) {
	        
	    	
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//	    	
//	    	if(principal.getRoleList().indexOf("Project Manager") ==-1 && principal.getId().equals(String.valueOf(updatedIssue.getAssigned_to_id())) ) {
//	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reune los privilegios para modificar esta tarea");
//	    	}
	    	
	    	int id_created = service.updateIssue(updatedIssue);
	        if (id_created != -1) {
	            return ResponseEntity.ok("Tarea actualizada");
	        } else {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteIssue(@PathVariable int id) {
	    	
	    	
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//	    	
//	    	IssueDto issue = service.getIssueById(id);
//	    
//	    	
//	    	if(principal.getRoleList().indexOf("Project Manager") ==-1 && principal.getId().equals(String.valueOf(issue.getAuthor_id()))) {
//	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reune los privilegios para modificar esta tarea");
//	    	}
	    	
	    	
	        int delete_id = service.deleteIssue(id);
	        if(delete_id != -1)
	        	return ResponseEntity.ok("Tarea eliminada");
	        else {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
	        }
	    }
	   
	
}
