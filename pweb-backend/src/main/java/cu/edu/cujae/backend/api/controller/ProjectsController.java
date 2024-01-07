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
      
        int id_newProject = service.createProject(project);
        if(id_newProject != -1) {
        	return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado");
        }
        else {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un proyecto con ese id");
        }
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateProject(@RequestBody ProjectDto updatedProject) {
      
        int id_updated = service.updateProject(updatedProject);
        if (id_updated != -1) {
            return ResponseEntity.ok("Poryecto actualizado correctamente");
        } else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del proyecto no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable int id) {
        
        int delete_id = service.deleteProject(id);
        if(delete_id != -1)
        	return ResponseEntity.ok("Proyecto eliminada");
        else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del proyecto no existe");
        }
    }
    
    
}
