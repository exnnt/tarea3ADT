package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Ruta;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.RutaRepository;

import jakarta.transaction.Transactional;

@Service
public class RutaService {
	@Autowired
	private RutaRepository rutaRepository;
	@Autowired
	private CarnetService carnetService;

	public List<Ruta> obtenerRutasPorPeregrino(Long peregrinoId) {
		return rutaRepository.findByPeregrinoIdOrderByOrden(peregrinoId);
	}

	@Transactional
	public void crearRuta(Long peregrinoId, Long paradaId, float distanciaKm) {

		List<Ruta> rutas = rutaRepository.findByPeregrinoIdOrderByOrden(peregrinoId);

		int nuevoOrden = 1;
		if (!rutas.isEmpty())
			nuevoOrden = rutas.get(rutas.size() - 1).getOrden() + 1;
		Ruta ruta = new Ruta();
		ruta.setPeregrinoId(peregrinoId);
		ruta.setParadaId(paradaId);
		ruta.setOrden(nuevoOrden);
		ruta.setDistanciaKm(distanciaKm);
		rutaRepository.save(ruta);
		carnetService.actualizarCarnet(peregrinoId, distanciaKm, 0);

	}

}
