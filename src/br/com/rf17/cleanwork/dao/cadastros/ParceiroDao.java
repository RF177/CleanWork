package br.com.rf17.cleanwork.dao.cadastros;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class ParceiroDao extends DaoCrud<Long, Parceiro> {

	public void save(Parceiro parceiro) throws Exception {
		saveCrud(parceiro, parceiro.getId_parceiro());
	}

	public void delete(Parceiro parceiro) throws Exception {
		deleteCrud(parceiro);
	}

	public Parceiro getById(Long pk) throws Exception {
		return getByIdCrud(Parceiro.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<Parceiro> listAll(String filtro, int tipo) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(Parceiro.class);
					
		if(tipo == 1){//Cliente
			criteria.add(Restrictions.eq("cliente", true));
		}else if(tipo == 2){//Forncedor
			criteria.add(Restrictions.eq("fornecedor", true));
		}	
		
		if(StringFunctions.isInteger(filtro)){//Pesquisa pelo id/codigo
			criteria.add(Restrictions.eq("id_parceiro", Long.parseLong(filtro)));
		}else{//Pesquisa pelo nome
			criteria.add(HibernateUtil.stringRestrictionsCriteriaSd("nome", filtro, true));
		}
				
		return (List<Parceiro>) criteria.list();  			
	}

}
