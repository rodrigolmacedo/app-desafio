package desafio.config;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory factory;
	
	public EntityManagerProducer() {
		factory = Persistence.createEntityManagerFactory("desafio");
	}
	
	@RequestScoped
	@Produces
	public EntityManager createMinerEntityManager() {
		return factory.createEntityManager();
	}

	public void closeEntityManager(@Disposes EntityManager manager) {
		if (manager.isOpen()) {
			manager.close();
		}
	}

}
