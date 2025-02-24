package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConjuntoContratado implements Serializable {
	private Long id;
	private Long idEstancia;
	private List<Servicio> serviciosContratados;
	private char modoPago; // 'E', 'T', 'B'
	private double precioTotal;
	private String extras = null;

	public ConjuntoContratado() {
	}

	@Override
	public String toString() {
		return " idEstancia=" + idEstancia + ", serviciosContratados=" + printserivicios() + ", modoPago=" + modoPago
				+ ", precioTotal=" + precioTotal + ", extras=" + extras + "]";
	}

	public String printserivicios() {
		String x = "";
		for (Servicio s : serviciosContratados) {
			System.out.println("hii");
			x = x + s.getNombre();
		}
		return x;
	}

	public String toString2() {
		return "ID = " + id + " idEstancia=" + idEstancia + ", serviciosContratados=" + printserivicios()
				+ ", modoPago=" + modoPago + ", precioTotal=" + precioTotal + ", extras=" + extras + "]";
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public ConjuntoContratado(Long id, Long idEstancia, List<Servicio> serviciosContratados, char modoPago,
			double precioTotal) {
		this.id = id;
		this.idEstancia = idEstancia;
		this.serviciosContratados = serviciosContratados;
		this.modoPago = modoPago;
		this.precioTotal = precioTotal;
	}

	public ConjuntoContratado(Long idEstancia) {

		this.idEstancia = idEstancia;
		serviciosContratados = new ArrayList<>();
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

	public List<Servicio> getServiciosContratados() {
		return serviciosContratados;
	}

	public void setServiciosContratados(List<Servicio> serviciosContratados) {
		this.serviciosContratados = serviciosContratados;
	}

	public char getModoPago() {
		return modoPago;
	}

	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public boolean addServicios(Servicio servicio) {
		if (servicio != null && !serviciosContratados.contains(servicio)) {
			serviciosContratados.add(servicio);
			return true;
		}
		return false;
	}

	public boolean deleteServicio(Servicio servicio) {
		if (servicio != null && serviciosContratados.contains(servicio)) {
			serviciosContratados.remove(servicio);
			return true;
		}
		return false;
	}
}
