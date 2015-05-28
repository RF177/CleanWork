package br.com.rf17.cleanwork.dao.cadastros;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class ProdutoDao extends DaoCrud<Long, Produto> {

	public void save(Produto produto) throws Exception {
		saveCrud(produto, produto.getId_produto());
	}

	public void delete(Produto produto) throws Exception {
		deleteCrud(produto);
	}

	public Produto getById(Long pk) throws Exception {
		return getByIdCrud(Produto.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listAll(String filtro, int tipo) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
								
		Criteria criteria = session.createCriteria(Produto.class);
					
		if(tipo == 1){//Produto final
			criteria.add(Restrictions.eq("tipo", 1));
		}else if(tipo == 2){//Materia prima
			criteria.add(Restrictions.eq("tipo", 2));
		}	
		
		if(StringFunctions.isInteger(filtro)){//Pesquisa pelo id/codigo
			criteria.add(Restrictions.eq("id_produto", Long.parseLong(filtro)));
		}else{//Pesquisa pela descricao
			criteria.add(Restrictions.like("descricao", "%"+filtro+"%"));
		}
				
		return (List<Produto>) criteria.list();  			
	}

}
