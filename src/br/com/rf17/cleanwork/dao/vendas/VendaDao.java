package br.com.rf17.cleanwork.dao.vendas;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.model.vendas.Venda;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class VendaDao extends DaoCrud<Long, Venda> {

	public void save(Venda venda) throws Exception {
		saveCrud(venda, venda.getId_venda());
	}

	public void delete(Venda venda) throws Exception {
		deleteCrud(venda);
	}

	public Venda getById(Long pk) throws Exception {
		return getByIdCrud(Venda.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<Venda> listAll(String filtro, int situacao) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(Venda.class);
		
		if(situacao != 0){
			criteria.add(Restrictions.eq("situacao", situacao));
		}
		
		if(StringFunctions.isInteger(filtro)){//Pesquisa pelo numero
			criteria.add(Restrictions.eq("id_venda", Long.parseLong(filtro)));
		}else{//Pesquisa pelo nome do cliente
			criteria.createAlias("cliente", "cliente_alias");
			criteria.add(Restrictions.like("cliente_alias.nome", "%"+filtro+"%"));
		}
		
		criteria.addOrder(Order.desc("id_venda"));
				
		return (List<Venda>) criteria.list();  			
	}

}
