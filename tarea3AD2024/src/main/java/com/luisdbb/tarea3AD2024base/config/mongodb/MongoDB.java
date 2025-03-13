package com.luisdbb.tarea3AD2024base.config.mongodb;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.CarnetService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Component
public class MongoDB {
	private static final String DB_NAME = "carnetsDB";
	private static final String COLLECTION_BASE = "backups";
	private MongoClient mongoClient;
	private MongoDatabase database;
	@Autowired
	private CarnetService carnetservice;

	public MongoDB() {
		connect();
	}
	@Autowired
	public MongoDB(CarnetService carnetService) {
	    this.carnetservice = carnetService;
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
    	 List<Carnet> carnetList = carnetservice.getTodosCarnets();
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String colcarnets = "backupcarnets<" + fecha+">";
        //se q no hay q poner los <> pero me queda mejor ig
        MongoCollection<Document> collection = database.getCollection(colcarnets);
        
        for (Carnet carnet : carnetList) {
            Document doc = new Document("id", carnet.getId())
                    .append("distancia", carnet.getDistancia())
                    .append("fecha_exp", carnet.getFechaexp().toString())
                    .append("n_vips", carnet.getNvips())
                    .append("parada_inicial", carnet.getParada_inicial().getNombre());
            
            collection.insertOne(doc);
        }
        System.out.println("backup creado: " + colcarnets);
    }


	public void cerrarConexion() {
		if (mongoClient != null) {
			mongoClient.close();
			System.out.println("closed");
		}
	}


}