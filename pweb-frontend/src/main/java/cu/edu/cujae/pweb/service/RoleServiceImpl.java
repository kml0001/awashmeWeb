package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RestService restService;
	@Override
	public List<RoleDto> getRoles() {
		List<RoleDto> RoleList = new ArrayList<RoleDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<RoleDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/roles", params, String.class).getBody();
		    RoleList = apiRestMapper.mapList(response, RoleDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return RoleList;
	}

	@Override
	public List<RoleDto> getRolesByUser(Long userId) {
		return null;
		//return getRoles().stream().filter(r -> r.getId() == userId).collect(Collectors.toList());
	}

	@Override
	public List<RoleDto> getRolesByName(String name) {
		return getRoles().stream().filter(r -> r.getRoleName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public RoleDto getRolesById(Long roleId) {
		return getRoles().stream().filter(r -> r.getId().equals(roleId)).findFirst().get();
	}

	@Override
	public void createRole(RoleDto Role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRole(RoleDto Role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRole(String id) {
		// TODO Auto-generated method stub
		
	}

}
