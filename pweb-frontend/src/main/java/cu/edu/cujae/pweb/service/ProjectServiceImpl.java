package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private RestService restService;

	@Override
	public List<ProjectDto> getProjects(){
		List<ProjectDto> ProjectList = new ArrayList<ProjectDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<ProjectDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/projects", params, String.class).getBody();
		    ProjectList = apiRestMapper.mapList(response, ProjectDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ProjectList;
	}

	@Override
	public ProjectDto getProjectById(String ProjectId) {
		ProjectDto Project = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<ProjectDto> apiRestMapper = new ApiRestMapper<>();
		    
		    UriTemplate template = new UriTemplate("/api/v1/projects/{projectId}");
		    String uri = template.expand(ProjectId).toString();
		    String response = (String)restService.GET(uri, params, String.class).getBody();
		    Project = apiRestMapper.mapOne(response, ProjectDto.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Project;
	}

	@Override
	public void createProject(ProjectDto Project) {
		restService.POST("/api/v1/projects", Project, String.class).getBody();
	}

	@Override
	public void updateProject(ProjectDto Project) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		restService.PUT("/api/v1/projects", params, Project, String.class).getBody();
	}

	@Override
	public void deleteProject(String ProjectId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/projects/{projectId}");
	    String uri = template.expand(ProjectId).toString();
		restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
