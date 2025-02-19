package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", length = 11, columnDefinition = "INT")
	private int id_user;
	
	@Column(name = "nombre", nullable = false, length = 255, unique = true)
	private String name;

	@Column(name = "password", nullable = false, length = 255)
	private String pass;
	@Column(name = "perfil", nullable = false, length = 11)
	private String type;

	public Usuario() {
	}

	public Usuario(String name, String pass, String type) {
		this.name = name;
		this.pass = pass;
		this.type = type;
	}

	public Usuario(String name, String pass, String type, int id) {
		this.name = name;
		this.pass = pass;
		this.type = type;
		this.id_user = id;
	}

	public int getId() {
		return id_user;
	}

	public void setId(int id) {
		this.id_user = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
