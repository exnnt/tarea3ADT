package com.luisdbb.tarea3AD2024base.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ConjuntoContratado {
    public ConjuntoContratado(Long id, Long idEstancia, List<Long> serviciosContratados, String modoPago,
			double precioTotal) {
		super();
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
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idEstancia;
    private List<Long> serviciosContratados;
    private String modoPago; // 'E' (Efectivo), 'T' (Tarjeta), 'B' (Bizum)
    private double precioTotal;
}

