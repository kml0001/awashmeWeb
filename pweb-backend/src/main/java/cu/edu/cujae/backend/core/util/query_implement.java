package cu.edu.cujae.backend.core.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cu.edu.cujae.backend.core.query.dto.ProjectReportData;

public class query_implement   {

	public void report() {
		
		 try (Connection conn = ConnectionImp.getConnection()) {
			 String sql = "SELECT project.name, COUNT(members.project_id) AS members, users.username, " +
		                "project.created_on, project.closed_on, SUM(issue.hours_reported) AS hours, " +
		                "AVG(issue.done_ratio) AS average, COUNT(issue) AS total_issues, " +
		                "COUNT(CASE WHEN issue.due_date IS NOT NULL AND issue.closed_on > issue.due_date THEN 1 END) AS delayed_issues " +
		                "FROM project " +
		                "LEFT JOIN issue ON project.id = issue.project_id " +
		                "LEFT JOIN users ON project.project_manager = users.id " +
		                "LEFT JOIN members ON project.id = members.project_id " +
		                "GROUP BY project.name, users.username, project.created_on, project.closed_on";

	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                // Ejecutar la consulta y mapear el resultado a una lista de objetos
	                List<ProjectReportData> projectReports = new ArrayList<>();
	                try (ResultSet resultSet = stmt.executeQuery()) {
	                    while (resultSet.next()) {
	                        ProjectReportData report = new ProjectReportData();
	                        report.setName(resultSet.getString("name"));
	                        report.setMembers(resultSet.getInt("members"));
	                        report.setUsername(resultSet.getString("username"));
	                        report.setCreatedOn(resultSet.getDate("created_on"));
	                        report.setClosedOn(resultSet.getDate("closed_on"));
	                        report.setTotalHoursReported(resultSet.getInt(6)); // Reemplaza con el índice correcto según tu consulta
	                        report.setAverageDoneRatio(resultSet.getDouble(7)); // Reemplaza con el índice correcto según tu consulta
	                        report.setTotalIssues(resultSet.getInt(8)); // Reemplaza con el índice correcto según tu consulta
	                        report.setDelayedIssues(resultSet.getInt("delayed_issues"));
	                        projectReports.add(report);
	                    }
	                }

	                // Imprimir los resultados
	                for (ProjectReportData report : projectReports) {
	                    System.out.println(report);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
