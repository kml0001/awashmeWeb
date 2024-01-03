package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.IssueDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.core.util.date_string_converter;
import cu.edu.cujae.backend.service.IssuesService;

@Service
public class IssuesServiceImp implements IssuesService{
	

	@Override
	public List<IssueDto> getIssues() {
	    String consultaSQL = "SELECT issue.*, project.name AS project_name, users.username AS assigned_to_name\r\n"
	    		+ "FROM issue\r\n"
	    		+ "JOIN project ON issue.project_id = project.id\r\n"
	    		+ "JOIN users ON issue.assigned_to_id = users.id;";
	  
	    	
	    List<IssueDto> listaIssues = new ArrayList<>();

	    try {
	        Connection conn = ConnectionImp.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(consultaSQL);

	        ResultSet resultado = stmt.executeQuery();

	        while (resultado.next()) {
	            IssueDto issue = new IssueDto(
	                resultado.getInt("id"),
	                resultado.getString("subject"),
	                resultado.getString("description"),
	                resultado.getBoolean("is_private"),
	                resultado.getDouble("done_ratio"),
	                resultado.getDate("closed_on"),
	                resultado.getDate("due_date"),
	                resultado.getDate("start_date"),
	                resultado.getDate("updated_on"),
	                resultado.getDate("created_on"),
	                resultado.getDouble("estimated_hours"),
	                resultado.getInt("project_id"),
	                resultado.getInt("author_id"),
	                resultado.getInt("assigned_to_id"),
	                resultado.getString("type"),
	                resultado.getDouble("hours_reported"),
	                resultado.getString("assigned_to_name"),
	                resultado.getString("project_name"),
	                author_id_name(resultado.getInt("assigned_to_id"))
	            );
	            listaIssues.add(issue);
	        }

	        resultado.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return listaIssues;
	}

	@Override
	public IssueDto getIssueById(int id) {
	    IssueDto issue = null;

	    String consultaSQL = "SELECT id, subject, done_ratio, closed_on, due_date, updated_on, created_on, project_id, author_id, assigned_to_id  ,hours_reported   FROM issue WHERE id = ?";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(consultaSQL)) {

	        stmt.setLong(1, id);

	        try (ResultSet resultado = stmt.executeQuery()) {
	            if (resultado.next()) {
	                issue = new IssueDto();
	                issue.setId(resultado.getInt("id"));
	                issue.setSubject(resultado.getString("subject"));
	                issue.setDone_ratio(resultado.getDouble("done_ratio"));
	                issue.setClosed_on(resultado.getDate("closed_on"));
	                issue.setDue_date(resultado.getDate("due_date"));
	                issue.setUpdate_on(resultado.getDate("updated_on"));
	                issue.setCreated_on(resultado.getDate("created_on"));
	                issue.setProject_id(resultado.getInt("project_id"));
	                issue.setAuthor_id(resultado.getInt("author_id"));
	                issue.setAssigned_to_id(resultado.getInt("assigned_to_id"));
	                issue.setHours_reported(resultado.getDouble("hours_reported"));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return issue;
	}

	@Override
	public int createIssue(IssueDto issue)  {
        String insertSQL = "INSERT INTO issue (subject, done_ratio, due_date, project_id, author_id, assigned_to_id ,hours_reported , closed_on) VALUES (?,?, ?, ?, ?, ?, ?,?)";
        int id = -1;
        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

        
            stmt.setString(1, issue.getSubject());
            stmt.setDouble(2, issue.getDone_ratio());
            stmt.setDate(3, new Date(issue.getDue_date().getTime()));

            stmt.setInt(4, issue.getProject_id());
            stmt.setInt(5, issue.getAuthor_id());
            stmt.setInt(6, issue.getAssigned_to_id());
           
            stmt.setDouble(7, issue.getHours_reported());
            
            if(issue.getClosed_on() != null)
            	stmt.setDate(8, new Date(issue.getClosed_on().getTime()));
            else {
            	stmt.setDate(8, null);
            }
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
               id = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print(id);
        return id;
}

	@Override
	public int updateIssue(IssueDto issue) {
        String updateSQL = "UPDATE issue SET subject=?, , done_ratio=?, due_date=? ,project_id=?, hours_reported=?  ,assigned_to_id=?  , closed_on=? WHERE id=?";

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, issue.getSubject());
            stmt.setDouble(2, issue.getDone_ratio());
            stmt.setDate(3, new Date(issue.getDue_date().getTime()));
            stmt.setInt(4, issue.getProject_id());
            stmt.setInt(5, issue.getAssigned_to_id());
            stmt.setDouble(6, issue.getHours_reported());
            if(issue.getClosed_on() != null)
            	stmt.setDate(7, new Date(issue.getClosed_on().getTime()));
            else {
            	stmt.setDate(7, null);
            }
            stmt.setInt(8, issue.getId()); 
            
            int rowsAffected = stmt.executeUpdate();
       
            if (rowsAffected > 0) {
            		return rowsAffected;
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna un valor negativo para indicar un error
        }
        return 1;
}

	@Override
	public int deleteIssue(int id) {
		 	
	        String consultaSQL = "DELETE FROM issue WHERE id=?";
	        int id_return = -1;
	        try {
	            Connection conn = ConnectionImp.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(consultaSQL);
	            stmt.setInt(1, id);

	            int filasEliminadas = stmt.executeUpdate();

	            stmt.close();
	            conn.close();

	            if (filasEliminadas > 0) {
	                
	                id_return = id;
	            } else {
	                
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return id_return;
	}
	
	
	private String author_id_name(int userId) {
		String nombreUsuario = null;

        try (Connection connection = ConnectionImp.getConnection()) {
            
            String sql = "SELECT username FROM users WHERE id = ?";
            
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
               
                statement.setInt(1, userId);

               
                try (ResultSet resultSet = statement.executeQuery()) {
                  
                    if (resultSet.next()) {
                      
                        nombreUsuario = resultSet.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreUsuario;
}
	
}
