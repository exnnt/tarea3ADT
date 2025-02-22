package com.luisdbb.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.config.db4o.DB4Onosecuantos;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

import java.util.List;

@Service
public class ServicioService {

	private final DB4Onosecuantos db4o;

	public ServicioService(DB4Onosecuantos db4o) {
		this.db4o = db4o;
	}

	public Servicio serviciar(String nombre, double precio) {

		Servicio s = new Servicio();
		s.setId(nuevoId());
		s.setNombre(nombre);
		s.setPrecio(precio);
		System.out.println(nombre);
		System.out.println(s.getId());
		db4o.store(s);
		return s;
	}

	public Servicio editar(Long id, String nombre, double precio) {
		Servicio temp = new Servicio();
		temp.setId(id);

		List<Servicio> resultados = db4o.queryByExample(temp);

		if (!resultados.isEmpty()) {
			Servicio s = resultados.get(0);
			s.setNombre(nombre);
			s.setPrecio(precio);

			db4o.store(s);
			return s;
		} else {
			throw new RuntimeException("servicio con id " + id + " no encontrado");
		}
	}

	public Servicio editar2(Long id, String nombre, double precio, List<Long> xd) {
		Servicio temp = new Servicio();
		temp.setId(id);

		List<Servicio> resultados = db4o.queryByExample(temp);

		if (!resultados.isEmpty()) {
			Servicio s = resultados.get(0);
			s.setNombre(nombre);
			s.setPrecio(precio);

			db4o.store(s);
			return s;
		} else {
			throw new RuntimeException("servicio con id " + id + " no encontrado");
		}
	}

	public void editar1(Servicio s) {

		db4o.store(s);
		
		  System.out.println("guardaos te lo juro");
		
	}

	public List<Servicio> listarServicios() {
		 List<Servicio> servicios = db4o.queryAll(Servicio.class);
		 for (Servicio servicio : servicios) {
			 servicio.getParadas().size();
		        System.out.println("Servicio: " + servicio.getId() + " Paradas: " + servicio.getparadasString());
		    }
		    return servicios;
	}

	private Long nuevoId() {
		List<Servicio> todos = db4o.queryAll(Servicio.class);
		long max = 0;
		for (Servicio s : todos) {
			if (s.getId() != null && s.getId() > max) {
				max = s.getId();
			}
		}
		return max + 1;
	}
}