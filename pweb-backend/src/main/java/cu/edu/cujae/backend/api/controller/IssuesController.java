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

import cu.edu.cujae.backend.core.service.IssuesServiceImp;
import cu.edu.cujae.backend.core.util.CurrentUserUtils;


@RestController
@RequestMapping("/api/v1/issues")
public class IssuesController {

	@Autowired
    private IssuesServiceImp service;
	

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

			int id = service.createIssue(issue);
			switch (id) {
			case 1:
				return  ResponseEntity.status(HttpStatus.CREATED).body("El id de la tarea no existe");
			default:
				return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El id de la tarea no existe");
			}
			
	}

	@PutMapping("/")
	public ResponseEntity<Object> updateIssue(@RequestBody IssueDto updatedIssue) {
		
			try {
			if (CurrentUserUtils.getUserRole().indexOf("Project Manager") == -1 && CurrentUserUtils.getUserId() != updatedIssue.getAuthor_id() && CurrentUserUtils.getUserId() != updatedIssue.getAssigned_to_id()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reúne los privilegios para modificar esta tarea");
			}}
			catch (Exception e) {
				// TODO: handle exception
			}
			int id = service.updateIssue(updatedIssue);

			switch (id) {
			case 1:
				return  ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada");
			default:
				return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
			}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteIssue(@PathVariable int id) {

		IssueDto issue = service.getIssueById(id);
		
		if(issue == null) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de la tarea no existe");
		if (CurrentUserUtils.getUserRole().indexOf("Project Manager") == -1 && CurrentUserUtils.getUserId() != issue.getAuthor_id()) 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reúne los privilegios para eliminar esta tarea");
		else {
			service.deleteIssue(id);
			return  ResponseEntity.status(HttpStatus.CREATED).body("Tarea eliminada");
		}



	}


}
