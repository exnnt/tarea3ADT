package com.luisdbb.tarea3AD2024base.config.mongodb;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

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

	public void cerrarConexion() {
		if (mongoClient != null) {
			mongoClient.close();
			System.out.println("closed");
		}
	}

	public static void main(String[] args) {
		System.out.println("yea");
		MongoDB manager = new MongoDB();

		manager.cerrarConexion();

	}
}