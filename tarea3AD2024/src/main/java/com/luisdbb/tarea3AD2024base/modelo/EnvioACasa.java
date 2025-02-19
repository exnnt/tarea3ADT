package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class EnvioACasa {
    public EnvioACasa(Long id, Direccion direccion, double peso, int largo, int ancho, int alto, boolean urgente,
			Long idParada) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.peso = peso;
		this.largo = largo;
		this.ancho = ancho;
		this.alto = alto;
		this.urgente = urgente;
		this.idParada = idParada;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getLargo() {
		return largo;
	}
	public void setLargo(int largo) {
		this.largo = largo;
	}
	public int getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public boolean isUrgente() {
		return urgente;
	}
	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}
	public Long getIdParada() {
		return idParada;
	}
	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;

    private double peso; 
    private int largo;
    private int ancho;
    private int alto;
    private boolean urgente;
    private Long idParada;
}
