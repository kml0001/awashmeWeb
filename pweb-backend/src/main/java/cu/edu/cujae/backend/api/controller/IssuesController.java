package cu.edu.cujae.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
import cu.edu.cujae.backend.core.util.CurrentUserUtils;


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
		try {
			int createdId = service.createIssue(issue);

			if (createdId != -1) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada");
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("La tarea ya existe");
			}
		} catch (DataIntegrityViolationException e) {
			// Excepción lanzada por problemas de integridad de datos (como violación de clave única)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error de integridad de datos: " + e.getMessage());
		} catch (DataAccessException e) {
			// Otra excepción de acceso a datos, puedes manejarla según tus necesidades
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos: " + e.getMessage());
		} catch (Exception e) {
			// Captura de excepciones generales
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido: " + e.getMessage());
		}
	}

	@PutMapping("/")
	public ResponseEntity<Object> updateIssue(@RequestBody IssueDto updatedIssue) {
		try {
			System.out.print(CurrentUserUtils.getUserRole());
			if (CurrentUserUtils.getUserRole().indexOf("Project Manager") == -1 && CurrentUserUtils.getUserId() != updatedIssue.getAuthor_id() && CurrentUserUtils.getUserId() != updatedIssue.getAssigned_to_id()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reúne los privilegios para modificar esta tarea");
			}
			int updatedRows = service.updateIssue(updatedIssue);

			if (updatedRows > 0) {
				return ResponseEntity.ok("Tarea actualizada");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El ID de la tarea no existe");
			}
		} catch (DataIntegrityViolationException e) {
			// Excepción lanzada por problemas de integridad de datos (puede ocurrir, por ejemplo, al violar una restricción única)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error de integridad de datos: " + e.getMessage());
		} catch (DataAccessException e) {
			// Otra excepción de acceso a datos (puede abordar problemas más generales de acceso a la base de datos)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de acceso a datos: " + e.getMessage());
		} catch (Exception e) {
			// Captura de excepciones generales
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido: " + e.getMessage());
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
			return ResponseEntity.ok("Tarea eliminada");
		}



	}


}
