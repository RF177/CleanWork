package br.com.rf17.cleanwork.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.rf17.cleanwork.model.dashboard.Dashboard_financeiro;
import br.com.rf17.cleanwork.model.dashboard.Dashboard_producao;
import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

public class DashboardDao {

	@SuppressWarnings("unchecked")
	public List<Dashboard_financeiro> buscaValorVendas() throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
		
		SQLQuery query = session.createSQLQuery("select 1 id, sum(valor_total) vl from vendas where situacao = 2");
		List<Object[]> records = query.list();
		
		List<Dashboard_financeiro> dashboards = new ArrayList<Dashboard_financeiro>();
        for (Object[] record  : records) {	 
        	Dashboard_financeiro dashboard_financeiro = new Dashboard_financeiro();
        	
        	dashboard_financeiro.setDescricao("Vendas");
        	dashboard_financeiro.setValor(Double.parseDouble(String.valueOf(record[1])));
        	
        	dashboards.add(dashboard_financeiro);	        	        	        
        }
                	     	       
		return dashboards;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Dashboard_financeiro> buscaValorCompras() throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
				
		SQLQuery query = session.createSQLQuery("select 1 id, sum(valor_total) vl from entradas");
		List<Object[]> records = query.list();
		
		List<Dashboard_financeiro> dashboards = new ArrayList<Dashboard_financeiro>();
        for (Object[] record  : records) {	 
        	Dashboard_financeiro dashboard_financeiro = new Dashboard_financeiro();
        	
        	dashboard_financeiro.setDescricao("Compras");
        	dashboard_financeiro.setValor(Double.parseDouble(String.valueOf(record[1])));
        	
        	dashboards.add(dashboard_financeiro);	        	        	        
        }        
        	     	       
		return dashboards;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Dashboard_financeiro> buscaValorVendasMes() throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
		
		SQLQuery query = session.createSQLQuery("select extract(month from data_emissao) mes, extract(year from data_emissao) ano, sum(valor_total) "
										      + "from vendas where situacao = 2 group by extract(month from data_emissao), extract(year from data_emissao)");
		List<Object[]> records = query.list();
		
		List<Dashboard_financeiro> dashboards = new ArrayList<Dashboard_financeiro>();
        for (Object[] record  : records) {	 
        	Dashboard_financeiro dashboard_financeiro = new Dashboard_financeiro();
        	
        	dashboard_financeiro.setDescricao("Vendas");
        	
        	DateFormat formatter = new SimpleDateFormat("MM/yyyy");  
        	Date date = (Date)formatter.parse(String.valueOf(record[0])+"/"+String.valueOf(record[1]));  
        	
        	dashboard_financeiro.setData(date);
        	dashboard_financeiro.setValor(Double.parseDouble(String.valueOf(record[2])));
        	
        	dashboards.add(dashboard_financeiro);	        	        	        
        }
                	     	       
		return dashboards;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Dashboard_financeiro> buscaValorComprasMes() throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
				
		SQLQuery query = session.createSQLQuery("select extract(month from data_entrada) mes, extract(year from data_entrada) ano, sum(valor_total)"
											 + " from entradas group by extract(month from data_entrada), extract(year from data_entrada)");
		List<Object[]> records = query.list();
		
		List<Dashboard_financeiro> dashboards = new ArrayList<Dashboard_financeiro>();
        for (Object[] record  : records) {	 
        	Dashboard_financeiro dashboard_financeiro = new Dashboard_financeiro();
        	
        	dashboard_financeiro.setDescricao("Compras");
        	
        	DateFormat formatter = new SimpleDateFormat("MM/yyyy");  
        	Date date = (Date)formatter.parse(String.valueOf(record[0])+"/"+String.valueOf(record[1]));  
        	
        	dashboard_financeiro.setData(date);
        	dashboard_financeiro.setValor(Double.parseDouble(String.valueOf(record[2])));
        	
        	dashboards.add(dashboard_financeiro);	        	        	        
        }        
        	     	       
		return dashboards;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Dashboard_producao> buscaProducao() throws Exception {
		Session session = (Session) HibernateUtil.getEntityManager().getDelegate();
				
		SQLQuery query = session.createSQLQuery("select data, sum(qtd), situacao from producao group by data, situacao order by data");
		List<Object[]> records = query.list();
		
		List<Dashboard_producao> dashboards = new ArrayList<Dashboard_producao>();
        for (Object[] record  : records) {	 
        	Dashboard_producao dashboard_producao = new Dashboard_producao();
        	
        	System.out.println(record);
        	//dashboard_producao.setData(data);
        	
        	dashboards.add(dashboard_producao);	        	        	        
        }        
        	     	       
		return dashboards;		
	}
	
}
