package cu.edu.cujae.pweb.service;

import java.util.List;

import cu.edu.cujae.pweb.dto.ProjectDto;

public interface ProjectService {
	List<ProjectDto> getProjects();
	ProjectDto getProjectById(String ProjectId);
	void createProject(ProjectDto Project);
	void updateProject(ProjectDto Project);
	void deleteProject(String id);
}
