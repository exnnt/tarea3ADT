package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "ruta")
public class Ruta implements Serializable {
	public Ruta() {
		
	};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "peregrino_id", nullable = false)
	private Long peregrinoId;

	@Column(name = "parada_id", nullable = false)
	private Long paradaId;

	@Column(name = "orden", nullable = false)
	private int orden;
	@Column(name = "distancia", nullable = true, columnDefinition = "FLOAT(5,1)")
	private Float distanciaKm;
	
	public Ruta(Long id, Long peregrinoId, Long paradaId, int orden, Float distanciaKm) {
		this.id = id;
		this.peregrinoId = peregrinoId;
		this.paradaId = paradaId;
		this.orden = orden;
		this.distanciaKm = distanciaKm;
	}

	public Ruta(Long peregrinoId, Long paradaId, Float distanciaKm) {
		this.peregrinoId = peregrinoId;
		this.paradaId = paradaId;
		this.distanciaKm = distanciaKm;
	}
	public Ruta(Long peregrinoId, Long paradaId, int orden, Float distanciaKm) {
		
		this.peregrinoId = peregrinoId;
		this.paradaId = paradaId;
		this.orden = orden;
		this.distanciaKm = distanciaKm;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPeregrinoId() {
		return peregrinoId;
	}

	public void setPeregrinoId(Long peregrinoId) {
		this.peregrinoId = peregrinoId;
	}

	public Long getParadaId() {
		return paradaId;
	}

	public void setParadaId(Long paradaId) {
		this.paradaId = paradaId;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public Float getDistanciaKm() {
		return distanciaKm;
	}

	public void setDistanciaKm(Float distanciaKm) {
		this.distanciaKm = distanciaKm;
	}

}
