package com.luisdbb.tarea3AD2024base.config.objectdb;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ObjectDBnsq {

	private static EntityManagerFactory emf;
	static {
		iniciaDB();
	}

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void cerrar() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		
		}
	}
	
	public static void iniciaDB() {
			try {
			emf = Persistence.createEntityManagerFactory(
					"objectdb://localhost:6136/ODBPeregrinos.odb;user=admin;password=admin");
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}