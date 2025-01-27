package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

public class Estancia {
public Estancia(Long id, LocalDate fecha, boolean vip, Long peregrinoId, Long paradaId) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrinoId = peregrinoId;
		this.paradaId = paradaId;
	}
public Long getPeregrinoId() {
	return peregrinoId;
}
public Long getParadaId() {
	return paradaId;
}
public void setPeregrinoId(Long peregrinoId) {
		this.peregrinoId = peregrinoId;
	}
	public void setParadaId(Long paradaId) {
		this.paradaId = paradaId;
	}
public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public boolean isVip() {
		return vip;
	}
	public void setVip(boolean vip) {
		this.vip = vip;
	}
	public Parada getParada() {
		return parada;
	}
	public void setParada(Parada parada) {
		this.parada = parada;
	}
private Long id;
private LocalDate fecha;
private boolean vip=false;
private Parada parada;
private Long peregrinoId;  
private Long paradaId;   
}
