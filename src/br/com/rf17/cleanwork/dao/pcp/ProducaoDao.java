package br.com.rf17.cleanwork.dao.pcp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.rf17.cleanwork.abstracts.DaoCrud;
import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
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
		
		criteria.addOrder(Order.desc("id_producao"));
				
		return (List<Producao>) criteria.list();  			
	}
	
	@SuppressWarnings("unchecked")
	public Double getQtdProducaoTotalPeriodo(Date dt_ini, Date dt_fim) throws Exception {
		
		try {
			Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			StringBuilder query = new StringBuilder();
			query.append("select sum(qtd) qtd_producao");
			query.append("  from producao");
			query.append(" where data >= '"+df.format(dt_ini)+"' and data <= '"+df.format(dt_fim)+"' and situacao = 3 ");
			
			SQLQuery sqlQuery = session.createSQLQuery(query.toString());
			
			List<Object[]> records = new ArrayList<Object[]>();		
			records.addAll(sqlQuery.list());
			
			Double qtod_producaoTotalPeriodo = 0.0;		
			qtod_producaoTotalPeriodo += Double.parseDouble(String.valueOf(records.get(0) [0]));				        	        	       
			
			return qtod_producaoTotalPeriodo;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao consultar quantidade de produção total no período. (Motivo: "+e.getMessage()+")");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Producao> getQtdProducaoTotalPeriodoProduto(Date dt_ini, Date dt_fim) throws Exception {
		try{
			Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			StringBuilder query = new StringBuilder();
			query.append("select sum(qtd) qtd_producao_item, id_produto");
			query.append("  from producao");
			query.append(" where data >= '"+df.format(dt_ini)+"' and data <= '"+df.format(dt_fim)+"' and situacao = 3 ");
			query.append(" group by id_produto");
			
			SQLQuery sqlQuery = session.createSQLQuery(query.toString());
			
			List<Object[]> records = new ArrayList<Object[]>();		
			records.addAll(sqlQuery.list());
			
			List<Producao> producao_periodoProduto = new ArrayList<Producao>();	
			for (Object[] record  : records) {	 
				Producao producao = new Producao();
				producao.setQtd(Double.parseDouble(String.valueOf(record [0])));
				producao.setProduto(new ProdutoDao().getById(Long.parseLong(String.valueOf(record [1]))));
				
				producao_periodoProduto.add(producao);
			}
	
			return producao_periodoProduto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao consultar quantidade de produção total no período por produto. (Motivo: "+e.getMessage()+")");
		}
	}
	
}