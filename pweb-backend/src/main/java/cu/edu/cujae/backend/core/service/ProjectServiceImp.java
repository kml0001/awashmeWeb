package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.ProjectDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.ProjectsService;

@Service
public class ProjectServiceImp implements ProjectsService {

	
	@Override
	public List<ProjectDto> getProjects() {
		 
		 List<ProjectDto> projects = new ArrayList<>();

	        try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "SELECT * FROM project";
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
	            String sql = "SELECT * FROM project WHERE id=?";
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
	    String insertSQL = "INSERT INTO project (name, description, status, is_public, project_manager) VALUES (?, ?, ?, ?, ?) RETURNING id";
	    
	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	    	
	        stmt.setString(1, project.getName());
	        stmt.setString(2, project.getDescription());
	        stmt.setString(3, project.getStatus());
	        stmt.setBoolean(4, project.getIs_public());
	        stmt.setInt(5, project.getProject_manager());

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
	public int updateProject(int id ,ProjectDto project) {
	    String updateSQL = "UPDATE project SET name=?, description=?, status=?, is_public=?, project_manager=? WHERE id=?";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

	        stmt.setString(1, project.getName());
	        stmt.setString(2, project.getDescription());
	        stmt.setString(3, project.getStatus());
	        stmt.setBoolean(4, project.getIs_public());
	        stmt.setInt(5, project.getProject_manager());
	        stmt.setInt(6, id);

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
	public int deleteProject(int id) {
		int id_return = -1;
		try (Connection conn = ConnectionImp.getConnection()) {
            String sql = "DELETE FROM project WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Proyecto eliminado correctamente.");
                    id_return = id;
                } else {
                    System.out.println("No se encontró ningún proyecto con el ID proporcionado para eliminar.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return id_return;
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

	    ProjectDto projectDto = new ProjectDto(id, created_on, updated_on, name, description, status, is_public, project_manager);
	    return projectDto;
	}
}
