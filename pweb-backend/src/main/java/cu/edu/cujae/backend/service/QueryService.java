package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.query.dto.Query_ProjectListDto;
import cu.edu.cujae.backend.core.query.dto.Query_UserListDto;

public interface QueryService {

	
	List<Query_ProjectListDto> getQuery_ProjectListDto();
	List<Query_UserListDto> getQuery_UserListDto();
	
}
