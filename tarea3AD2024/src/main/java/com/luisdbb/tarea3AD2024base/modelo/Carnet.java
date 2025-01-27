package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Carnet implements Serializable{
public Carnet(Long id, Parada parada_inicial, Peregrino user) {
		super();
		this.id = id;
		
		this.parada_inicial = parada_inicial;
		this.user = user;
	}
public Carnet(Long id, Parada parada_inicial, Peregrino user, int nvips, double distancia) {
	super();
	this.id = id;
	this.nvips=nvips;
	this.parada_inicial = parada_inicial;
	this.user = user;
	this.distancia=distancia;
}
public Carnet(Long id, Parada parada_inicial, Peregrino user, int nvips, double distancia, LocalDate date) {
	super();
	this.id = id;
	this.nvips=nvips;
	this.parada_inicial = parada_inicial;
	this.user = user;
	this.distancia=distancia;
	this.fechaexp=date;
}


@Override
	public String toString() {
		return "Id: " + id + ", fechaexp: " + fechaexp + ", distancia: " + distancia + ", nvips:" + nvips
				+ ", Parada inicial: " + parada_inicial.getNombre() + ", Nombre: " + user.getNombre() ;
	}
public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getFechaexp() {
		return fechaexp;
	}
	public void setFechaexp(LocalDate fechaexp) {
		this.fechaexp = fechaexp;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	public int getNvips() {
		return nvips;
	}
	public void setNvips(int nvips) {
		this.nvips = nvips;
	}
	public Parada getParada_inicial() {
		return parada_inicial;
	}
	public void setParada_inicial(Parada parada_inicial) {
		this.parada_inicial = parada_inicial;
	}
	public Peregrino getUser() {
		return user;
	}
	public void setUser(Peregrino user) {
		this.user = user;
	}
private Long id;
private LocalDate fechaexp=LocalDate.now();
private double distancia =0.0;
private int nvips=0;
private Parada parada_inicial;
private Peregrino user;
}
