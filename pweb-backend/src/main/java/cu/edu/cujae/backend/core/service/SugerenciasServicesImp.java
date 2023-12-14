package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.SugerenciaDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.SugerenciasService;

@Service
public class SugerenciasServicesImp implements SugerenciasService{

	@Override
	public List<SugerenciaDto> getSugerencias() {
		 List<SugerenciaDto> suggestions = new ArrayList<>();

	        try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "SELECT * FROM sugerencias";
	            try (PreparedStatement statement = conn.prepareStatement(sql);
	                 ResultSet resultSet = statement.executeQuery()) {

	                while (resultSet.next()) {
	                    suggestions.add(mapResultSetToSuggestion(resultSet));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return suggestions;
	    }


	@Override
	public SugerenciaDto getSugerenciaById(int id) {
		 SugerenciaDto suggestion = null;

	        try (Connection conn =ConnectionImp.getConnection()) {
	            String sql = "SELECT * FROM sugerencias WHERE id=?";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	                statement.setInt(1, id);
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        suggestion = mapResultSetToSuggestion(resultSet);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return suggestion;
	}

	@Override
	public int createSugerencia(SugerenciaDto sugerencia) {
		int id_sugerencia_return = -1;
		 try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "INSERT INTO sugerencias (datos, fecha_creacion, texto, importancia, urgencia) " +
	                         "VALUES (?, ?, ?, ?, ?) RETURNING id";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            	stmt.setString(1, sugerencia.getDatos());
	            	stmt.setString(2, sugerencia.getFecha_creacion());
	            	stmt.setString(3, sugerencia.getTexto());
	            	stmt.setString(4, sugerencia.getImportancia());
	            	stmt.setString(5, sugerencia.getUrgencia());

	                ResultSet resultado = stmt.executeQuery();
	                if (resultado.next()) {
	                	id_sugerencia_return = resultado.getInt("id");
		            }
		            resultado.close();
	                stmt.close();
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 return id_sugerencia_return;
	}

	@Override
	public int updateSugerencia(int id, SugerenciaDto updatedSugerencia) {
		int id_sugerencia_return = -1;
		try (Connection conn = ConnectionImp.getConnection()) {
            String sql = "UPDATE sugerencias SET datos=?, fecha_creacion=?, texto=?, importancia=?, urgencia=? WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, updatedSugerencia.getDatos());
                statement.setString(2, updatedSugerencia.getFecha_creacion());
                statement.setString(3, updatedSugerencia.getTexto());
                statement.setString(4, updatedSugerencia.getImportancia());
                statement.setString(5, updatedSugerencia.getUrgencia());
                statement.setInt(6, updatedSugerencia.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Sugerencia actualizada correctamente.");
                    id_sugerencia_return = updatedSugerencia.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return id_sugerencia_return;
	}

	@Override
	public int deleteSugerencia(int id) {
		int id_return = -1;
		 try (Connection conn = ConnectionImp.getConnection()) {
	            String sql = "DELETE FROM sugerencias WHERE id=?";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	                statement.setInt(1, id);

	                int rowsAffected = statement.executeUpdate();
	                if (rowsAffected > 0) {
	                    System.out.println("Sugerencia eliminada correctamente.");
	                    id_return = id;
	                } else {
	                    System.out.println("No se encontró ninguna sugerencia con el ID proporcionado para eliminar.");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 return id_return;
	}
	
	
	
	private SugerenciaDto mapResultSetToSuggestion(ResultSet resultSet) throws SQLException {
        SugerenciaDto suggestion = new SugerenciaDto(
        		resultSet.getString("datos"),
        		resultSet.getString("texto"),
        		resultSet.getString("importancia"),
        		resultSet.getString("urgencia"));
        suggestion.setId(resultSet.getInt("id"));
        suggestion.setFecha_creacion(resultSet.getString("fecha_creacion"));
        return suggestion;
	}
}
