package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {
	@Query("SELECT p FROM Peregrino p WHERE p.id_user = :idUser")
	Peregrino findByIdUser(@Param("idUser") int idUser);
}
