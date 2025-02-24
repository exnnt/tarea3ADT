package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoRepository;

@Service
public class ConjuntoService {

	@Autowired
	private ConjuntoRepository conjuntoRepository;

	public ConjuntoContratado guardarconjunto(ConjuntoContratado conjuntoContratado) {

		conjuntoContratado.setId(generarNuevoId());

		conjuntoRepository.guardarconj(conjuntoContratado);
		return conjuntoContratado;
	}

	public List<ConjuntoContratado> listarConjuntosContratados() {
		return conjuntoRepository.listarConjuntosContratados();
	}

	private Long generarNuevoId() {
		List<ConjuntoContratado> todos = listarConjuntosContratados();
		long max = 0;
		for (ConjuntoContratado cc : todos) {
			if (cc.getId() != null && cc.getId() > max) {
				max = cc.getId();
			}
		}
		return max + 1;
	}
}
