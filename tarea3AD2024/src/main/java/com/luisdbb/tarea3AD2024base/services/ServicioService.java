package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.config.db4o.DB4Onosecuantos;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ServiceRepository;

import java.util.List;

@Service
public class ServicioService {
	 @Autowired
	private ServiceRepository serviceRepository;

	public Servicio serviciar(String nombre, double precio) {

		Servicio s = new Servicio();
		s.setId(nuevoId());
		s.setNombre(nombre);
		s.setPrecio(precio);
		System.out.println(nombre);
		System.out.println(s.getId());
		serviceRepository.guardarservi(s);
		return s;
	}

	public Servicio editar(Long id, String nombre, double precio) {
		Servicio temp = new Servicio();
		temp.setId(id);

		List<Servicio> resultados = serviceRepository.listservicios();

		if (!resultados.isEmpty()) {
			Servicio s = resultados.get(0);
			s.setNombre(nombre);
			s.setPrecio(precio);

			serviceRepository.guardarservi(s);
			return s;
		} else {
			throw new RuntimeException("servicio con id " + id + " no encontrado");
		}
	}

	public Servicio editar2(Long id, String nombre, double precio, List<Long> xd) {
		Servicio temp = new Servicio();
		temp.setId(id);

		List<Servicio> resultados = serviceRepository.listservicios();

		if (!resultados.isEmpty()) {
			Servicio s = resultados.get(0);
			s.setNombre(nombre);
			s.setPrecio(precio);

			serviceRepository.guardarservi(s);
			return s;
		} else {
			throw new RuntimeException("servicio con id " + id + " no encontrado");
		}
	}

	public void editar1(Servicio s) {

		serviceRepository.guardarservi(s);
		
		  System.out.println("guardaos te lo juro");
		
	}

	public List<Servicio> listarServicios() {
		 List<Servicio> servicios = serviceRepository.listservicios();
		 for (Servicio servicio : servicios) {
			 servicio.getParadas().size();
		        System.out.println("Servicio: " + servicio.getId() + " Paradas: " + servicio.getparadasString());
		    }
		    return servicios;
	}

	private Long nuevoId() {
		List<Servicio> todos =serviceRepository.listservicios();
		long max = 0;
		for (Servicio s : todos) {
			if (s.getId() != null && s.getId() > max) {
				max = s.getId();
			}
		}
		return max + 1;
	}
}