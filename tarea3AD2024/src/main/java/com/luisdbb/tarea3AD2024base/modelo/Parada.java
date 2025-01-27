package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Parada implements Serializable {
	  private static final long serialVersionUID = 5211512026320329244L; 

public Parada(String nombre, char region, String responsable) {
		super();
		
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
public Parada(Long id,String nombre, char region, String responsable) {
	super();
	this.id= id;
	this.nombre = nombre;
	this.region = region;
	this.responsable = responsable;
}

public String toString() {
    StringBuilder peregrinosNombres = new StringBuilder();
    for (Peregrino peregrino : peregrinos) {
        peregrinosNombres.append(peregrino.getNombre()).append(", ");
    }
    if (peregrinosNombres.length() > 0) {
        peregrinosNombres.setLength(peregrinosNombres.length() - 2);
    }

    return "Parada " + id + "\n" +
    "Nombre: " + nombre + "\n" +
    "Region: " + region + "\n" +
    "Responsable: " + responsable + "\n"+
    "--------------------";


}
public Long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public char getRegion() {
		return region;
	}
	public void setRegion(char region) {
		this.region = region;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public Set<Peregrino> getPeregrinos() {
		return peregrinos;
	}
	public void setPeregrinos(Set<Peregrino> peregrinos) {
		this.peregrinos = peregrinos;
	}
private Long id;
private String nombre;
private char region;
private String responsable;
private Set<Peregrino> peregrinos= new HashSet<Peregrino>();

}
