package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	  @Transactional
	    public Usuario crearUsuario(Usuario user) {
	        return userRepository.save(user); 
	    }

	public Usuario update(Usuario entity) {
		return userRepository.save(entity);
	}

	

	public Usuario find(Long id) {
		return userRepository.findById(id).get();
	}

	public List<Usuario> findAll() {
		return userRepository.findAll();
	}

	public boolean authenticate(String username, String password) {
		Usuario user = this.findByUsername(username);
		if (user == null) {
			return false;
		} else {
			if (password.equals(user.getPass()))
				return true;
			else
				return false;
		}
	}

	private Usuario findByUsername(String username) {
		return userRepository.findByName(username);
		
	}


	

}
