package com.luisdbb.tarea3AD2024base.config.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DB4Onosecuantos {

	private static final String DBPATH = "src/main/resources/escritura/db/tarea4_db4o.yap";
	private final ObjectContainer db;

	public DB4Onosecuantos() {

		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBPATH);
	}

	public void store(Object obj) {
		db.store(obj);
		db.commit();
	}

	public <T> List<T> queryByExample(T example) {
		return db.queryByExample(example);
	}

	public <T> List<T> queryAll(Class<T> clase) {
		return db.query(clase);
	}

	public <T> List<T> queryNative(Predicate<T> predicate) {
		return db.query(predicate);
	}

	@PreDestroy
	public void close() {
		if (!db.ext().isClosed()) {
			db.close();
		}
	}
}
