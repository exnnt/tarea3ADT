package com.luisdbb.tarea3AD2024base.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

	Parada findBynombre(String name);
	//el resto estan en jpa creo

	Parada findByResponsable(String r);
	

}
