package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.config.objectdb.ObjectDBnsq;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
@Repository
public class EnvioRepository {

	public void guardarenvio(EnvioACasa envio) {

		EntityManager em = ObjectDBnsq.getEntityManager();
		em.getTransaction().begin();
		em.persist(envio);
		em.getTransaction().commit();
		em.close();
	}

	public List<EnvioACasa> getenvios() {

		EntityManager em = ObjectDBnsq.getEntityManager();
		TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e", EnvioACasa.class);
		List<EnvioACasa> envios = query.getResultList();
		em.close();
		return envios;
	}

	public List<EnvioACasa> getenviosParada(Long idParada) {
		EntityManager em = ObjectDBnsq.getEntityManager();

		TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada",
				EnvioACasa.class);

		query.setParameter("idParada", idParada);

		List<EnvioACasa> envios = query.getResultList();

		em.close();

		return envios;
	}
}
