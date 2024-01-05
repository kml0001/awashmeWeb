package cu.edu.cujae.backend.service;


import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.UserDto;

public interface UserService {
	
	int createUser(UserDto user) throws SQLException;
	
	int updateUser(UserDto user) throws SQLException;
	
	List<UserDto> listUsers() throws SQLException;
	
	UserDto getUserById(int id) throws SQLException;
	
	UserDto getUserByUsername(String username) throws SQLException;
	
	int deleteUser(int id) throws SQLException;
}
