package cu.edu.cujae.backend.core.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserDto {
		@ApiModelProperty(hidden = true)
		private int id;
		private String name;
		private String Datos_generales; 
		private int cant_project;
		private double cant_horas_reportadas;
		private int cant_tareas_atrasadas;
		
		
		public UserDto(String datos_generales,String name, int cant_project, double cant_horas_reportadas,
				int cant_tareas_atrasadas) {
			super();
			this.id = -1;
			this.name = name;
			Datos_generales = datos_generales;
			this.cant_project = cant_project;
			this.cant_horas_reportadas = cant_horas_reportadas;
			this.cant_tareas_atrasadas = cant_tareas_atrasadas;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDatos_generales() {
			return Datos_generales;
		}

		public void setDatos_generales(String datos_generales) {
			Datos_generales = datos_generales;
		}

		public int getCant_project() {
			return cant_project;
		}

		public void setCant_project(int cant_project) {
			this.cant_project = cant_project;
		}

		public double getCant_horas_reportadas() {
			return cant_horas_reportadas;
		}

		public void setCant_horas_reportadas(double cant_horas_reportadas) {
			this.cant_horas_reportadas = cant_horas_reportadas;
		}

		public int getCant_tareas_atrasadas() {
			return cant_tareas_atrasadas;
		}

		public void setCant_tareas_atrasadas(int cant_tareas_atrasadas) {
			this.cant_tareas_atrasadas = cant_tareas_atrasadas;
		}
	
	
	
	
}
