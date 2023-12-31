package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.dto.SuggestionDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

@Service
public class SuggestionServiceImpl implements SuggestionService{
	@Autowired
	private RestService restService;

	@Override
	public List<SuggestionDto> getSuggestions(){
		List<SuggestionDto> SuggestionList = new ArrayList<SuggestionDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<SuggestionDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/suggestions", params, String.class).getBody();
		    SuggestionList = apiRestMapper.mapList(response, SuggestionDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SuggestionList;
	}

	@Override
	public SuggestionDto getSuggestionById(String SuggestionId) {
		SuggestionDto Suggestion = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<SuggestionDto> apiRestMapper = new ApiRestMapper<>();
		    
		    UriTemplate template = new UriTemplate("/api/v1/suggestions/{suggestionId}");
		    String uri = template.expand(SuggestionId).toString();
		    String response = (String)restService.GET(uri, params, String.class).getBody();
		    Suggestion = apiRestMapper.mapOne(response, SuggestionDto.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Suggestion;
	}

	@Override
	public void createSuggestion(SuggestionDto Suggestion) {
		restService.POST("/api/v1/suggestions", Suggestion, String.class).getBody();
	}

	@Override
	public void updateSuggestion(SuggestionDto Suggestion) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		restService.PUT("/api/v1/suggestions", params, Suggestion, String.class).getBody();
	}

	@Override
	public void deleteSuggestion(String SuggestionId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/suggestions/{suggestionId}");
	    String uri = template.expand(SuggestionId).toString();
		restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
