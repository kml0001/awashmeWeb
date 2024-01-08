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

import cu.edu.cujae.backend.core.dto.ProjectFilterDto;
import cu.edu.cujae.backend.core.dto.ProjectDto;
import cu.edu.cujae.backend.core.query.dto.ProjectReportDto;
import cu.edu.cujae.backend.core.service.ProjectServiceImp;
import cu.edu.cujae.backend.core.service.QueryImplement;
import cu.edu.cujae.backend.core.util.CurrentUserUtils;



@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsController {


	@Autowired
	private ProjectServiceImp service;
	@GetMapping("/")
	public ResponseEntity<List<ProjectDto>> getProjects() {
		List <ProjectDto> temp = service.getProjects();
		return ResponseEntity.ok(temp);
	}



	@PostMapping("/report")
	public ResponseEntity<List<ProjectReportDto>> getProjectsReport(@RequestBody ProjectFilterDto filter) {
		List <ProjectReportDto> temp = QueryImplement.getProjectReports(filter);
		return ResponseEntity.ok(temp);
	}


	@GetMapping("/{id}")
	public ResponseEntity<Object> getProjectById(@PathVariable int id) {

		ProjectDto project = service.getProjectById(id);
		if (project != null) {
			return ResponseEntity.ok(project);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del proyecto no existe");
		}
	}

	@PostMapping("/")
	public ResponseEntity<Object> createProject(@RequestBody ProjectDto project) {

		int id = service.createProject(project);

		switch (id) {
		case 1:
			return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado");
		case 0:
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un proyecto con ese nombre");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
		}
	}

	@PutMapping("/")
	public ResponseEntity<Object> updateProject(@RequestBody ProjectDto updatedProject) {

		// Verificar si el usuario tiene permisos para actualizar el proyecto
		if (CurrentUserUtils.getUserRole().indexOf("Project Manager") == -1 && CurrentUserUtils.getUserId() != updatedProject.getProject_manager()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reúne los privilegios para modificar esta tarea");
		}

		int id = service.updateProject(updatedProject);
		switch (id) {
		case 1:
			return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto actualizado");
		case 0:
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un proyecto con ese nombre");
		case 2:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de proyecto no existe");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProject(@PathVariable int id) {
		
		ProjectDto project = service.getProjectById(id);
		
		if (CurrentUserUtils.getUserRole().indexOf("Project Manager") == -1 && CurrentUserUtils.getUserId() != project.getProject_manager()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No reúne los privilegios para modificar esta tarea");
		}
		int projectId = service.deleteProject(id);
		
		switch (projectId) {
		case 1:
			return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto Eliminado");
		case 2:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id de proyecto no existe");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
		}

	}
}
