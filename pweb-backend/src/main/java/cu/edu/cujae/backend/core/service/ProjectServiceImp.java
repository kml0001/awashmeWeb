package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
	            String sql = "SELECT * FROM projects";
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
	            String sql = "SELECT * FROM projects WHERE id=?";
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
		  
		   int id_project_return = -1;
		   System.out.println("Prueba");
		   System.out.println(project.get_public());
		    	try (Connection conn = ConnectionImp.getConnection()) {
		            String sql = "INSERT INTO projects (name, identifier, description, status, is_public, inherit_members, created_on, updated_on, project_type) " +
		                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)  RETURNING id";
		            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		            	stmt.setString(1, project.getName());
		            	stmt.setString(2, project.getIdentifier());
		            	stmt.setString(3, project.getDescription());
		            	stmt.setString(4, project.getStatus());
		            	stmt.setString(5, project.get_public());
		            	stmt.setString(6, project.getInherit_members());
		            	stmt.setString(7,project.getCreated_on());
		            	stmt.setString(8,project.getUpdated_on());
		            	stmt.setString(9, project.getProject_type());

		                ResultSet resultado = stmt.executeQuery();
			            
			            if (resultado.next()) {
			            	id_project_return = resultado.getInt("id"); // Obtener el ID generado
			            }
			            resultado.close();
		                stmt.close();
		                conn.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		return id_project_return;
	}

	@Override
	public int updateProject(int id, ProjectDto updatedProject) {
		int id_project_return = -1;
		
		try (Connection conn = ConnectionImp.getConnection()) {
            String sql = "UPDATE projects SET name=?, identifier=?, description=?, status=?, is_public=?, inherit_members=?, created_on=?, updated_on=?, project_type=? WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            	stmt.setString(1, updatedProject.getName());
            	stmt.setString(2, updatedProject.getIdentifier());
            	stmt.setString(3, updatedProject.getDescription());
            	stmt.setString(4, updatedProject.getStatus());
            	stmt.setString(5, updatedProject.get_public());
                stmt.setString(6, updatedProject.getInherit_members());
                stmt.setString(7,updatedProject.getCreated_on());
                stmt.setString(8,new Date().toString());
                stmt.setString(9, updatedProject.getProject_type());
                stmt.setInt(10, updatedProject.getId());

                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Proyecto actualizado correctamente.");
                    id_project_return =updatedProject.getId();
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return id_project_return;
	}

	@Override
	public int deleteProject(int id) {
		int id_return = -1;
		try (Connection conn = ConnectionImp.getConnection()) {
            String sql = "DELETE FROM projects WHERE id=?";
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
		   ProjectDto projectDto = new ProjectDto(
      			 resultSet.getString("name"),
      			 resultSet.getString("identifier"),
      			 resultSet.getString("description"),
      			 resultSet.getString("status"),
      			 resultSet.getString("is_public"),
      			 resultSet.getString("inherit_members"),
      			 resultSet.getString("project_type"));
		    projectDto.setId(resultSet.getInt("id"));
		    projectDto.setCreated_on(resultSet.getString("created_on"));
		    projectDto.setUpdated_on(resultSet.getString("updated_on"));
	        return projectDto;
	    }
}
