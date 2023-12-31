package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.IssueDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.IssuesService;
@Service
public class IssuesServiceImp implements IssuesService{

	@Override
	public List<IssueDto> getIssues() {
	    String consultaSQL = "SELECT id, subject, description, is_private, done_ratio, closed_on, due_date, start_date, update_on, created_on, estimated_hours, project_id, author_id, assigned_to_id FROM issues";
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
	                resultado.getString("closed_on"),
	                resultado.getString("due_date"),
	                resultado.getString("start_date"),
	                resultado.getString("update_on"),
	                resultado.getString("created_on"),
	                resultado.getDouble("estimated_hours"),
	                resultado.getInt("project_id"),
	                resultado.getInt("author_id"),
	                resultado.getInt("assigned_to_id")
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

	    String consultaSQL = "SELECT id, subject, description, is_private, done_ratio, closed_on, due_date, start_date, update_on, created_on, estimated_hours, project_id, author_id, assigned_to_id FROM issues WHERE id = ?";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(consultaSQL)) {

	        stmt.setLong(1, id);

	        try (ResultSet resultado = stmt.executeQuery()) {
	            if (resultado.next()) {
	                issue = new IssueDto();
	                issue.setId(resultado.getInt("id"));
	                issue.setSubject(resultado.getString("subject"));
	                issue.setDescription(resultado.getString("description"));
	                issue.setIs_private(resultado.getBoolean("is_private"));
	                issue.setDone_ratio(resultado.getDouble("done_ratio"));
	                issue.setClosed_on(resultado.getString("closed_on"));
	                issue.setDue_date(resultado.getString("due_date"));
	                issue.setStart_date(resultado.getString("start_date"));
	                issue.setUpdate_on(resultado.getString("update_on"));
	                issue.setCreated_om(resultado.getString("created_on"));
	                issue.setEstimated_hours(resultado.getDouble("estimated_hours"));
	                issue.setProject_id(resultado.getInt("project_id"));
	                issue.setAuthor_id(resultado.getInt("author_id"));
	                issue.setAsigned_to_id(resultado.getInt("assigned_to_id"));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return issue;
	}


	@Override
	public int createIssue(IssueDto issue) {
	    String insertSQL = "INSERT INTO issues (subject, description, is_private, done_ratio, closed_on, due_date, start_date, update_on, estimated_hours, project_id, author_id, assigned_to_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	        stmt.setString(1, issue.getSubject());
	        stmt.setString(2, issue.getDescription());
	        stmt.setBoolean(3, issue.Is_private());
	        stmt.setDouble(4, issue.getDone_ratio());
	        stmt.setString(5, issue.getClosed_on());
	        stmt.setString(6, issue.getDue_date());
	        stmt.setString(7, issue.getStart_date());
	        stmt.setString(8, issue.getUpdate_on());
	        stmt.setDouble(9, issue.getEstimated_hours());
	        stmt.setInt(10, issue.getProject_id());
	        stmt.setInt(11, issue.getAuthor_id());
	        stmt.setInt(12, issue.getAsigned_to_id());

	        int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Devuelve el ID generado
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
	public int updateIssue(int id ,IssueDto issue) {
        String updateSQL = "UPDATE issues SET subject=?, description=?, is_private=?, done_ratio=?, closed_on=?, due_date=?, start_date=?, estimated_hours=?, project_id=?, assigned_to_id=? WHERE id=?";

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, issue.getSubject());
            stmt.setString(2, issue.getDescription());
            stmt.setBoolean(3, issue.Is_private());
            stmt.setDouble(4, issue.getDone_ratio());
            stmt.setString(5, issue.getClosed_on());
            stmt.setString(6, issue.getDue_date());
            stmt.setString(7, issue.getStart_date());
            stmt.setDouble(8, issue.getEstimated_hours());
            stmt.setInt(9, issue.getProject_id());
            stmt.setInt(10, issue.getAsigned_to_id());
            stmt.setInt(11, id); // Identificador único para la actualización

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Devuelve el ID generado
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
	public int deleteIssue(int id) {
		 	
	        String consultaSQL = "DELETE FROM issues WHERE id=?";
	        int id_return = -1;
	        try {
	            Connection conn = ConnectionImp.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(consultaSQL);
	            stmt.setInt(1, id);

	            int filasEliminadas = stmt.executeUpdate();

	            stmt.close();
	            conn.close();

	            if (filasEliminadas > 0) {
	                System.out.println("Issue eliminado correctamente.");
	                id_return = id;
	            } else {
	                System.out.println("No se encontró ningún issue con el ID proporcionado para eliminar.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return id_return;
	}

}
