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

	private static boolean portochecker() {
		try (Socket socket = new Socket("localhost", 6136)) {
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private static void abrePuerto() {
		try {
			File exeFile = new File("scripts/objectdb-2.9.2/bin/server-2.9.2.exe");
			if (exeFile.exists()) {

				ProcessBuilder processBuilder = new ProcessBuilder(exeFile.getAbsolutePath());
				//CAMBIAR AL DIRECTORY DEL EXE O EXPLOTA
				processBuilder.directory(exeFile.getParentFile());
				processBuilder.start();
				Thread.sleep(5000);
				
			} else {
				System.out.println("error1");
			}
		} catch (IOException e) {
			System.out.println("error2");
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void iniciaDB() {
		if (!portochecker()) {
			System.out.println("inicia puerto");
			abrePuerto();

		}
		try {
			System.out.println("before db");
			emf = Persistence.createEntityManagerFactory(
					"objectdb://localhost:6136/ODBPeregrinos.odb;user=admin;password=admin");
			System.out.println("after db");
		} catch (Exception e) {
			System.out.println("aqui");
			System.out.println(e.getMessage());
		}
	}
}