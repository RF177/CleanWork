package br.com.rf17.cleanwork.utils.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.utils.StringFunctions;

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
			throw new Exception("Conex„o n„o encontrada!");
		}
	}
	
	/**
	 * Filtra utilizando LIKE desconsiderando acentos e case 
	 * uso:  .add(HibernateUtil.stringRestrictionsCriteriaSd("descricao", descricao))
	 * 
	 * @param coluna
	 * @param parametro
	 * @return Criterion Restrictions.sqlRestriction
	 */
	public static Criterion stringRestrictionsCriteriaSd(String coluna, String parametro, boolean first){				  				                                                                       
		return Restrictions.sqlRestriction(stringRestrictionsSQLSd("{alias}."+coluna, parametro, first));
	}
	public static Criterion stringRestrictionsCriteriaSd(String coluna, String parametro){
		return stringRestrictionsCriteriaSd(coluna, parametro, false);
	}
	
	/**
	 * Filtra utilizando LIKE desconsiderando acentos e case
	 * http://www.guj.com.br/java/212706-accent-insensitive-hibernate
	 * 
	 * uso:  stringRestrictionsSQLSd("coluna", "parametro")
	 * 
	 * @param coluna
	 * @param parametro
	 * @return String
	 */	
	public static String stringRestrictionsSQLSd(String coluna, String parametro, boolean first){		
		parametro = StringFunctions.clearAccents(parametro).toUpperCase();
		//parametro = Normalizer.normalize(parametro, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
		return "upper(TRANSLATE("+coluna+",'·ÈÌÛ˙‡ËÏÚ˘„ı‚ÍÓÙÙ‰ÎÔˆ¸Á¡…Õ”⁄¿»Ã“Ÿ√’¬ Œ‘€ƒÀœ÷‹«', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')) LIKE  '"+(first ? "%" : "") + parametro + "%'";		                                                                       		
	}
	
	public static String stringRestrictionsSQLSd(String coluna, String parametro){
		return stringRestrictionsSQLSd(coluna, parametro, false);
	}
	
}
