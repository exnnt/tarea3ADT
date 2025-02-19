package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;

@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	Set<Estancia> findByPeregrinoId(Long peregrinoId);

	List<Estancia> findByParadaId(Long paradaId);

	List<Estancia> findByParadaIdAndFechaBetween(Long paradaId, LocalDate startDate, LocalDate endDate);

}
