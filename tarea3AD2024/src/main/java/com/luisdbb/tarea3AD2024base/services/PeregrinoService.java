package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

import jakarta.transaction.Transactional;

@Service
public class PeregrinoService {
	@Autowired
	PeregrinoRepository peregrinoRepository;
	@Transactional
	public Peregrino creaPeregrino(Peregrino user) {
		return peregrinoRepository.save(user);
	}
	
	public Peregrino findbyiduser(int i) {
		return peregrinoRepository.findByIdUser(i);
	}
	
}
