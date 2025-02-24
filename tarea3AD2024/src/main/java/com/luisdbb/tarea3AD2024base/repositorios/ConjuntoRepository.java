package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.luisdbb.tarea3AD2024base.config.db4o.DB4Onosecuantos;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;

@Repository
public class ConjuntoRepository {

	private ObjectContainer db;

	public ConjuntoRepository() {
		this.db = DB4Onosecuantos.getInstance();
	}

	public void guardarconj(ConjuntoContratado conjuntoContratado) {
		db.store(conjuntoContratado);
		// creo q no hace falta tbh
		db.commit();
	}

	public List<ConjuntoContratado> listarConjuntosContratados() {
		return db.query(ConjuntoContratado.class);
	}

}
