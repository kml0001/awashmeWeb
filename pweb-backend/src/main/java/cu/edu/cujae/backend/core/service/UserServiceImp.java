package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.UserService;

@Service
public class UserServiceImp implements UserService{

	
	
	@Override
	public List<UserDto> getUsuarios() {
		 List<UserDto> users = new ArrayList<>();

	        try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "SELECT * FROM users";
	            try (PreparedStatement statement = conn.prepareStatement(sql);
	                 ResultSet resultSet = statement.executeQuery()) {

	                while (resultSet.next()) {
	                    users.add(mapResultSetToUser(resultSet));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return users;
	}

	@Override
	public UserDto getUsuarioById(int id) {
		UserDto user = null;
	        try (Connection conn =ConnectionImp.getConnection()) {
	            String sql = "SELECT * FROM users WHERE id=?";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	                statement.setInt(1, id);
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        user = mapResultSetToUser(resultSet);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return user;
	}

	@Override
	public int createUsuario(UserDto user) {
		int id_user_return = -1;
		 try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "INSERT INTO users (datos_generales, cant_project, cant_horas_reportadas, cant_tareas_atrasadas , name) " +
	                         "VALUES (?, ?, ?, ?, ?) RETURNING id";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            	stmt.setString(1, user.getDatos_generales());
	            	stmt.setInt(2, user.getCant_project());
	            	stmt.setDouble(3, user.getCant_horas_reportadas());
	            	stmt.setInt(4, user.getCant_tareas_atrasadas());
	            	stmt.setString(5, user.getName());
	                ResultSet resultado = stmt.executeQuery();
		            
		            if (resultado.next()) {
		            	id_user_return = resultado.getInt("id"); // Obtener el ID generado
		            }
		            resultado.close();
	                stmt.close();
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return id_user_return;
	}

	@Override
	public int updateUsuario(int id, UserDto updatedUsuario) {
		int id_user_return = -1;
		 try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "UPDATE users SET name = ?,datos_generales=?, cant_project=?, cant_horas_reportadas=?, cant_tareas_atrasadas=? WHERE id=?";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            	statement.setString(1, updatedUsuario.getName());
	                statement.setString(2, updatedUsuario.getDatos_generales());
	                statement.setInt(3, updatedUsuario.getCant_project());
	                statement.setDouble(4, updatedUsuario.getCant_horas_reportadas());
	                statement.setInt(5, updatedUsuario.getCant_tareas_atrasadas());
	                statement.setInt(6, updatedUsuario.getId());

	                int rowsAffected = statement.executeUpdate();
	                if (rowsAffected > 0) {
	                    System.out.println("Usuario actualizado correctamente.");
	                    id_user_return = updatedUsuario.getId();
	                } else {
	                    System.out.println("No se encontró ningún usuario con el ID proporcionado para actualizar.");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 return id_user_return;
	}

	
	@Override
	public int deleteUsuario(int id) {
		int id_return = -1;
		try (Connection conn = ConnectionImp.getConnection()) {
            String sql = "DELETE FROM users WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Usuario eliminado correctamente.");
                    id_return = id;
                } else {
                    System.out.println("No se encontró ningún usuario con el ID proporcionado para eliminar.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return id_return;
	}

	

	 private UserDto mapResultSetToUser(ResultSet resultSet) throws SQLException {
		 	UserDto user = new UserDto(
		 			resultSet.getString("name"),
		 			resultSet.getString("datos_generales"),
		 			resultSet.getInt("cant_project"),
		 			resultSet.getDouble("cant_horas_reportadas"),
		 			resultSet.getInt("cant_tareas_atrasadas"));
		 	user.setId(resultSet.getInt("id"));
	        return user;
	    }
}
