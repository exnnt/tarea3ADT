package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;


@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

	
	Usuario save(Usuario entity);
	Usuario findByName(String name);
	Optional<Usuario> findById(Long id_user);
	List<Usuario> findAll();

}
