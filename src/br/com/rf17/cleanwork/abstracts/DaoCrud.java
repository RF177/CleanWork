package br.com.rf17.cleanwork.abstracts;

import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public abstract class DaoCrud<PK, E> {

	public void saveCrud(E entity, PK pk) throws Exception {
		try {
			HibernateUtil.verificaConexao();
			HibernateUtil.getEntityManager().getTransaction().begin();
			if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
				if(pk == null){//Novo
					HibernateUtil.getEntityManager().persist(entity);
				}else{//Update
					HibernateUtil.getEntityManager().merge(entity);
				}
				
				HibernateUtil.getEntityManager().getTransaction().commit();
			}
		} catch (Exception e) {
			HibernateUtil.getEntityManager().getTransaction().rollback();
			throw e;
		}
	}
	
	public void deleteCrud(E entity) throws Exception {
		try {
			HibernateUtil.verificaConexao();
			HibernateUtil.getEntityManager().getTransaction().begin();
			if (HibernateUtil.getEntityManager().getTransaction().isActive()) {				
				HibernateUtil.getEntityManager().remove(HibernateUtil.getEntityManager().merge(entity));
				HibernateUtil.getEntityManager().getTransaction().commit();
			}
		} catch (Exception e) {
			HibernateUtil.getEntityManager().getTransaction().rollback();
			throw e;			
		} 
	}

	@SuppressWarnings("unchecked")
	public E getByIdCrud(Class<?> clazz, PK pk) throws Exception {
		HibernateUtil.verificaConexao();
		return (E) HibernateUtil.getEntityManager().find(clazz, pk);
	}
	
}
