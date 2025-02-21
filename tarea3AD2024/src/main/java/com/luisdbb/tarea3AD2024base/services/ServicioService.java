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

	// id null si es nuevo
	public Servicio serviciear(Long id, String nombre, double precio) {
		Servicio s = null;

		if (id != null) {

			Servicio temp = new Servicio();
			temp.setId(id);

			List<Servicio> resultados = db4o.queryByExample(temp);
			if (!resultados.isEmpty()) {
				s = resultados.get(0);
			}
		}

		if (s == null) {
			s = new Servicio();

			s.setId(nuevoid());
		}

		s.setNombre(nombre);
		s.setPrecio(precio);

		db4o.store(s);
		return s;
	}

	public List<Servicio> listarServicios() {
		return db4o.queryAll(Servicio.class);
	}

	private Long nuevoid() {

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