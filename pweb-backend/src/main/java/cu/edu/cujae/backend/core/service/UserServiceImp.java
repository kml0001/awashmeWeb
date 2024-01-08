package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.RoleService;
import cu.edu.cujae.backend.service.UserService;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private RoleService roleservice;
	
	@Override
	public int createUser(UserDto user) {
		String insertSQL = "INSERT INTO users (username, lastname, mail, passwd) VALUES (?, ?, ?, ?)";
		int status = 2;
		try (Connection conn = ConnectionImp.getConnection();
				PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getFullname());
			stmt.setString(3, user.getMail());
			stmt.setString(4,encodePass(user.getPasswd()));

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				status = 1;

			}

		} catch (SQLException e) {
			// Captura específicamente la excepción de violación de llave foránea
			if (e.getMessage().contains("ERROR 1")) {
				System.out.println("Nombre de usuario en uso");
				status = 0;
			} else {
				e.printStackTrace();
				status = -1;
			}
		}
		return status;
	}

	@Override
	public int updateUser(UserDto updatedUser) {
		int staus = 2;
		System.out.print(updatedUser.getPasswd() + "<-------------------------------PASSW-------------");
		String updateSQL = "UPDATE users SET username=?, lastname=?, mail=?, passwd=? WHERE id=?";

		try (Connection conn = ConnectionImp.getConnection();
				PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

			stmt.setString(1, updatedUser.getUsername());
			stmt.setString(2, updatedUser.getFullname());
			stmt.setString(3, updatedUser.getMail());
			stmt.setString(4, updatedUser.getPasswd());
			stmt.setInt(5, updatedUser.getId());

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				// Ahora, actualizar roles asociados al usuario
				roleservice.updateRolesForUser(updatedUser.getId(),updatedUser.getRoleList() );
				staus = 1;
			} 
		} catch (SQLException e) {
			if (e.getMessage().contains("ERROR 1")) {
				System.out.println("Ya existe un usuario con ese nombre");
				staus = 0;
			} else if (e.getMessage().contains("ERROR 5")) {
				System.out.println("Ya existe un usuario con esa direccion de correo");
				staus = 3;
			}
		}
		return staus;
	}

	@Override
	public List<UserDto> listUsers() {
        String selectAllSQL = "SELECT * FROM users ORDER BY id DESC";
        List<UserDto> users = new ArrayList<>();

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectAllSQL);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                UserDto user = mapResultSetToUser(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
	
	@Override
	public UserDto getUserById(int userId) {
        String selectByIdSQL = "SELECT * FROM users WHERE id = ?";
        UserDto user = null;

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectByIdSQL)) {

            stmt.setInt(1, userId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

	@Override
	public UserDto getUserByUsername(String username) {
        String selectByUsernameSQL = "SELECT * FROM users where username = ?";
        UserDto user = null;

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectByUsernameSQL)) {
    
            stmt.setString(1, username);
            try (ResultSet resultSet = stmt.executeQuery()) {
        
            	
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

	@Override
	public int deleteUser(int userId) throws SQLException {
		 String deleteSQL = "DELETE FROM users WHERE id = ?";
		 int status = 0;
	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

	            stmt.setInt(1, userId);

	            int rowsAffected = stmt.executeUpdate();

	            if (rowsAffected > 0) {
	                // También elimina los roles asociados al usuario
	            	roleservice.deleteRolesForUser(userId);
	                status = 1;
	            } 
	        } catch (SQLException e) {
	            e.printStackTrace();
	            status = -1;
	        }
	        return status;
	}

	private String encodePass(String password) {
		
		return new BCryptPasswordEncoder().encode(password);
		
	}

    // Método auxiliar para mapear un conjunto de resultados (ResultSet) a un objeto UserDto
    private UserDto mapResultSetToUser(ResultSet resultSet) throws SQLException {
        UserDto user = new UserDto();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setFullname(resultSet.getString("lastname"));
        user.setMail(resultSet.getString("mail"));
        user.setPasswd((resultSet.getString("passwd")));

        // Recuperar roles asociados al usuario, si es necesario
        List<RoleDto> roles = roleservice.getRolesByUserId(resultSet.getInt("id"));
        user.setRoleList(roles);
        return user;
    }

}
