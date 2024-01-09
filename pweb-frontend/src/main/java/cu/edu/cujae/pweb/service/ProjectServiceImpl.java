package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cu.edu.cujae.pweb.utils.JsfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.dto.ProjectFilterDto;
import cu.edu.cujae.pweb.dto.ProjectReportDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

import javax.faces.application.FacesMessage;

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
		    String response = (String)restService.GET("/api/v1/projects/", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    
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
		    String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    Project = apiRestMapper.mapOne(response, ProjectDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Project;
	}

	@Override
	public void createProject(ProjectDto Project) {
		ResponseEntity response = restService.POST("/api/v1/projects/", Project, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "projectDto_added");
				break;
			case 409:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "projectDto_exist");
				break;
			case 500:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "message_error");
				break;
			// Puedes agregar más casos según sea necesario
			default:
				// Código para otros códigos de status
				break;
		}
	}

	@Override
	public void updateProject(ProjectDto Project) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ResponseEntity response = restService.PUT("/api/v1/projects/", params, Project, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "projectDto_updated");
				break;
			case 409:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "projectDto_exist");
				break;
			case 500:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "message_error");
				break;
			// Puedes agregar más casos según sea necesario
			default:
				// Código para otros códigos de status
				break;
		}
	}

	@Override
	public void deleteProject(String ProjectId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/projects/{projectId}");
	    String uri = template.expand(ProjectId).toString();
		ResponseEntity response = restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "projectDto_deleted");
				break;
			case 500:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "message_error");
				break;
			// Puedes agregar más casos según sea necesario
			default:
				// Código para otros códigos de status
				break;
		}
	}
	
//	@Override
//	public void getProjectReports(ProjectFilterDto Issue) {
//		Object asd = restService.POST("/api/v1/projects/report", Issue, String.class).getBody();
//		System.out.println("dasss" + asd);
//	}
	@Override
	public List<ProjectReportDto> getProjectReports(ProjectFilterDto filter){
		List<ProjectReportDto> projectReportList = new ArrayList<ProjectReportDto>();
		ApiRestMapper<ProjectReportDto> apiRestMapper = new ApiRestMapper<>();
		
		System.out.println("filtro min: " + filter.getMinParticipants());
		System.out.println("filtro max: " + filter.getMaxParticipants());
		
		Object response = restService.POST("/api/v1/projects/report", filter, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		
		System.out.println(response);
		try {
			projectReportList = apiRestMapper.mapList(response, ProjectReportDto.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectReportList;
	}
}
