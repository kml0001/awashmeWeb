package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.dto.ProjectDto;

public interface ProjectsService {

    List<ProjectDto> getProjects();

    ProjectDto getProjectById(int id);

    int createProject(ProjectDto project);

    int updateProject(ProjectDto updatedProject);

    int deleteProject(int id);
}