package br.com.rf17.cleanwork.bean.pesquisagenerica;

import java.util.ArrayList;
import java.util.List;

import br.com.rf17.cleanwork.dao.cadastros.ParceiroDao;
import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.utils.StringFunctions;

public class PesquisaGenericaUtils {

	public PesquisaGenericaTable formaTabela(String tipoPesquisa) throws Exception{
		
		String HeaderTexto = "Pesquisa ";
    	List<ColumnModel> pesquisaGenericacolumns = new ArrayList<ColumnModel>(); 
       	List<PesquisaGenericaTableDados> pesquisaGenericaDados = new ArrayList<PesquisaGenericaTableDados>();
       	       	       
       	if(tipoPesquisa.equals("produtoinsumo")){	     	
	        
    		HeaderTexto += "Produtos Matéria-Prima";
    		
	     	pesquisaGenericacolumns.add(new ColumnModel("Código", "data1",120));
	     	pesquisaGenericacolumns.add(new ColumnModel("Descrição", "data2",400));
	     	pesquisaGenericacolumns.add(new ColumnModel("Último Custo", "data3",120)); 
	     	
	     	List<Produto> produtos = new ProdutoDao().listAll("", 2);
	     	String precoFormatado = "";
	     	
        	for (Produto p : produtos) {    		
    			precoFormatado = StringFunctions.formataDouble(p.getPreco_custo(), 2); //Formata o preço    		    				
				pesquisaGenericaDados.add(new PesquisaGenericaTableDados(p.getId_produto(), p.getId_produto()+"", p.getDescricao(), precoFormatado,null,null,null));
			}        		
        	
    	}else if(tipoPesquisa.equals("produtofinal")){	     	
	        
    		HeaderTexto += "Produtos para Venda";
    		
	     	pesquisaGenericacolumns.add(new ColumnModel("Código", "data1",120));
	     	pesquisaGenericacolumns.add(new ColumnModel("Descrição", "data2",400));
	     	pesquisaGenericacolumns.add(new ColumnModel("Preço", "data3",120)); 
	     	
	     	List<Produto> produtos = new ProdutoDao().listAll("", 1);
	     	String precoFormatado = "";
	     	
        	for (Produto p : produtos) {    		
        		precoFormatado = StringFunctions.formataDouble(p.getPreco_venda(), 2); //Formata o preço    		    				
				pesquisaGenericaDados.add(new PesquisaGenericaTableDados(p.getId_produto(), p.getId_produto()+"", p.getDescricao(), precoFormatado,null,null,null));
			}        		
        	
    	}else if(tipoPesquisa.equals("fornecedor")){	     	
	        
    		HeaderTexto += "Fornecedor";
    		
	     	pesquisaGenericacolumns.add(new ColumnModel("Código", "data1",120));
	     	pesquisaGenericacolumns.add(new ColumnModel("Nome", "data2",400));
	     	pesquisaGenericacolumns.add(new ColumnModel("CPF / CNPJ", "data3",150)); 
	     	
	     	List<Parceiro> fornecedores = new ParceiroDao().listAll("", 2);
	     	
        	for (Parceiro p : fornecedores) {  
       			pesquisaGenericaDados.add(new PesquisaGenericaTableDados(p.getId_parceiro(), p.getId_parceiro()+"", p.getNome(), p.getInscricao(), null,null,null));
			}        		
        	
    	}else if(tipoPesquisa.equals("cliente")){	     	
	        
    		HeaderTexto += "Cliente";
    		
	     	pesquisaGenericacolumns.add(new ColumnModel("Código", "data1",120));
	     	pesquisaGenericacolumns.add(new ColumnModel("Nome", "data2",400));
	     	pesquisaGenericacolumns.add(new ColumnModel("CPF / CNPJ", "data3",150)); 
	     	
	     	List<Parceiro> fornecedores = new ParceiroDao().listAll("", 1);
	     	
        	for (Parceiro p : fornecedores) {  
        		pesquisaGenericaDados.add(new PesquisaGenericaTableDados(p.getId_parceiro(), p.getId_parceiro()+"", p.getNome(), p.getInscricao(), null,null,null));
			}        		
        	
    	}
       	
       	return  new PesquisaGenericaTable(HeaderTexto, pesquisaGenericacolumns, pesquisaGenericaDados);		
	}	
}
