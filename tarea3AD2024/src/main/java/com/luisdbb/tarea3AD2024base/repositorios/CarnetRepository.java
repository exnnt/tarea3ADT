package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {
	//no me funcionaba sin el query pq me iba a user 
	
}
