package com.luisdbb.tarea3AD2024base.config.db4o;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.List;

@Component
public class DB4Onosecuantos {

	private static final String DBPATH = "src/main/resources/escritura/db/tarea4_db4o.yap";
	private static DB4Onosecuantos INSTANCE = null;
	private static ObjectContainer db;
	public DB4Onosecuantos() {
		open();
		  Runtime.getRuntime().addShutdownHook(new Thread(() -> close()));
	}
	 private static synchronized void instanciar() 
	    {
	        if (INSTANCE == null) {
	            INSTANCE = new DB4Onosecuantos();
	        }
	    }
	 public static ObjectContainer getInstance() 
	    {
	        if (INSTANCE == null) 
	        {
	            instanciar();
	        }
	        return db;
	    }

		public void store(Object obj) {
			open();
			db.store(obj);
			db.commit();
			System.out.println((Servicio) obj);
			
		}
		
		public <T> List<T> queryByExample(T example) {
			open();
			return db.queryByExample(example);
		}

		public <T> List<T> queryNative(Predicate<T> predicate) {
			open();
			return db.query(predicate);
		}

		public void close() {
		if (!db.ext().isClosed()) {
			db.close();
		}
	}

	 public void open() {
	        if (db == null || db.ext().isClosed() ) {
	        	//esto pa siempre en los db4o
	        	 var config = Db4oEmbedded.newConfiguration();
	             config.common().objectClass(Servicio.class).cascadeOnUpdate(true); 
	        	 db = Db4oEmbedded.openFile(config, DBPATH);
	        }
	        
	        
	 }
	    }
