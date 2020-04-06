package com.robert.app.item.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Region {
	
		@Id 
		@Column (unique = true)
		private String idRegion;
		private String nombreRegion;
		public String getIdRegion() {
			return idRegion;
		}
		public void setIdRegion(String idRegion) {
			this.idRegion = idRegion;
		}
		public String getNombreRegion() {
			return nombreRegion;
		}
		public void setNombreRegion(String nombreRegion) {
			this.nombreRegion = nombreRegion;
		}
		
		
		
		//Getters and Setters
	

	
	
	
	

}
