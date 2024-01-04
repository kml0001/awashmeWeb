package cu.edu.cujae.backend.core.dto;

public class UserFilterDto {

	
		private String nameOrLastName;
		
	    private Integer minProjectCountRange ;
	    private Integer maxProjectCountRange ;
	    
	    private Integer minTotalHoursReportedRange;
	    private Integer maxTotalHoursReportedRange;
	    
	    private Integer minDelayedTasksRange;
	    private Integer maxDelayedTasksRange;
	    
		public UserFilterDto(String nameOrLastName, Integer minProjectCountRange, Integer maxProjectCountRange,
				Integer minTotalHoursReportedRange, Integer maxTotalHoursReportedRange, Integer minDelayedTasksRange,
				Integer maxDelayedTasksRange) {
			super();
			this.nameOrLastName = nameOrLastName;
			this.minProjectCountRange = minProjectCountRange;
			this.maxProjectCountRange = maxProjectCountRange;
			this.minTotalHoursReportedRange = minTotalHoursReportedRange;
			this.maxTotalHoursReportedRange = maxTotalHoursReportedRange;
			this.minDelayedTasksRange = minDelayedTasksRange;
			this.maxDelayedTasksRange = maxDelayedTasksRange;
		}
		public String getNameOrLastName() {
			return nameOrLastName;
		}
		public void setNameOrLastName(String nameOrLastName) {
			this.nameOrLastName = nameOrLastName;
		}
		public Integer getMinProjectCountRange() {
			return minProjectCountRange;
		}
		public void setMinProjectCountRange(Integer minProjectCountRange) {
			this.minProjectCountRange = minProjectCountRange;
		}
		public Integer getMaxProjectCountRange() {
			return maxProjectCountRange;
		}
		public void setMaxProjectCountRange(Integer maxProjectCountRange) {
			this.maxProjectCountRange = maxProjectCountRange;
		}
		public Integer getMinTotalHoursReportedRange() {
			return minTotalHoursReportedRange;
		}
		public void setMinTotalHoursReportedRange(Integer minTotalHoursReportedRange) {
			this.minTotalHoursReportedRange = minTotalHoursReportedRange;
		}
		public Integer getMaxTotalHoursReportedRange() {
			return maxTotalHoursReportedRange;
		}
		public void setMaxTotalHoursReportedRange(Integer maxTotalHoursReportedRange) {
			this.maxTotalHoursReportedRange = maxTotalHoursReportedRange;
		}
		public Integer getMinDelayedTasksRange() {
			return minDelayedTasksRange;
		}
		public void setMinDelayedTasksRange(Integer minDelayedTasksRange) {
			this.minDelayedTasksRange = minDelayedTasksRange;
		}
		public Integer getMaxDelayedTasksRange() {
			return maxDelayedTasksRange;
		}
		public void setMaxDelayedTasksRange(Integer maxDelayedTasksRange) {
			this.maxDelayedTasksRange = maxDelayedTasksRange;
		}
	    
}
