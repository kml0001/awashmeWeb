package cu.edu.cujae.pweb.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.ProjectFilterDto;
import cu.edu.cujae.pweb.dto.ProjectReportDto;
import cu.edu.cujae.pweb.service.ProjectService;

@Component
@ManagedBean
@ViewScoped
public class ProjectReportBean {
	private List<ProjectReportDto> projectReports;
	
	private ProjectReportDto selectedProjectReport;
	
	private List<ProjectReportDto> selectedProjectReports;
	
	private List<String> filterNames;
	
	private List<String> selectedFilterNames;
	
	private String selectedFilterName;
	
	private ProjectFilterDto filter;
	
	@Autowired
    private ProjectService projectService;
	
	
    public List<ProjectReportDto> getProjects() {
		return projectReports;
	}

	public void setProjects(List<ProjectReportDto> projects) {
		this.projectReports = projects;
	}

	public ProjectReportDto getSelectedProject() {
		return selectedProjectReport;
	}

	public void setSelectedProject(ProjectReportDto selectedProject) {
		this.selectedProjectReport = selectedProject;
	}

	public List<ProjectReportDto> getProjectReports() {
		return projectReports;
	}

	public void setProjectReports(List<ProjectReportDto> projectReports) {
		this.projectReports = projectReports;
	}

	public ProjectReportDto getSelectedProjectReport() {
		return selectedProjectReport;
	}

	public void setSelectedProjectReport(ProjectReportDto selectedProjectReport) {
		this.selectedProjectReport = selectedProjectReport;
	}

	public List<ProjectReportDto> getSelectedProjectReports() {
		return selectedProjectReports;
	}

	public void setSelectedProjectReports(List<ProjectReportDto> selectedProjectReports) {
		this.selectedProjectReports = selectedProjectReports;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public List<ProjectReportDto> getSelectedProjects() {
		return selectedProjectReports;
	}

	public void setSelectedProjects(List<ProjectReportDto> selectedProjects) {
		this.selectedProjectReports = selectedProjects;
	}

	@PostConstruct
    public void init() {
    	filter = new ProjectFilterDto();
    	projectReports = projectReports == null? projectService.getProjectReports(filter): projectReports;

//    	this.filterNames.add("Start date");
//    	this.filterNames.add("Number of members");
//    	this.filterNames.add("Number of issues");
//    	this.filterNames.add("Number of delayed tasks");
//    	this.filterNames.add("Done ratio");
    	System.out.println("ssssssssss" + projectReports);
    }

	public List<String> getFilterNames() {
		return filterNames;
	}

	public void setFilterNames(List<String> filterNames) {
		this.filterNames = filterNames;
	}

	public List<String> getSelectedFilterNames() {
		return selectedFilterNames;
	}

	public void setSelectedFilterNames(List<String> selectedFilterNames) {
		this.selectedFilterNames = selectedFilterNames;
	}

	public String getSelectedFilterName() {
		return selectedFilterName;
	}

	public void setSelectedFilterName(String selectedFilterName) {
		this.selectedFilterName = selectedFilterName;
	}

	public ProjectFilterDto getFilter() {
		return filter;
	}

	public void setFilter(ProjectFilterDto filter) {
		this.filter = filter;
	}
	
	public void applyFilters() {
        this.projectReports = projectService.getProjectReports(filter);
	}
}
