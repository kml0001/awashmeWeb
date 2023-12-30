package cu.edu.cujae.backend.core.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.RoleService;

@Service
public class RoleServiceImp implements RoleService{
	
		
	@Override
	public List<RoleDto> getRolesByUserId(int userId) throws SQLException {
		List<RoleDto> RoleList = new ArrayList<>();
		 try (Connection conn = ConnectionImp.getConnection()) {
			 PreparedStatement pstmt = conn.prepareStatement(
				      "SELECT id, role_name, description FROM xrole inner join user_role on user_role.role_id = xrole.id where user_role.user_id = ?");
			pstmt.setInt(1, userId);
	
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				RoleList.add(new RoleDto(rs.getInt("id")
						,rs.getString("role_name")
						));
			}
		}
		return RoleList;
	}

	@Override
	public List<RoleDto> listRoles() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleDto getRoleById(int roleId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	public int createRole(RoleDto role) {
        String insertSQL = "INSERT INTO roles (roleName) VALUES (?) RETURNING id";

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setString(1, role.getRoleName());

            try (ResultSet generatedKeys = stmt.executeQuery()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt("id");
                } else {
                    throw new SQLException("No se pudo obtener el ID generado.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna un valor negativo para indicar un error
        }
    }
	
	
	
	public List<RoleDto> getAllRoles() {
        String selectAllSQL = "SELECT * FROM roles";
        List<RoleDto> roles = new ArrayList<>();

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectAllSQL);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                RoleDto role = mapResultSetToRole(resultSet);
                roles.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }
	
	
	private RoleDto mapResultSetToRole(ResultSet resultSet) throws SQLException {
	        RoleDto role = new RoleDto();
	        role.setId(resultSet.getInt("id"));
	        role.setRoleName(resultSet.getString("roleName"));
	        return role;
	}

	@Override
	public int insertUserRoles(int userId, List<RoleDto> roleList) throws SQLException {
	        String insertRoleSQL = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";

	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(insertRoleSQL)) {

	            for (RoleDto role : roleList) {
	                stmt.setInt(1, userId);
	                stmt.setInt(2, role.getId());
	                stmt.executeUpdate();
	            }
	            try (ResultSet generatedKeys = stmt.executeQuery()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt("id");
	                } else {
	                    throw new SQLException("No se pudo obtener el ID generado.");
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Retorna un valor negativo para indicar un error
	        }
	}

	@Override
	public void deleteRolesForUser(int userId) throws SQLException {
		 String deleteRolesSQL = "DELETE FROM user_roles WHERE user_id=?";

	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(deleteRolesSQL)) {

	            stmt.setInt(1, userId);
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public void updateRolesForUser(int userId, List<RoleDto> updatedRoleList) throws SQLException {
		this.deleteRolesForUser(userId);
		this.insertUserRoles(userId, updatedRoleList);
		
	}
	
}
