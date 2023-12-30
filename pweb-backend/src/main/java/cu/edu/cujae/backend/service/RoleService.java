package cu.edu.cujae.backend.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.RoleDto;

public interface RoleService {
	
	List<RoleDto> getRolesByUserId(int userId) throws SQLException;
	
	List<RoleDto> listRoles() throws SQLException;
	
	RoleDto getRoleById(int roleId) throws SQLException;
	
	int insertUserRoles(int userid , List<RoleDto> ListRol) throws SQLException;
	
	void deleteRolesForUser(int userId) throws SQLException;
	
	void updateRolesForUser(int userId, List<RoleDto> updatedRoleList)throws SQLException;
	
}