package cu.edu.cujae.pweb.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.ProjectDto;
import cu.edu.cujae.pweb.dto.ProjectReportDto;
import cu.edu.cujae.pweb.service.ProjectService;

@Component
@ManagedBean
@ViewScoped
public class ProjectReportBean {
	private List<ProjectReportDto> projectReports;
	
	private ProjectDto selectedProjectReport;
	
	private List<ProjectReportDto> selectedProjectReports;
	
	@Autowired
    private ProjectService projectService;
	
	
    public List<ProjectReportDto> getProjects() {
		return projectReports;
	}

	public void setProjects(List<ProjectReportDto> projects) {
		this.projectReports = projects;
	}

	public ProjectDto getSelectedProject() {
		return selectedProjectReport;
	}

	public void setSelectedProject(ProjectDto selectedProject) {
		this.selectedProjectReport = selectedProject;
	}

	public List<ProjectReportDto> getSelectedProjects() {
		return selectedProjectReports;
	}

	public void setSelectedProjects(List<ProjectReportDto> selectedProjects) {
		this.selectedProjectReports = selectedProjects;
	}

	@PostConstruct
    public void init() {
    	projectReports = projectReports == null? projectService.getProjectReports(): projectReports;
    	System.out.println(projectReports);
    }
}
