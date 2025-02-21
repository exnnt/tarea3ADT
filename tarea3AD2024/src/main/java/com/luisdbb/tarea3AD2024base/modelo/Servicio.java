package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;

public class Servicio implements Serializable {
	private Long id;
	private String nombre;
	private double precio;

	public Servicio() {
	}

	public Servicio(Long id, String nombre, double precio) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
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
}
