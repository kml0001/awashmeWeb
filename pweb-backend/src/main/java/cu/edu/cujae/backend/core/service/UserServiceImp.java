package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        String insertSQL = "INSERT INTO user (firstname, lastname, mail, passwd) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getMail());
            stmt.setString(4, user.getPasswd());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        
                        // Ahora, insertar roles asociados al usuario
                        roleservice.insertUserRoles(userId, user.getRoles());

                        return userId;
                    } else {
                        throw new SQLException("No se pudo obtener el ID generado.");
                    }
                }
            } else {
                throw new SQLException("La inserción no tuvo éxito, no se creó ninguna fila.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna un valor negativo para indicar un error
        }
    }

	@Override
	public int updateUser(int userId, UserDto updatedUser) {
        String updateSQL = "UPDATE user SET firstname=?, lastname=?, mail=?, passwd=? WHERE id=?";

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, updatedUser.getFirstname());
            stmt.setString(2, updatedUser.getLastname());
            stmt.setString(3, updatedUser.getMail());
            stmt.setString(4, updatedUser.getPasswd());
            stmt.setInt(5, userId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Ahora, actualizar roles asociados al usuario
            	roleservice.updateRolesForUser(userId, updatedUser.getRoles());
                return rowsAffected;
            } else {
                return -1; // No se actualizó ninguna fila
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

	@Override
	public List<UserDto> listUsers() {
        String selectAllSQL = "SELECT * FROM user";
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
        String selectByIdSQL = "SELECT * FROM user WHERE id = ?";
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
        String selectByUsernameSQL = "SELECT * FROM user WHERE username = ?";
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
		 String deleteSQL = "DELETE FROM user WHERE id = ?";

	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

	            stmt.setInt(1, userId);

	            int rowsAffected = stmt.executeUpdate();

	            if (rowsAffected > 0) {
	                // También elimina los roles asociados al usuario
	            	roleservice.deleteRolesForUser(userId);
	                return rowsAffected;
	            } else {
	                return -1; // No se eliminó ninguna fila
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1;
	        }
	}

    // Método auxiliar para mapear un conjunto de resultados (ResultSet) a un objeto UserDto
    private UserDto mapResultSetToUser(ResultSet resultSet) throws SQLException {
        UserDto user = new UserDto();
        user.setId(resultSet.getInt("id"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setMail(resultSet.getString("mail"));
        user.setPasswd(resultSet.getString("passwd"));

        // Recuperar roles asociados al usuario, si es necesario
        List<RoleDto> roles = roleservice.getRolesByUserId(resultSet.getInt("id"));
        user.SetRoles(roles);
        return user;
    }

}
