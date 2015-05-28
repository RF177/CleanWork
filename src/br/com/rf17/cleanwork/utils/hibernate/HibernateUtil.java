package br.com.rf17.cleanwork.utils.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class HibernateUtil {
	
	public static final String PERSISTENCE_PATH = "cleanwork";
	
	private static EntityManager entityManager;
	
	public HibernateUtil(String persistencePath) throws Exception {
		if (entityManager == null) {
			EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory(persistencePath);
			entityManager = entityFactory.createEntityManager();
		}		
	}
	
	public static EntityManager getEntityManager() throws Exception {
		if (entityManager == null) {
			new HibernateUtil(PERSISTENCE_PATH);			
		}			
		return entityManager;
	}

	public static void setEntityManager(EntityManager entityManager) {
		HibernateUtil.entityManager = entityManager;
	}

	public Session getHibernateSession() throws Exception {		
		if (getEntityManager() == null) {
			return null;
		}
		return (Session) getEntityManager().getDelegate(); 		
	}

	public static void verificaConexao() throws Exception{
		if (getEntityManager() == null) {
			throw new Exception("Conexão não encontrada!");
		}
	}
	
}
