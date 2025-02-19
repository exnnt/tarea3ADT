package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "paradas")
public class Parada implements Serializable {
	private static final long serialVersionUID = 5211512026320329244L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", length = 255, nullable = false)
	private String nombre;

	@Column(name = "region", length = 1, nullable = false)
	private char region;

	@Column(name = "responsable", length = 100, nullable = false)
	private String responsable;

	@ManyToMany(mappedBy = "paradas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

	private Set<Peregrino> peregrinos = new HashSet<Peregrino>();

	public Parada() {
	}

	public Parada(String nombre, char region, String responsable) {
		super();
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}

	public Parada(Long id, String nombre, char region, String responsable) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}

	@Override
	public String toString() {
		StringBuilder peregrinosNombres = new StringBuilder();
		for (Peregrino peregrino : peregrinos) {
			peregrinosNombres.append(peregrino.getNombre()).append(", ");
		}
		if (peregrinosNombres.length() > 0) {
			peregrinosNombres.setLength(peregrinosNombres.length() - 2);
		}

		return "Parada " + id + "\n" + "Nombre: " + nombre + "\n" + "Region: " + region + "\n" + "Responsable: "
				+ responsable + "\n" + "--------------------";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Set<Peregrino> getPeregrinos() {
		return peregrinos;
	}

	public void setPeregrinos(Set<Peregrino> peregrinos) {
		this.peregrinos = peregrinos;
	}
}