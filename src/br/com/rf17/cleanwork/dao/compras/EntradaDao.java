package br.com.rf17.cleanwork.dao.compras;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.model.compras.Entrada;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class EntradaDao extends DaoCrud<Long, Entrada> {

	public void save(Entrada entrada) throws Exception {
		saveCrud(entrada, entrada.getId_entrada());
	}

	public void delete(Entrada entrada) throws Exception {
		deleteCrud(entrada);
	}

	public Entrada getById(Long pk) throws Exception {
		return getByIdCrud(Entrada.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<Entrada> listAll(String filtro) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(Entrada.class);
		
		if(StringFunctions.isInteger(filtro)){//Pesquisa pelo numero
			criteria.add(Restrictions.eq("numero", Integer.parseInt(filtro)));
		}else{//Pesquisa pelo nome
			criteria.createAlias("fornecedor", "fornecedor_alias");
			criteria.add(Restrictions.like("fornecedor_alias.nome", "%"+filtro+"%"));
		}
				
		return (List<Entrada>) criteria.list();  			
	}

}
