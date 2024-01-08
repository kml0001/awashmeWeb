package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cu.edu.cujae.pweb.dto.*;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.security.CurrentUserUtils;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

import javax.faces.application.FacesMessage;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private RestService restService;

	@Override
	public List<UserDto> getUsers(){
		System.out.println("--------------------> Pidiendo los usuarios <-----------------------");
		List<UserDto> userList = new ArrayList<UserDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<UserDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/users/", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    System.out.println("Llego a hacer la peticion: " + response);
		    userList = apiRestMapper.mapList(response, UserDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public UserDto getUserById(String userId) {
		UserDto user = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<UserDto> apiRestMapper = new ApiRestMapper<>();

		    UriTemplate template = new UriTemplate("/api/v1/users/{userId}");
		    String uri = template.expand(userId).toString();
		    String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    user = apiRestMapper.mapOne(response, UserDto.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}

	@Override
	public void createUser(UserDto user) {
		ResponseEntity response = restService.POST("/api/v1/users/", user, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_added");
				break;
			case 409:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "userDto_exist");
				break;
			case 404:
				// Código para el status NOT_FOUND (ID de usuario no existe)
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
	public void updateUser(UserDto user) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ResponseEntity response = restService.PUT("/api/v1/users/", params, user, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_updated");
				break;
			case 409:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "userDto_exist");
				break;
			case 410:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "userMail_exist");
				break;
			case 404:
				// Código para el status NOT_FOUND (ID de usuario no existe)
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
	public void deleteUser(String userId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/users/{userId}");
	    String uri = template.expand(userId).toString();
		ResponseEntity response = restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer());
		HttpStatus status = response.getStatusCode();
		int statusCode = status.value();

		switch (statusCode) {
			case 201:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO,  "userDto_deleted");
				break;
			case 409:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "userDto_exist");
				break;
			case 410:
				JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_FATAL,  "userMail_exist");
				break;
			case 404:
				// Código para el status NOT_FOUND (ID de usuario no existe)
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
	public List<UserReportDto> getUserReports(UserFilterDto filter){
		List<UserReportDto> userReportList = new ArrayList<UserReportDto>();
		ApiRestMapper<UserReportDto> apiRestMapper = new ApiRestMapper<>();

		Object response = restService.POST("/api/v1/users/report", filter, String.class, CurrentUserUtils.getTokenBearer()).getBody();

		System.out.println(response);
		try {
			userReportList = apiRestMapper.mapList(response, UserReportDto.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userReportList;
	}
	
}
