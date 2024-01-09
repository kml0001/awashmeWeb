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

import cu.edu.cujae.pweb.dto.IssueDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

import javax.faces.application.FacesMessage;

@Service
public class IssueServiceImpl implements IssueService{
	@Autowired
	private RestService restService;

	@Override
	public List<IssueDto> getIssues(){
		List<IssueDto> IssueList = new ArrayList<IssueDto>();
		
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<IssueDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/issues/", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    System.out.println("--------------------> Pidiendo los issues <-----------------------");
		    IssueList = apiRestMapper.mapList(response, IssueDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return IssueList;
	}

	@Override
	public IssueDto getIssueById(String IssueId) {
		IssueDto Issue = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<IssueDto> apiRestMapper = new ApiRestMapper<>();
		    
		    UriTemplate template = new UriTemplate("/api/v1/issues/{issueId}");
		    String uri = template.expand(IssueId).toString();
		    String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    Issue = apiRestMapper.mapOne(response, IssueDto.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Issue;
	}

	@Override
	public void createIssue(IssueDto Issue) {
		ResponseEntity response = restService.POST("/api/v1/issues/", Issue, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_added");
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
	public void updateIssue(IssueDto Issue) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ResponseEntity response = restService.PUT("/api/v1/issues/", params, Issue, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_updated");
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
	public void deleteIssue(String IssueId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/issues/{issueId}");
	    String uri = template.expand(IssueId).toString();
		ResponseEntity response = restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "issueDto_deleted");
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
	
}
