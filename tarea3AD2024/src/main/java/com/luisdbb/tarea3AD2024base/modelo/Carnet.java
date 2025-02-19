package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "carnets")
public class Carnet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_exp", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
	private LocalDate fechaexp;

	@Column(name = "distancia", nullable = true, columnDefinition = "FLOAT(5,1)")
	private Float distancia;

	@Column(name = "n_vip", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int nvips = 0;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pinicial_id", nullable = false)
	private Parada parada_inicial;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private Peregrino user;
	public Carnet() {
		
	}
	public Carnet(Long id, Parada parada_inicial, Peregrino user) {
		super();
		this.id = id;
		this.parada_inicial = parada_inicial;
		this.user = user;
	}
	public Carnet(Peregrino user, Parada parada_inicial) {
		super();
		this.user=user;
		this.parada_inicial=parada_inicial;
		nvips = 0;
		distancia=(float) 0.0;
		this.fechaexp= LocalDate.now();
		//cba to test it all rn tbh
		// time
		
	}

	public Carnet(Long id, Parada parada_inicial, Peregrino user, int nvips, Float distancia) {
		super();
		this.id = id;
		this.nvips = nvips;
		this.parada_inicial = parada_inicial;
		this.user = user;
		this.distancia = distancia;
	}

	public Carnet(Long id, Parada parada_inicial, Peregrino user, int nvips, Float distancia, LocalDate date) {
		super();
		this.id = id;
		this.nvips = nvips;
		this.parada_inicial = parada_inicial;
		this.user = user;
		this.distancia = distancia;
		this.fechaexp = date;
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

	public void setDistancia(Float distancia) {
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

	@Override
	public String toString() {
		return "Id: " + id + ", fechaexp: " + fechaexp + ", distancia: " + distancia + ", nvips:" + nvips
				+ ", Parada inicial: " + parada_inicial.getNombre() + ", Nombre: " + user.getNombre();
	}
}
