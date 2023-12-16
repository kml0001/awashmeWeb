package cu.edu.cujae.backend.core.dto;

//import io.swagger.annotations.ApiModelProperty;

public class IssueDto {

	private	String id;
	private String Tipo; 
	private String Fondo_de_tiempo;
	private double cumplimiento;
	private String persona_asignada;
	
	
	public IssueDto() {
		super();
	}
	
	

	public IssueDto(String tipo, String fondo_de_tiempo, double cumplimiento, String persona_asignada) {
		super();
		this.id = null;
		Tipo = tipo;
		Fondo_de_tiempo = fondo_de_tiempo;
		this.cumplimiento = cumplimiento;
		this.persona_asignada = persona_asignada;
	}



	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public String getFondo_de_tiempo() {
		return Fondo_de_tiempo;
	}

	public void setFondo_de_tiempo(String fondo_de_tiempo) {
		Fondo_de_tiempo = fondo_de_tiempo;
	}

	public double getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(double cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public String getPersona_asignada() {
		return persona_asignada;
	}

	public void setPersona_asignada(String persona_asignada) {
		this.persona_asignada = persona_asignada;
	}
	
	
	
	
}
