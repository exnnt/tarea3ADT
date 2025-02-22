package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Servicio implements Serializable {
	private Long id;
	private String nombre;
	private double precio;
	private List<Long> paradas;

	public Servicio() {
	    this.paradas = new ArrayList<>();
	}

	public Servicio(Long id, String nombre, double precio) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradas = (paradas != null) ? paradas : new ArrayList<>(); 

	}
	public Servicio(Long id, String nombre, double precio, List<Long> paradas) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradas= paradas;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<Long> getParadas() {
		return paradas;
	}

	public void setParadas(List<Long> paradas) {
		this.paradas = paradas;
	}
	public void addParada(Long id) {
	 if (paradas == null) {		 
            paradas = new ArrayList<>(); //inicializar pa q no rompa 
	 }
		paradas.add(id);
	}
	public String getparadasString() {
		if (paradas == null || paradas.isEmpty()) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < paradas.size(); i++) {
			
			result.append(paradas.get(i));
			
			if (i < paradas.size() - 1) {
				result.append(", ");
			}
		}

		return result.toString();
	}
	public String toString() {
		return getparadasString();
	}

}
