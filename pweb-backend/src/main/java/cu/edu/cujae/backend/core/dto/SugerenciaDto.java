package cu.edu.cujae.backend.core.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class SugerenciaDto {

	@ApiModelProperty(hidden = true)
	private int id;
	private String Datos;
	
	@ApiModelProperty(hidden = true)
	private String fecha_creacion;
	private String texto;
	private String importancia;
	private String urgencia;

	
	public SugerenciaDto( String datos, String texto, String importancia, String urgencia) {
		super();
		this.id = -1;
		Datos = datos;
		this.fecha_creacion = new Date().toString();
		this.texto = texto;
		this.importancia = importancia;
		this.urgencia = urgencia;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDatos() {
		return Datos;
	}
	public void setDatos(String datos) {
		Datos = datos;
	}
	public String getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getImportancia() {
		return importancia;
	}
	public void setImportancia(String importancia) {
		this.importancia = importancia;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	
	
	
	
	
}
