package cu.edu.cujae.backend.core.dto;

import io.swagger.annotations.ApiModelProperty;

public class SuggestionDto {
	@ApiModelProperty(hidden = true)
	private int id;
	@ApiModelProperty(hidden = true)
	private String created_on;
	
	private int author_id;
	private int project_id;
	private String description;
	private String urgency;
	private String importance;
	public SuggestionDto(int id, int author_id, String description, String created_on, String urgency, String importance , int project_id) {
		super();
		this.id = id;
		this.author_id = author_id;
		this.description = description;
		this.created_on = created_on;
		this.urgency = urgency;
		this.importance = importance;
		this.project_id = project_id;
	}
	public SuggestionDto() {
	}
	
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	
}
