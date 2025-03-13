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
				System.out.println("creando: " + path);
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
			System.err.println("no valid file");
			return;
		}

		try {
			String path = "/tarea5ad/" + parada;
			Collection colparada = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);
			
			if (colparada == null) {
				System.out.println("creando coleccoin parada : " + parada);
				createCollection(path);
				colparada = DatabaseManager.getCollection(URI + path, USERNAME, PASSWORD);
			}

			if (colparada != null) {
				System.out.println("aqui");
				XMLResource resource = (XMLResource) colparada.createResource(carnetFile.getName(), "XMLResource");
				System.out.println("aqui2");
				resource.setContent(carnetFile);
				System.out.println("aqui3");
				//en verdad me da donde falla el stacktrace pero por comprobar
				colparada.storeResource(resource);
				System.out.println("aqui5");
				System.out.println("guardado en " + parada + " el carnet: " + carnetFile.getName());
			} else {
				System.out.println("quemovida no");
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
			} else {
				System.out.println("oe");
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
	                System.out.println("mecachi");
	                return carnets;
	            }
	            
	            String[] carnetsDB = collection.listResources();
	            if (carnetsDB == null || carnetsDB.length == 0) {
	                System.out.println("parda vacia");
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
