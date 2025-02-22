package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.luisdbb.tarea3AD2024base.config.db4o.DB4Onosecuantos;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ServiceRepository {
	private ObjectContainer db;

//luego lo muevo todo cuando funcione 

	public ServiceRepository() {
		this.db = DB4Onosecuantos.getInstance();
	}

	public void guardarservi(Servicio s) {
		db.store(s);
		db.commit();
	}

	public List<Servicio> listservicios() {
		return db.query(Servicio.class);
	}
}
