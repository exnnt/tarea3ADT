package com.luisdbb.tarea3AD2024base.config.existdb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.CollectionManagementService;

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
				mgtService.createCollection(path);

			} else {
				System.out.println("esto ya existe: " + path);
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public static void parada(String name) {
		String path = "/tarea5ad/" + name;
		createCollection(path);
	}

	public static void storeCarnet(String parada, File carnetFile) {
		testConexion();
		if (!carnetFile.exists() || carnetFile.length() == 0) {
			return;
		}

		try {
			String path = "/tarea5ad/" + parada;
			Collection colparada = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);

			if (colparada == null) {
				createCollection(path);
				colparada = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);
			}

			if (colparada != null) {

				XMLResource resource = (XMLResource) colparada.createResource(carnetFile.getName(), "XMLResource");

				resource.setContent(carnetFile);

				colparada.storeResource(resource);
			} 
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public static void testConexion() {
		try {
			Collection col = DatabaseManager.getCollection(URI, USERNAME, PASSWORD);
			if (col == null) {
				System.err.println("sadgi");
			} 
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getCarnetsParada(String parada) {
		List<String> carnets = new ArrayList<>();
		String path = "/tarea5ad/" + parada;
		Collection collection = null;

		try {
			collection = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);
			if (collection == null) {
				return carnets;
			}

			String[] carnetsDB = collection.listResources();
			if (carnetsDB == null || carnetsDB.length == 0) {
				return carnets;
			}

			for (String carnet : carnetsDB) {
				XMLResource resource = (XMLResource) collection.getResource(carnet);
				if (resource != null) {
					String xml = (String) resource.getContent();
					carnets.add(xml);
				}
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		} finally {
			if (collection != null) {
				try {
					collection.close();
				} catch (XMLDBException e) {
					e.printStackTrace();
				}
			}
		}
		return carnets;
	}

}
