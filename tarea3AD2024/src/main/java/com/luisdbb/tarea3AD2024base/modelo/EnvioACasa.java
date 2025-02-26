package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class EnvioACasa {
	public EnvioACasa() {
		this.volumen = new int[3];
	}

	public EnvioACasa(Long id, Direccion direccion, double peso, int largo, int ancho, int alto, boolean urgente,
			Long idParada) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.peso = peso;
		this.volumen = new int[] { largo, ancho, alto };
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
		return volumen[0];
	}

	public void setLargo(int largo) {
		this.volumen[0] = largo;
	}

	public int getAncho() {
		return volumen[1];
	}

	public void setAncho(int ancho) {
		this.volumen[1] = ancho;
	}

	public int getAlto() {
		return volumen[2];
	}

	public void setAlto(int alto) {
		this.volumen[2] = alto;
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
	private int[] volumen;
	private boolean urgente;
	private Long idParada;
}
