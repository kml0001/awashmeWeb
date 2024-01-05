package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;
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
	
	private List<Date> rangeStartDate;
	private boolean startDateBoolean;
	
	private int minParticipants;
	private int maxParticipants;
	private boolean minParticipantsBoolean;
	private boolean maxParticipantsBoolean;
	
	
	
    private Integer minTasks;
    private Integer maxTasks;
    private boolean minTasksBoolean;
    private boolean maxTasksBoolean;
    
    private Integer minDelayedTasks;
    private Integer maxDelayedTasks;
    private boolean minDelayedTasksBoolean;
    private boolean maxDelayedTasksBoolean;
    
    private Double minCompletionRate;
    private Double maxCompletionRate;
    private boolean minCompletionRateBoolean;
    private boolean maxCompletionRateBoolean;
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
    	projectReports = projectReports == null? projectService.getProjectReports(new ProjectFilterDto()): projectReports;
    	this.rangeStartDate = new ArrayList<>();
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
		ProjectFilterDto pfdto = new ProjectFilterDto();
		System.out.println(this.isStartDateBoolean());
		if (this.isStartDateBoolean()) {
			if(rangeStartDate != null) {
				pfdto.setStartDate(rangeStartDate.get(0));
				pfdto.setEndDate(rangeStartDate.get(rangeStartDate.size() - 1));
			}
		}
		
		
		if (this.isMinParticipantsBoolean()) {
			pfdto.setMinParticipants(this.minParticipants);
		}
		if (this.isMaxParticipantsBoolean()) {
			pfdto.setMaxParticipants(this.maxParticipants);
		}
		
		
		if (this.isMaxDelayedTasksBoolean()) {
			pfdto.setMaxDelayedTasks(this.maxDelayedTasks);
		}
		if(this.isMinDelayedTasksBoolean()) {
			pfdto.setMinDelayedTasks(this.minDelayedTasks);
		}
		
		
		if(this.isMaxTasksBoolean()) {
			pfdto.setMaxTasks(this.maxTasks);
		}
		if(this.isMinTasksBoolean()) {
			pfdto.setMinTasks(minTasks);
		}
		
		
		if(this.isMaxCompletionRateBoolean()) {
			pfdto.setMaxCompletionRate(this.maxCompletionRate);
		}
		if(this.isMinCompletionRateBoolean()) {
			pfdto.setMinCompletionRate(this.minCompletionRate);
		}
		
		System.out.println(pfdto.getMaxParticipants());
		System.out.println(pfdto.getMinParticipants());
		
        this.projectReports = projectService.getProjectReports(pfdto);

        //System.out.println(projectReports.size());
        System.out.println("asd");
        PrimeFaces.current().ajax().update("accordionPanel:projectReport2");
	}

	public int getMinParticipants() {
		return minParticipants;
	}

	public void setMinParticipants(int minParticipants) {
		this.minParticipants = minParticipants;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public List<Date> getRangeStartDate() {
		return rangeStartDate;
	}

	public void setRangeStartDate(List<Date> rangeStartDate) {
		this.rangeStartDate = rangeStartDate;
	}

	public boolean isStartDateBoolean() {
		return startDateBoolean;
	}

	public void setStartDateBoolean(boolean startDateBoolean) {
		this.startDateBoolean = startDateBoolean;
	}

	public boolean isMinParticipantsBoolean() {
		return minParticipantsBoolean;
	}

	public void setMinParticipantsBoolean(boolean minParticipantsBoolean) {
		this.minParticipantsBoolean = minParticipantsBoolean;
	}

	public boolean isMaxParticipantsBoolean() {
		return maxParticipantsBoolean;
	}

	public void setMaxParticipantsBoolean(boolean maxParticipantsBoolean) {
		this.maxParticipantsBoolean = maxParticipantsBoolean;
	}

	public Integer getMaxTasks() {
		return maxTasks;
	}

	public void setMaxTasks(Integer maxTasks) {
		this.maxTasks = maxTasks;
	}

	public Integer getMinTasks() {
		return minTasks;
	}

	public void setMinTasks(Integer minTasks) {
		this.minTasks = minTasks;
	}

	public Integer getMinDelayedTasks() {
		return minDelayedTasks;
	}

	public void setMinDelayedTasks(Integer minDelayedTasks) {
		this.minDelayedTasks = minDelayedTasks;
	}

	public Integer getMaxDelayedTasks() {
		return maxDelayedTasks;
	}

	public void setMaxDelayedTasks(Integer maxDelayedTasks) {
		this.maxDelayedTasks = maxDelayedTasks;
	}

	public Double getMinCompletionRate() {
		return minCompletionRate;
	}

	public void setMinCompletionRate(Double minCompletionRate) {
		this.minCompletionRate = minCompletionRate;
	}

	public Double getMaxCompletionRate() {
		return maxCompletionRate;
	}

	public void setMaxCompletionRate(Double maxCompletionRate) {
		this.maxCompletionRate = maxCompletionRate;
	}

	public boolean isMaxTasksBoolean() {
		return maxTasksBoolean;
	}

	public void setMaxTasksBoolean(boolean maxTasksBoolean) {
		this.maxTasksBoolean = maxTasksBoolean;
	}

	public boolean isMinTasksBoolean() {
		return minTasksBoolean;
	}

	public void setMinTasksBoolean(boolean minTasksBoolean) {
		this.minTasksBoolean = minTasksBoolean;
	}

	public boolean isMinDelayedTasksBoolean() {
		return minDelayedTasksBoolean;
	}

	public void setMinDelayedTasksBoolean(boolean minDelayedTasksBoolean) {
		this.minDelayedTasksBoolean = minDelayedTasksBoolean;
	}

	public boolean isMaxDelayedTasksBoolean() {
		return maxDelayedTasksBoolean;
	}

	public void setMaxDelayedTasksBoolean(boolean maxDelayedTasksBoolean) {
		this.maxDelayedTasksBoolean = maxDelayedTasksBoolean;
	}

	public boolean isMinCompletionRateBoolean() {
		return minCompletionRateBoolean;
	}

	public void setMinCompletionRateBoolean(boolean minCompletionRateBoolean) {
		this.minCompletionRateBoolean = minCompletionRateBoolean;
	}

	public boolean isMaxCompletionRateBoolean() {
		return maxCompletionRateBoolean;
	}

	public void setMaxCompletionRateBoolean(boolean maxCompletionRateBoolean) {
		this.maxCompletionRateBoolean = maxCompletionRateBoolean;
	}
}
