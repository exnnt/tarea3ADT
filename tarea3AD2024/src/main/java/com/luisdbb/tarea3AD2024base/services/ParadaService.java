package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;

import jakarta.transaction.Transactional;

@Service
public class ParadaService {
	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
	private UserService userService;

	@Transactional
	public Parada crearParada(Parada user,String responsable, String pass) {		
		Usuario respons= new Usuario(responsable, pass, "parada");
		userService.crearUsuario(respons);
		return paradaRepository.save(user);
	}

	public Parada find(Long id) {
		return paradaRepository.findById(id).get();
	}

	public Parada findbyName(String name) {
		return paradaRepository.findBynombre(name);
	}

	public Parada findbyrespons(String r) {
		return paradaRepository.findByResponsable(r);
	}

	public List<Parada> findAll() {
		return paradaRepository.findAll();
	}

}
