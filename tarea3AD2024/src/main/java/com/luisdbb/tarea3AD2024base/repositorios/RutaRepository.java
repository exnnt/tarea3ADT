package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Ruta;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

	List<Ruta> findByPeregrinoIdOrderByOrden(Long peregrinoId);

	List<Ruta> findByParadaId(Long paradaId);

}
