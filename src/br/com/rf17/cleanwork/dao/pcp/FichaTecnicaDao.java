package br.com.rf17.cleanwork.dao.pcp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.model.pcp.FichaTecnica;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class FichaTecnicaDao extends DaoCrud<Long, FichaTecnica> {

	public void save(FichaTecnica fichaTecnica) throws Exception {
		saveCrud(fichaTecnica, fichaTecnica.getId_fichatecnica());
	}

	public void delete(FichaTecnica fichaTecnica) throws Exception {
		deleteCrud(fichaTecnica);
	}

	public FichaTecnica getById(Long pk) throws Exception {
		return getByIdCrud(FichaTecnica.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<FichaTecnica> listAll(String filtro) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(FichaTecnica.class);
		
		if(StringFunctions.isInteger(filtro)){//Pesquisa pelo codigo da ficha
			criteria.add(Restrictions.eq("codigo", Integer.parseInt(filtro)));
		}else{//Pesquisa pela descricao da ficha
			criteria.add(HibernateUtil.stringRestrictionsCriteriaSd("descricao", filtro, true));
		}
				
		return (List<FichaTecnica>) criteria.list();  			
	}
	
	@SuppressWarnings("unchecked")
	public List<FichaTecnica> listAllByProduto(Produto produto) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(FichaTecnica.class);
		
		criteria.add(Restrictions.eq("produto", produto));
				
		return (List<FichaTecnica>) criteria.list();  			
	}

}
