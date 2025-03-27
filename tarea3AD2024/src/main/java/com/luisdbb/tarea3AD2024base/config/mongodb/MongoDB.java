package com.luisdbb.tarea3AD2024base.config.mongodb;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoDB {
	private static final String DB_NAME = "carnetsDB";
	private static final String COLLECTION_BASE = "backups";
	private MongoClient mongoClient;
	private MongoDatabase database;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private PeregrinoService peregrinoService;

	public MongoDB() {
		connect();
	}

	@Autowired
	public MongoDB(CarnetService carnetService, PeregrinoService peregrinoService) {
		this.carnetService = carnetService;
		this.peregrinoService = peregrinoService;
		connect();
	}

	private void connect() {
		try {
			this.mongoClient = MongoClients.create("mongodb://localhost:27017");
			this.database = mongoClient.getDatabase(DB_NAME);

			MongoCollection<Document> collection = database.getCollection(COLLECTION_BASE);
			if (collection.countDocuments() == 0) {
				Document crea = new Document("init", true);
				collection.insertOne(crea);
				System.out.println("database creada creo");

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void backupCarnets() {
		List<Peregrino> peregrinos = peregrinoService.findall();
		for (Peregrino p : peregrinos) {
			try {
				carnetService.exportCarnet(p);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Document> carnetDocs = new ArrayList<>();
		File escritura = new File("src/main/resources/escritura");
		File[] xemeles = escritura.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

		if (xemeles != null) {
			for (File file : xemeles) {
				Document carnetDoc = carnetxml(file);
				if (carnetDoc != null) {
					carnetDocs.add(carnetDoc);
				}
			}
		}

		String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
		Document backupDoc = new Document("fecha", fecha).append("carnets", carnetDocs).append("nombre",
				"backupcarnets_" + fecha);

		MongoCollection<Document> collection = database.getCollection("backups");
		collection.insertOne(backupDoc);
		System.out.println("creado:  " + backupDoc.getString("nombre"));
	}

	private Document carnetxml(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document xmlDocument = builder.parse(file);
			xmlDocument.getDocumentElement().normalize();

			Element root = xmlDocument.getDocumentElement();

			int id = getIntFromTag(root, "id");
			String fechaExp = getStringFromTag(root, "fechaexp");
			String expedidoEn = getStringFromTag(root, "expedidoen");
			String hoy = getStringFromTag(root, "hoy");
			String distanciaTotal = getStringFromTag(root, "distanciatotal");

			Element peregrinoElement = (Element) root.getElementsByTagName("peregrino").item(0);
			String nombrePeregrino = getStringFromTag(peregrinoElement, "nombre");
			String nacionalidad = getStringFromTag(peregrinoElement, "nacionalidad");

			List<Document> paradasList = new ArrayList<>();
			NodeList paradaNodes = root.getElementsByTagName("parada");
			if (paradaNodes != null) {
				for (int i = 0; i < paradaNodes.getLength(); i++) {
					if (paradaNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element paradaElement = (Element) paradaNodes.item(i);
						String nombre = getStringFromTag(paradaElement, "nombre");
						String region = getStringFromTag(paradaElement, "region");
						if (!nombre.isEmpty() && !region.isEmpty()) {
							Document paradaDoc = new Document("orden", getIntFromTag(paradaElement, "orden"))
									.append("nombre", nombre).append("region", region);
							paradasList.add(paradaDoc);
						}
					}
				}
			}
			
			List<Document> estanciasList = new ArrayList<>();
			NodeList estanciaNodes = root.getElementsByTagName("estancia");
			if (estanciaNodes != null) {
				for (int i = 0; i < estanciaNodes.getLength(); i++) {
					if (estanciaNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element estanciaElement = (Element) estanciaNodes.item(i);
						Document estanciaDoc = new Document("id", getIntFromTag(estanciaElement, "id"))
								.append("fecha", getStringFromTag(estanciaElement, "fecha"))
								.append("parada", getStringFromTag(estanciaElement, "parada"))
								.append("vip", estanciaElement.getElementsByTagName("vip").getLength() > 0);
						estanciasList.add(estanciaDoc);
					}
				}
			}

			return new Document("id", id).append("fechaExp", fechaExp).append("hoy", hoy)
					.append("expedidoEn", expedidoEn)
					.append("peregrino", new Document("nombre", nombrePeregrino).append("nacionalidad", nacionalidad))
					.append("distanciaTotal", distanciaTotal).append("paradas", paradasList)
					.append("estancias", estanciasList);

		} catch (Exception e) {
			System.err.println("Error procesando XML " + file.getName() + ": " + e.getMessage());
			return null;
		}
	}

	private String getStringFromTag(Element element, String tag) {
		try {
			NodeList nodeList = element.getElementsByTagName(tag);
			if (nodeList != null && nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				if (node != null) {
					return node.getTextContent().trim();
				}
			}
		} catch (Exception e) {
			System.err.println("error1" + tag);
		}
		return "";
	}

	private int getIntFromTag(Element element, String tag) {
		try {
			String value = getStringFromTag(element, tag);
			if (!value.isEmpty()) {
				return Integer.parseInt(value);
			}
		} catch (Exception e) {
			System.err.println("error2" + tag);
		}
		return 0;
	}

	public void cerrarConexion() {
		if (mongoClient != null) {
			mongoClient.close();
			System.out.println("closed");
		}
	}

}