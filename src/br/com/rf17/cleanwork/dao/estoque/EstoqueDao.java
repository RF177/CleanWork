package br.com.rf17.cleanwork.dao.estoque;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.model.estoque.Estoque;
import br.com.rf17.cleanwork.utils.StringFunctions;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class EstoqueDao {

	@SuppressWarnings("unchecked")
	public List<Estoque> listAll(String filtro) throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
				
		SQLQuery query;	
		List<Object[]> records = new ArrayList<Object[]>();//inicializa		
		
		String where = "";
		
		if(!filtro.isEmpty()){
			if(StringFunctions.isInteger(filtro)){//Pesquisa pelo id/codigo do produto
				where = "id_produto = "+filtro;
			}else{//Pesquisa pela descricao do produto
				where = "descricao like '%"+filtro+"%' " ;
			}
		}
		
		//Entrada Compras
		query = session.createSQLQuery("select a.id_produto, (nvl(sum(b.qtd), 0)) entrada_compra"
									+ " from produtos a "
									+ " left outer join CLEANWORK.entradaitem b ON b.id_produto = a.id_produto"
									+ (where.isEmpty() ? "" : " where a."+where)
									+ " group by a.id_produto ");
		records.addAll(query.list());
		
		
		//Entrada Producao
		query = session.createSQLQuery("select a.id_produto, nvl(sum(b.qtd), 0)"
									+ " from produtos a"
									+ " left outer join CLEANWORK.producao b ON b.id_produto = a.id_produto AND b.SITUACAO = 3 "
									+ (where.isEmpty() ? "" : " where a."+where)
									+ " group by a.id_produto ");
		records.addAll(query.list());
		
		
		//Saida Venda
		query = session.createSQLQuery("select b.id_produto, nvl(sum(b.qtd), 0) * -1 estoque"
									+ " from vendas a "
									+ " left outer join CLEANWORK.vendaitem b ON b.id_venda = a.id_venda"
									+ " left outer join CLEANWORK.produtos c ON c.id_produto = b.id_produto"
									+ " WHERE a.situacao = 2 "+(where.isEmpty() ? "" : " AND c."+where)
									+ " group by b.id_produto ");
		records.addAll(query.list());
	
		
		//Saida Producao
		query = session.createSQLQuery("select c.id_produto, sum(nvl(a.qtd, 0) * nvl(c.QTD, 0)) * -1 saida_producao"
									+ " from producao a"
									+ " left outer join FICHATECNICA b ON b.id_produto = a.id_produto AND b.ID_FICHATECNICA = A.ID_FICHATECNICA"
									+ " left outer join FICHATECNICA_INSUMO c ON c.ID_FICHATECNICA = b.ID_FICHATECNICA"
									+ " left outer join CLEANWORK.produtos d ON d.id_produto = c.id_produto"
									+ " where a.SITUACAO = 3 "+(where.isEmpty() ? "" : " AND d."+where)
									+ " group by c.id_produto ");
		records.addAll(query.list());
	

		ProdutoDao produtoDao = new ProdutoDao();
		List<Estoque> estoques = new ArrayList<Estoque>();
        for (Object[] record  : records) {	 
        	Estoque estoque = new Estoque();
        	
        	estoque.setProduto(produtoDao.getById(Long.parseLong(String.valueOf(record [0]))));
        	estoque.setQtd(Long.parseLong(String.valueOf(record [1])));
        	
        	estoques.add(estoque);	        	        	        
        }
        
        
        List<Estoque> ests = new ArrayList<Estoque>();
        List<Produto> produtos = new ArrayList<Produto>();
        for(Estoque estoque : estoques){
        	
        	if(!produtos.contains(estoque.getProduto())){
        		Estoque new_est = new Estoque();
        		new_est.setProduto(estoque.getProduto());
        		new_est.setQtd(estoque.getQtd());
        		
        		produtos.add(estoque.getProduto());
        		ests.add(new_est);
        	}else{
        		for(Estoque e : ests){
        			if(e.getProduto().getId_produto() == estoque.getProduto().getId_produto()){
        				e.setQtd(e.getQtd() + estoque.getQtd());
        			}
        		}
        	}
        	
        	
        }
        	     	       
		return ests;		
	}
}
