package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

import jakarta.transaction.Transactional;
import java.util.Properties;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Usuario crearUsuario(Usuario user) {
		return userRepository.save(user);
	}

	public Integer crearUsuarioReturnId(Usuario usuario) {
		Usuario savedUser = userRepository.save(usuario);
		return savedUser.getId();
	}

	public Usuario findByName(String name) {
		return userRepository.findByName(name);
	}

	public Usuario update(Usuario entity) {
		return userRepository.save(entity);
	}

	public Usuario find(Integer id) {
		return userRepository.findById(id).get();
	}

	public List<Usuario> findAll() {
		return userRepository.findAll();
	}

	@Value("${usuario.admin}")
	private String adminUsername;
	@Value("${pass.admin}")
	private String adminPassword;

	public int authenticate(String username, String password) {
		if (username.equals(adminUsername) && password.equals(adminPassword)) {
			return 1;
		} else {
			Usuario user = this.findByUsername(username);
			if (user == null) {
				return 0;
			} else {

				if (password.equals(user.getPass()))
					if (user.getType().equals("peregrino"))
						return 3;
					else
						return 2;
				else
					return 0;
			}
		}
	}

	private Usuario findByUsername(String username) {
		return userRepository.findByName(username);

	}

}
