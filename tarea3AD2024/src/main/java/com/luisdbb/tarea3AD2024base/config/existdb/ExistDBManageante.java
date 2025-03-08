package com.luisdbb.tarea3AD2024base.config.existdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

public class ExistDBManageante {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db";

	static {
		try {
			Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createCollection(String path) {
		try {
			testConexion();
			Collection parent = DatabaseManager.getCollection(URI, USERNAME, PASSWORD);
			if (parent == null) {
				System.err.println("nulo mecachis");
				return;
			}

			CollectionManagementService mgtService = (CollectionManagementService) parent
					.getService("CollectionManagementService", "1.0");
			Collection col = DatabaseManager.getCollection(URI + "/" + path, USERNAME, PASSWORD);

			if (col == null) {
				System.out.println("creando: " + path);
				mgtService.createCollection(path);

			} else {
				System.out.println("esto ya existe: " + path);
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public static void storeCarnet(String parada, File carnetFile) {
		testConexion();
		if (!carnetFile.exists() || carnetFile.length() == 0) {
			System.err.println(carnetFile.getName());
			return;
		}

		try (FileInputStream fis = new FileInputStream(carnetFile)) {
			String path = "/tarea5ad/" + parada;
			Collection colparada = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);

			if (colparada == null) {
				System.out.println("creando coleccoin parada : " + parada);
				createCollection(path);
				colparada = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);
			}

			if (colparada != null) {
				XMLResource resource = (XMLResource) colparada.createResource(carnetFile.getName(), "XMLResource");
				resource.setContent(carnetFile);
				colparada.storeResource(resource);
				System.out.println("guardado en " + parada + " el carnet: " + carnetFile.getName());
			} else {
				System.out.println("quemovida no");
			}
		} catch (XMLDBException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void testConexion() {
		try {
			Collection col = DatabaseManager.getCollection(URI, USERNAME, PASSWORD);
			if (col == null) {
				System.err.println("sadgi");
			} else {
				System.out.println("oe");
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

}
