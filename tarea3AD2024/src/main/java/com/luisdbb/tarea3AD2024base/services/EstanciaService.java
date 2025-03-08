package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;

import jakarta.transaction.Transactional;

@Service
public class EstanciaService {
	@Autowired
	private EstanciaRepository estanciaRepository;
	@Autowired
	private CarnetService carnetService;

	@Transactional
	public Estancia creaEstancia(Peregrino p, Parada user, boolean vip) {
		Estancia carnetet = new Estancia(vip, p.getId(), user.getId());
		
		int vips = 0;
		if (vip)
			vips = 1;

		carnetService.actualizarCarnet(p.getId(), vips);
		return estanciaRepository.save(carnetet);

	}

	// luego devolver para buscar por fecha
	@Transactional
	public List<Estancia> findbyFecha(LocalDate ini, LocalDate fin) {
		return estanciaRepository.findByParadaIdAndFechaBetween(Tarea3Ad2024baseApplication.inicial.getId(), ini, fin);
	}

	public Set<Estancia> findbyPeregrino(Long peregrinoId) {
		return estanciaRepository.findByPeregrinoId(peregrinoId);
	}

}
