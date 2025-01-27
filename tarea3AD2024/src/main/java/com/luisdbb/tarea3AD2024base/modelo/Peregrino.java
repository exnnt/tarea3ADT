package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Peregrino implements Serializable{
	@Override
	public String toString() {
		return "id: " + id + ", nombre: " + nombre + ", nacionalidad: " + nacionalidad + "";
	}
	public Peregrino(String nombre, String nacionalidad) {
		super();
		
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		}
	public Peregrino(Long id,String nombre, String nacionalidad) {
		super();
		this.id= id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		}
	public Peregrino(Long id,String nombre, String nacionalidad, int id_u) {
		super();
		this.id= id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.id_user=id_u;
		}
private int id_user;
public int getId_user() {
	return id_user;
}
public void setId_user(int id_user) {
	this.id_user = id_user;
}
private Long id;
private String nombre;
private String nacionalidad;
private Set<Estancia> estancias= new HashSet<Estancia>();
private Set<Parada> paradas= new HashSet<Parada>();
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
public String getNacionalidad() {
	return nacionalidad;
}
public void setNacionalidad(String nacionalidad) {
	this.nacionalidad = nacionalidad;
}
public Set<Estancia> getEstancias() {
	return estancias;
}
public void setEstancias(Set<Estancia> estancias) {
	this.estancias = estancias;
}
public Set<Parada> getParadas() {
	return paradas;
}
public void setParadas(Set<Parada> paradas) {
	this.paradas = paradas;
}
public void addParada(Parada parada) {
    this.paradas.add(parada);
}
public void nuevoPeregrino() {
	
	
}



}
