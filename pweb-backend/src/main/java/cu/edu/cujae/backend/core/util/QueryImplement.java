package cu.edu.cujae.backend.core.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cu.edu.cujae.backend.core.dto.FilterDto;
import cu.edu.cujae.backend.core.query.dto.ProjectReportDto;

public class QueryImplement   {

	public static List<ProjectReportDto> getProjectReports(FilterDto filter) {
        List<ProjectReportDto> projectReports = new ArrayList<>();

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
                try (ResultSet resultSet = stmt.executeQuery()) {
                    while (resultSet.next()) {
                        ProjectReportDto report = new ProjectReportDto();
                        report.setName(resultSet.getString("name"));
                        report.setMembers(resultSet.getInt("members"));
                        report.setUsername(resultSet.getString("username"));
                        report.setCreatedOn(resultSet.getDate("created_on"));
                        report.setClosedOn(resultSet.getDate("closed_on"));
                        report.setTotalHoursReported(resultSet.getInt(6)); // Reemplaza con el índice correcto según tu consulta
                        report.setAverageDoneRatio(resultSet.getDouble(7)); // Reemplaza con el índice correcto según tu consulta
                        report.setTotalIssues(resultSet.getInt(8)); // Reemplaza con el índice correcto según tu consulta
                        report.setDelayedIssues(resultSet.getInt("delayed_issues"));
                        
                        
                        if(Filters(filter,report)) {
                        	projectReports.add(report);
                        }
                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectReports;
    }
	
	private static boolean Filters(FilterDto filter ,ProjectReportDto report) {
	
	        // Verificar si la fecha de creación está dentro del rango
	        if (filter.getStartDate() != null && report.getCreatedOn().before(filter.getStartDate())) {
	            return false;
	        }
	        if (filter.getEndDate() != null && report.getCreatedOn().after(filter.getEndDate())) {
	            return false;
	        }
	        // Verificar el número de participantes
	        if (filter.getMinParticipants() != null && report.getMembers() < filter.getMinParticipants()) {
	            return false;
	        }
	        if (filter.getMaxParticipants() != null && report.getMembers() > filter.getMaxParticipants()) {
	            return false;
	        }
	        // Verificar el número total de tareas
	        if (filter.getMinTasks() != null && report.getTotalHoursReported() < filter.getMinTasks()) {
	            return false;
	        }
	        if (filter.getMaxTasks() != null && report.getTotalHoursReported() > filter.getMaxTasks()) {
	            return false;
	        }
	        // Verificar el número de tareas demoradas
	        if (filter.getMinDelayedTasks() != null && report.getDelayedIssues() < filter.getMinDelayedTasks()) {
	            return false;
	        }
	        if (filter.getMaxDelayedTasks() != null && report.getDelayedIssues() > filter.getMaxDelayedTasks()) {
	            return false;
	        }
	        // Verificar la tasa de finalización
	        if (filter.getMinCompletionRate() != null && report.getAverageDoneRatio() < filter.getMinCompletionRate()) {
	            return false;
	        }
	        if (filter.getMaxCompletionRate() != null && report.getAverageDoneRatio() > filter.getMaxCompletionRate()) {
	            return false;
	        }
	        // Si ha pasado todas las verificaciones, el informe cumple con los requisitos
	        return true;
	    }
}