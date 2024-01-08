package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.UserFilterDto;
import cu.edu.cujae.pweb.dto.UserReportDto;
import cu.edu.cujae.pweb.service.UserService;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ManagedBean
@ViewScoped
public class UserReportBean {
	private List<UserReportDto> userReports;
	
	private UserReportDto selectedUserReport;
	
	private List<UserReportDto> selectedUserReports;
	
	private List<String> filterNames;
	
	private List<String> selectedFilterNames;
	
	private String selectedFilterName;
	
	private UserFilterDto filter;
	
	private List<Date> rangeStartDate = new ArrayList<>();
	private boolean startDateBoolean;
	
	private int minParticipants;
	private int maxParticipants;
	private boolean minParticipantsBoolean;
	private boolean maxParticipantsBoolean;
	
	private String name;
	private boolean nameBoolean;
	
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
    private UserService userService;
	
	
    public List<UserReportDto> getUsers() {
		return userReports;
	}

	public void setUsers(List<UserReportDto> users) {
		this.userReports = users;
	}

	public UserReportDto getSelectedUser() {
		return selectedUserReport;
	}

	public void setSelectedUser(UserReportDto selectedUser) {
		this.selectedUserReport = selectedUser;
	}

	public List<UserReportDto> getUserReports() {
		if(this.userReports == null){
			this.userReports = this.userService.getUserReports(new UserFilterDto());
		}

		return userReports;
	}

	public void setUserReports(List<UserReportDto> userReports) {
		this.userReports = userReports;
	}

	public UserReportDto getSelectedUserReport() {
		return selectedUserReport;
	}

	public void setSelectedUserReport(UserReportDto selectedUserReport) {
		this.selectedUserReport = selectedUserReport;
	}

	public List<UserReportDto> getSelectedUserReports() {
		return selectedUserReports;
	}

	public void setSelectedUserReports(List<UserReportDto> selectedUserReports) {
		this.selectedUserReports = selectedUserReports;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<UserReportDto> getSelectedUsers() {
		return selectedUserReports;
	}

	public void setSelectedUsers(List<UserReportDto> selectedUsers) {
		this.selectedUserReports = selectedUsers;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNameBoolean() {
		return nameBoolean;
	}

	public void setNameBoolean(boolean nameBoolean) {
		this.nameBoolean = nameBoolean;
	}

	public void setSelectedFilterName(String selectedFilterName) {
		this.selectedFilterName = selectedFilterName;
	}

	public UserFilterDto getFilter() {
		return filter;
	}

	public void setFilter(UserFilterDto filter) {
		this.filter = filter;
	}
	
	public void applyFilters() {
		UserFilterDto pfdto = new UserFilterDto();
		if(this.nameBoolean){
			pfdto.setNameOrLastName(this.name);
		}

		if (this.isMinParticipantsBoolean()) {
			pfdto.setMinProjectCountRange(this.minParticipants);
		}
		if (this.isMaxParticipantsBoolean()) {
			pfdto.setMaxProjectCountRange(this.maxParticipants);
		}
		
		
		if (this.isMaxDelayedTasksBoolean()) {
			pfdto.setMaxDelayedTasksRange(this.maxDelayedTasks);
		}
		if(this.isMinDelayedTasksBoolean()) {
			pfdto.setMinDelayedTasksRange(this.minDelayedTasks);
		}


		if(this.isMaxTasksBoolean()) {
			pfdto.setMaxTotalHoursReportedRange(this.maxTasks);
		}
		if(this.isMinTasksBoolean()) {
			pfdto.setMinTotalHoursReportedRange(minTasks);
		}

		
        this.userReports = userService.getUserReports(pfdto);

        //System.out.println(userReports.size());
        System.out.println("asd");
        PrimeFaces.current().ajax().update("accordionPanel:userReport2");
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
