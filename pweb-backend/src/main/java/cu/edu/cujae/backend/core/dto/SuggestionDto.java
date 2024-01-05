package cu.edu.cujae.backend.core.dto;

import io.swagger.annotations.ApiModelProperty;

public class SuggestionDto {
	@ApiModelProperty(hidden = true)
	private int id;
	@ApiModelProperty(hidden = true)
	private String created_on;
	
	private int author_id;
	private String subjet;
	private String text;
	private String urgency;
	private String importance;
	public SuggestionDto(int id, int author_id, String description, String created_on, String urgency, String importance , String subject) {
		super();
		this.id = id;
		this.author_id = author_id;
		this.text = description;
		this.created_on = created_on;
		this.urgency = urgency;
		this.importance = importance;
	}
	public SuggestionDto() {
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
	public String getSubjet() {
		return subjet;
	}
	public void setSubjet(String subjet) {
		this.subjet = subjet;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
