package cu.edu.cujae.pweb.dto;

public class SuggestionDto {
	private int id;

	private String created_on;
	
	private int author_id;
	private String author_name;
	private String subject;
	private String text;
	private String urgency;
	private String importance;
	public SuggestionDto(int id, int author_id, String description, String created_on, String urgency, String importance , String subject,
			String author_name) {
		super();
		this.id = id;
		this.author_id = author_id;
		this.author_name = author_name;
		this.text = description;
		this.created_on = created_on;
		this.urgency = urgency;
		this.importance = importance;
		this.subject = subject;
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
	public String getText() {
		return text;
	}
	public void setText(String description) {
		this.text = description;
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
	
	public String getSubject() {
		return this.subject;
	}
	
	public void serSubject(String subject) {
		this.subject = subject;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
}

