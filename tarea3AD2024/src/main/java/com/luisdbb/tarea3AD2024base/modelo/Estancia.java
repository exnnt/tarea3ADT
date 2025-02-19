package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "estancias")
public class Estancia {
		public Estancia() {
			
		};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "peregrino_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Peregrino peregrino;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parada_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Parada parada;

	@Column(name = "peregrino_id", nullable = false)
	private Long peregrinoId;

	@Column(name = "parada_id", nullable = false)
	private Long paradaId;

	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@Column(name = "vip", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean vip = false;

	private String vipStatus;
	public String getvipstring() {
		return vipStatus;
	}

	public Estancia(Long id, LocalDate fecha, boolean vip, Long peregrinoId, Long paradaId) {
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrinoId = peregrinoId;
		this.paradaId = paradaId;
	}
	 public Estancia(long id, String fecha, long peregrinoId, long paradaId, String vipStatus) {
	        this.id = id;
	        this.fechastring = fecha;
	        this.peregrinoId = peregrinoId;
	        this.paradaId = paradaId;
	        this.vipStatus = vipStatus;
	    }
	
	@Override
	public String toString() {
		return "Estancia [id=" + id + ", peregrino=" + peregrino + ", parada=" + parada + ", peregrinoId=" + peregrinoId
				+ ", paradaId=" + paradaId + ", fecha=" + fecha + ", vip=" + vip + "]";
	}

	public Estancia(boolean vip, Long peregrinoId, Long paradaId) {
		
		this.fecha = LocalDate.now();
		this.vip = vip;
		this.peregrinoId = peregrinoId;
		this.paradaId = paradaId;
	}
	private String fechastring;
	public String getFestring() {
		return fechastring;
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

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}
}
