package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
				String consultaSQL = "SELECT * FROM issues";
		        List<IssueDto> listaIssues = new ArrayList<>();
		       
		        try {
		            Connection conn = ConnectionImp.getConnection();
		            PreparedStatement stmt = conn.prepareStatement(consultaSQL);

		            ResultSet resultado = stmt.executeQuery();

		            while (resultado.next()) {
		            	IssueDto issue = new IssueDto(
		            								  resultado.getString("Tipo"),
		            								  resultado.getString("Fondo_de_tiempo"),
		            								  resultado.getDouble("cumplimiento"),
		            								  resultado.getString("persona_asignada"));
		            	issue.setId(String.valueOf(resultado.getInt("id")));
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

	       
	        String consultaSQL = "SELECT * FROM issues WHERE id = ?";

	        try {
	            Connection conn = ConnectionImp.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(consultaSQL);
	            stmt.setLong(1, id);

	            ResultSet resultado = stmt.executeQuery();

	            if (resultado.next()) {
	                issue = new IssueDto();
	                issue.setId(String.valueOf(resultado.getInt("id")));
	                issue.setTipo(resultado.getString("Tipo"));
	                issue.setFondo_de_tiempo(resultado.getString("Fondo_de_tiempo"));
	                issue.setCumplimiento(resultado.getDouble("cumplimiento"));
	                issue.setPersona_asignada(resultado.getString("persona_asignada"));
	            }

	            resultado.close();
	            stmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return issue;

	}

	@Override
	public int createIssue(IssueDto issue) {
		
			int id_issue_return = -1;
	        String consultaSQL = "INSERT INTO issues (Tipo, Fondo_de_tiempo, cumplimiento, persona_asignada) VALUES (?, ?, ?, ?) RETURNING id";

	        try {
	            Connection conn = ConnectionImp.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(consultaSQL);
	            stmt.setString(1, issue.getTipo());
	            stmt.setString(2, issue.getFondo_de_tiempo());
	            stmt.setDouble(3, issue.getCumplimiento());
	            stmt.setString(4, issue.getPersona_asignada());

	            ResultSet resultado = stmt.executeQuery();
	            
	            if (resultado.next()) {
	            	id_issue_return = resultado.getInt("id"); // Obtener el ID generado
	            }
	            resultado.close();
                stmt.close();
                conn.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return id_issue_return;
	}

	@Override
	public int updateIssue(int id, IssueDto updatedIssue) {
		
		int id_issue_return =-1;
        String consultaSQL = "UPDATE issues SET Tipo=?, Fondo_de_tiempo=?, cumplimiento=?, persona_asignada=? WHERE id=?";
        try {
            Connection conn = ConnectionImp.getConnection();
            PreparedStatement stmt = conn.prepareStatement(consultaSQL);
            stmt.setString(1, updatedIssue.getTipo());
            stmt.setString(2, updatedIssue.getFondo_de_tiempo());
            stmt.setDouble(3, updatedIssue.getCumplimiento());
            stmt.setString(4, updatedIssue.getPersona_asignada());
            stmt.setInt(5, Integer.valueOf(updatedIssue.getId()));

            int filasActualizadas = stmt.executeUpdate();

            stmt.close();
            conn.close();

            if (filasActualizadas > 0) {
            	id_issue_return = Integer.valueOf(updatedIssue.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return id_issue_return;
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
