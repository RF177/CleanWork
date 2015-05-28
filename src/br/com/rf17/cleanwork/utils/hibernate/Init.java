package br.com.rf17.cleanwork.utils.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Init implements ServletContextListener {
	
	private HibernateUtil hibernateUtil;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			if (HibernateUtil.getEntityManager().isOpen()) {
				HibernateUtil.getEntityManager().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			if (hibernateUtil == null) { 
				hibernateUtil = new HibernateUtil(HibernateUtil.PERSISTENCE_PATH);
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	
}
