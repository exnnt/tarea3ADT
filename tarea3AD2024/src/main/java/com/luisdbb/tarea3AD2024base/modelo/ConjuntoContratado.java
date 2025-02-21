package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.List;

public class ConjuntoContratado implements Serializable {
	private Long id;
	private Long idEstancia;
	private List<Long> serviciosContratados;
	private String modoPago; // 'E', 'T', 'B'
	private double precioTotal;

	public ConjuntoContratado() {
	}

	public ConjuntoContratado(Long id, Long idEstancia, List<Long> serviciosContratados, String modoPago,
			double precioTotal) {
		this.id = id;
		this.idEstancia = idEstancia;
		this.serviciosContratados = serviciosContratados;
		this.modoPago = modoPago;
		this.precioTotal = precioTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(Long idEstancia) {
		this.idEstancia = idEstancia;
	}

	public List<Long> getServiciosContratados() {
		return serviciosContratados;
	}

	public void setServiciosContratados(List<Long> serviciosContratados) {
		this.serviciosContratados = serviciosContratados;
	}

	public String getModoPago() {
		return modoPago;
	}

	public void setModoPago(String modoPago) {
		this.modoPago = modoPago;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
}
