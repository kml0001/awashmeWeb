package cu.edu.cujae.backend.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.RoleDto;

public interface RoleService {
	
	List<RoleDto> getRolesByUserId(int userId) throws SQLException;
	
	List<RoleDto> listRoles() throws SQLException;
	
	RoleDto getRoleById(int roleId) throws SQLException;
	
	int insertUserRoles(int userid , RoleDto Role) throws SQLException;
	
	void deleteRolesForUser(int userId) throws SQLException;
	
	void updateRolesForUser(int userId, RoleDto updatedRoleList)throws SQLException;
	
}