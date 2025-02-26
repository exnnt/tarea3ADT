package com.luisdbb.tarea3AD2024base.config.objectdb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ObjectDBnsq {
	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("objectdb://localhost:6136/ODBPeregrinos.odb;user=admin;password=admin");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void cerrar() {
		if (emf != null && emf.isOpen()) {
			emf.close();
			System.out.println("close");
		}
	}
}