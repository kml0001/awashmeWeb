package cu.edu.cujae.pweb.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectFilterDto {
	
	private Date startDate;
    private Date endDate;
    private Integer minParticipants;
    private Integer maxParticipants;
    private Integer minTasks;
    private Integer maxTasks;
    private Integer minDelayedTasks;
    private Integer maxDelayedTasks;
    private Double minCompletionRate;
    private Double maxCompletionRate;
    private List<Date> rangeStartDate;
    
	public ProjectFilterDto(Date startDate, Date endDate, Integer minParticipants, Integer maxParticipants, Integer minTasks,
			Integer maxTasks, Integer minDelayedTasks, Integer maxDelayedTasks, Double minCompletionRate,
			Double maxCompletionRate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.minParticipants = minParticipants;
		this.maxParticipants = maxParticipants;
		this.minTasks = minTasks;
		this.maxTasks = maxTasks;
		this.minDelayedTasks = minDelayedTasks;
		this.maxDelayedTasks = maxDelayedTasks;
		this.minCompletionRate = minCompletionRate;
		this.maxCompletionRate = maxCompletionRate;
		this.rangeStartDate = new ArrayList<Date>();
	}
	
	public ProjectFilterDto() {
		this.rangeStartDate = new ArrayList<Date>();
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getMinParticipants() {
		return minParticipants;
	}
	public void setMinParticipants(Integer minParticipants) {
		this.minParticipants = minParticipants;
	}
	public Integer getMaxParticipants() {
		return maxParticipants;
	}
	public void setMaxParticipants(Integer maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	public Integer getMinTasks() {
		return minTasks;
	}
	public void setMinTasks(Integer minTasks) {
		this.minTasks = minTasks;
	}
	public Integer getMaxTasks() {
		return maxTasks;
	}
	public void setMaxTasks(Integer maxTasks) {
		this.maxTasks = maxTasks;
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
	public List<Date> getRangeStartDate() {
		return rangeStartDate;
	}
	public void setRangeStartDate(List<Date> rangeStartDate) {
		this.rangeStartDate = rangeStartDate;
	}
	
	
	
	
}
