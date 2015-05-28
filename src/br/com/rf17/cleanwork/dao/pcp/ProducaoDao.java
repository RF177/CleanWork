package br.com.rf17.cleanwork.dao.pcp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.model.pcp.Producao;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class ProducaoDao extends DaoCrud<Long, Producao> {

	public void save(Producao producao) throws Exception {
		saveCrud(producao, producao.getId_producao());
	}

	public void delete(Producao producao) throws Exception {
		deleteCrud(producao);
	}

	public Producao getById(Long pk) throws Exception {
		return getByIdCrud(Producao.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<Producao> listAll(String filtro) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(Producao.class);
		
		if(StringFunctions.isInteger(filtro)){//Pesquisa pelo codigo da producao
			criteria.add(Restrictions.eq("id_producao", Long.parseLong(filtro)));
		}else{//Pesquisa pela descricao do produto
			criteria.createAlias("produto", "produto_alias");
			criteria.add(Restrictions.like("produto_alias.descricao", "%"+filtro+"%"));
		}
				
		return (List<Producao>) criteria.list();  			
	}

}
