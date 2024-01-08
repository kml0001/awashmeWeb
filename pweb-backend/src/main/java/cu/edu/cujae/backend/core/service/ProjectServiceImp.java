package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.MembersDto;
import cu.edu.cujae.backend.core.dto.ProjectDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.core.util.date_string_converter;
import cu.edu.cujae.backend.service.MembersService;
import cu.edu.cujae.backend.service.ProjectsService;
import cu.edu.cujae.backend.service.RoleService;



@Service
public class ProjectServiceImp implements ProjectsService {

	@Autowired
	private MembersService members;
	
	@Override
	public List<ProjectDto> getProjects() {
		 
		 List<ProjectDto> projects = new ArrayList<>();

	        try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "SELECT project.*, users.username AS project_manager_name FROM project join users on users.id = project_manager";
	            try (PreparedStatement statement = conn.prepareStatement(sql);
	                 ResultSet resultSet = statement.executeQuery()) {

	                while (resultSet.next()) {
	                    projects.add(mapResultSetToProject(resultSet));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return projects;
	}

	@Override
	public ProjectDto getProjectById(int id) {
	
		ProjectDto projectDto = null;
	        try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "SELECT project.*, users.username AS project_manager_name FROM project join users on users.id = project_manager  WHERE project.id=?";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	                statement.setInt(1, id);

	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        projectDto = mapResultSetToProject(resultSet);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return projectDto;
	}

	@Override
	public int createProject(ProjectDto project) {
	    String insertSQL = "INSERT INTO project (name, description, project_manager) VALUES (?, ?, ?) RETURNING id";
	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	        stmt.setString(1, project.getName());
	        stmt.setString(2, project.getDescription());
	        stmt.setInt(3, project.getProject_manager());
	        ResultSet rowsAffected = stmt.executeQuery();
	        
	        if (rowsAffected.next()) {
	        	int id = rowsAffected.getInt("id");

	        	List<UserDto> users = project.getMembers();
	        	for(UserDto user : users) {
	        		try {
	        			members.insertMembers(new MembersDto(id, user.getId()));
	        		}
	        		catch (Exception e) {
	        			System.out.println("La relacion ya existe <-----------------------");
	        		}
	        	}
	        	return 1;
	        }
	        else {
	        	return 0;
	        }
	    }  catch (SQLException e) {
	        // Manejar excepción específica de PostgreSQL para proyectos duplicados
	        if (e.getMessage().contains("Ya existe un proyecto con el mismo nombre")) {
	            return 0;
	        } else {
	         
	            return -1;
	        }
	    }
	}

	@Override
	public int updateProject(ProjectDto project) {
		String updateSQL = "UPDATE project SET name=?, description=?, status=?, is_public=?, project_manager=? WHERE id=?";

		try (Connection conn = ConnectionImp.getConnection();
			PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

			stmt.setString(1, project.getName());
			stmt.setString(2, project.getDescription());
			stmt.setString(3, project.getStatus());
			stmt.setBoolean(4, project.getIs_public());
			stmt.setInt(5, project.getProject_manager());
			stmt.setInt(6, project.getId());

			int rowsAffected = stmt.executeUpdate();
			
			
			return (rowsAffected > 0) ? 1 : 2;
			
		}  catch (SQLException e) {
			// Manejar excepción específica de PostgreSQL para proyectos duplicados
			if (e.getMessage().contains("Ya existe un proyecto con el mismo nombre")) {
				return 0;
			} else {
				
				return -1;
			}
		}
	}

	@Override
	public int deleteProject(int id) {
	
		try (Connection conn = ConnectionImp.getConnection()) {
            String sql = "DELETE FROM project WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                
                int rowsAffected = statement.executeUpdate();
               
                return (rowsAffected > 0) ? 1 : 2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
		
	}

	private List<UserDto> getMembersByProjectId(int projectId){
	List<UserDto> list = new ArrayList<>();
		String selectByUserSQL = "SELECT users.* FROM project JOIN members on project.id = members.project_id JOIN users on users.id = members.user_id where project_id = ?";
		try (Connection conn = ConnectionImp.getConnection();
				PreparedStatement stmt = conn.prepareStatement(selectByUserSQL)) {
			
			stmt.setInt(1, projectId);
	
			try (ResultSet resultSet = stmt.executeQuery()) {
				while (resultSet.next()) {
					UserDto user = new UserDto();
					user.setId(resultSet.getInt("id"));
					user.setFullname(resultSet.getString("lastname"));
					user.setUsername(resultSet.getString("username"));
					list.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return list;
	}
	
	private ProjectDto mapResultSetToProject(ResultSet resultSet) throws SQLException {
	    int id = resultSet.getInt("id");
	    String created_on = resultSet.getString("created_on");
	    String updated_on = resultSet.getString("updated_on");
	    String name = resultSet.getString("name");
	    String description = resultSet.getString("description");
	    String status = resultSet.getString("status");
	    Boolean is_public = resultSet.getBoolean("is_public");
	    int project_manager = resultSet.getInt("project_manager");
	    String closed_on = resultSet.getString("closed_on");
	    String project_manager_name = resultSet.getString("project_manager_name");
	    List<UserDto> members = this.getMembersByProjectId(id);
	    ProjectDto projectDto = new ProjectDto(id, created_on, updated_on, name, description, status, is_public, project_manager ,closed_on ,project_manager_name,members);
	    return projectDto;
	}
}
