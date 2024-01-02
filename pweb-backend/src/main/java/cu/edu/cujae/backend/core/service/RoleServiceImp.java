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
				      "SELECT * FROM role join user_role on user_role.rol_id = role.id where user_role.user_id = ?");
			pstmt.setInt(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				RoleList.add(new RoleDto(rs.getInt("id")
						,rs.getString("name")
						));
			}
		}
		 catch (SQLException e) {
	            e.printStackTrace();
	        }
		return RoleList;
	}

	@Override
	public List<RoleDto> listRoles() throws SQLException {
		  String selectAllSQL = "SELECT * FROM role";
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

	@Override
	public RoleDto getRoleById(int roleId) throws SQLException {
		 String insertSQL = "SELECT * FROM role WHERE id = ?";
		 RoleDto rol = null;
	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	            stmt.setInt(1, roleId);
	            ResultSet resultSet =stmt.executeQuery();
	            if(resultSet.next()) {
	            	rol = mapResultSetToRole(resultSet);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return rol;
	}

	
	
	
	
	
	public int createRole(RoleDto role) {
        String insertSQL = "INSERT INTO role (name) VALUES (?) RETURNING id";

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
	
	
	
	private RoleDto mapResultSetToRole(ResultSet resultSet) throws SQLException {
	        RoleDto role = new RoleDto();
	        role.setId(resultSet.getInt(1));
	        role.setRoleName(resultSet.getString(2));
	        return role;
	}

	@Override
	public int insertUserRoles(int userId, RoleDto role) throws SQLException {
	        String insertRoleSQL = "INSERT INTO user_role (user_id, rol_id) VALUES (?, ?)";
	        int r = -1;
	        System.out.println(role.getId() + "<--------------------------");
	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(insertRoleSQL)) {
	        	System.out.print(role.getId());
	            if (role !=null) {
	                stmt.setInt(1, userId);
	                stmt.setInt(2, role.getId());
	                int rowsAffected = stmt.executeUpdate();
	                if(rowsAffected > 0)
	                    r  = 1;
	                }
	        	}
	            catch (Exception e) {
					e.printStackTrace();
				}
	
			return r;
	        
	}

	@Override
	public void deleteRolesForUser(int userId) throws SQLException {
		 String deleteRolesSQL = "DELETE FROM user_role WHERE user_id=?";

	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(deleteRolesSQL)) {

	            stmt.setInt(1, userId);
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public void updateRolesForUser(int userId, RoleDto updatedRoleList) throws SQLException {
		this.deleteRolesForUser(userId);
		this.insertUserRoles(userId, updatedRoleList);
		
	}
	
}
