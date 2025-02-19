package com.luisdbb.tarea3AD2024base.modelo;


import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Servicio {
    public Servicio(Long id, String nombre, double precio, List<Long> paradasDisponibles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradasDisponibles = paradasDisponibles;
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

	public List<Long> getParadasDisponibles() {
		return paradasDisponibles;
	}

	public void setParadasDisponibles(List<Long> paradasDisponibles) {
		this.paradasDisponibles = paradasDisponibles;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;

    @ElementCollection
    private List<Long> paradasDisponibles;
}
