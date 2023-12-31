package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.Suggestion;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.SuggestionService;

@Service
public class SuggestionServicesImp implements SuggestionService{

	@Override
	public List<Suggestion> getSuggestion() {
	    String selectSQL = "SELECT * FROM suggestion";
	    List<Suggestion> suggestions = new ArrayList<>();

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(selectSQL);
	         ResultSet resultSet = stmt.executeQuery()) {

	        while (resultSet.next()) {
	            Suggestion suggestion = mapResultSetToSuggestion(resultSet);
	            suggestions.add(suggestion);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return suggestions;
	}

	@Override
	public Suggestion getSuggestionById(int id) {
	    String selectSQL = "SELECT * FROM suggestions WHERE id = ?";
	    Suggestion suggestion = null;

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

	        stmt.setInt(1, id);

	        try (ResultSet resultSet = stmt.executeQuery()) {
	            if (resultSet.next()) {
	                suggestion = mapResultSetToSuggestion(resultSet);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return suggestion;
	}

	@Override
	public int createSuggestion(Suggestion suggestion) {
	    String insertSQL = "INSERT INTO suggestion (author_id, description, urgency, importance) VALUES (?, ?,?, ?) RETURNING id";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	        stmt.setInt(1, suggestion.getAuthor_id());
	        stmt.setString(2, suggestion.getDescription());
	        stmt.setString(3, suggestion.getUrgency());
	        stmt.setString(4, suggestion.getImportance());

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
	public int updateSuggestion(int id, Suggestion updatedSuggestion) {
	    String updateSQL = "UPDATE suggestion SET author_id=?, description=?,urgency=?, importance=? WHERE id=?";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

	        stmt.setInt(1, updatedSuggestion.getAuthor_id());
	        stmt.setString(2, updatedSuggestion.getDescription());
	        stmt.setString(3, updatedSuggestion.getUrgency());
	        stmt.setString(4, updatedSuggestion.getImportance());
	        stmt.setInt(5, id);

	        int rowsAffected = stmt.executeUpdate();

	        return rowsAffected > 0 ? id : -1;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; // Retorna un valor negativo para indicar un error
	    }
	}

	@Override
	public int deleteSuggestion(int id) {
	    String deleteSQL = "DELETE FROM suggestion WHERE id=?";

	    try (Connection conn = ConnectionImp.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

	        stmt.setInt(1, id);

	        int rowsAffected = stmt.executeUpdate();

	        return rowsAffected > 0 ? id : -1;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; // Retorna un valor negativo para indicar un error
	    }
	}

	
	
	 private static Suggestion mapResultSetToSuggestion(ResultSet resultSet) throws SQLException {
	        Suggestion suggestion = new Suggestion();
	        suggestion.setId(resultSet.getInt("id"));
	        suggestion.setAuthor_id(resultSet.getInt("author_id"));
	        suggestion.setDescription(resultSet.getString("description"));
	        suggestion.setCreated_on(resultSet.getString("created_on"));
	        suggestion.setUrgency(resultSet.getString("urgency"));
	        suggestion.setImportance(resultSet.getString("importance"));
	        return suggestion;
	    }
	
}
