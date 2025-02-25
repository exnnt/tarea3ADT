package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ServiceRepository;

@Service
public class EnvioService {
	 @Autowired
		private EnvioRepository envioRepository;
	 public void registrarEnvio(EnvioACasa envio) 
	    {
	        envioRepository.guardarenvio(envio);
	    }
	 public List<EnvioACasa> getenvios() 
	    {
	        return envioRepository.getenvios();
	    }
	 public List<EnvioACasa> getenviosParada(Long idParada) 
	    {
	        return envioRepository.getenviosParada(idParada);
	    }
}
