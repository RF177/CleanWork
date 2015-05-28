package br.com.rf17.cleanwork.bean.vendas.relatorios;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTable;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTableDados;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;
import br.com.rf17.cleanwork.utils.DataUtils;

@ManagedBean
@ViewScoped
public class RelVendasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	//private ParceiroDao parceiroDao = new ParceiroDao();

	// Variaveis para pesquisa generica
	private PesquisaGenericaTable pesquisaGenericaTable;// os dados da pesquisa
	private List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados;// o filtro da pesquisa
	private PesquisaGenericaTableDados selectedPesquisaGenericaDados;// o item selecionado
	//private String tipoPesquisa;

	// Filtros
	private Parceiro cliente;
	private Date dt1;
	private Date dt2;

	public RelVendasBean() {
		dt1 = DataUtils.primeiroDiaMes(null);
		dt2 = DataUtils.ultimoDiaMes(null);
	}

	public PesquisaGenericaTable getPesquisaGenericaTable() {
		return pesquisaGenericaTable;
	}

	public void setPesquisaGenericaTable(PesquisaGenericaTable pesquisaGenericaTable) {
		this.pesquisaGenericaTable = pesquisaGenericaTable;
	}

	public List<PesquisaGenericaTableDados> getFilteredpesquisaGenericaDados() {
		return filteredpesquisaGenericaDados;
	}

	public void setFilteredpesquisaGenericaDados(List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados) {
		this.filteredpesquisaGenericaDados = filteredpesquisaGenericaDados;
	}

	public PesquisaGenericaTableDados getSelectedPesquisaGenericaDados() {
		return selectedPesquisaGenericaDados;
	}

	public void setSelectedPesquisaGenericaDados(PesquisaGenericaTableDados selectedPesquisaGenericaDados) {
		this.selectedPesquisaGenericaDados = selectedPesquisaGenericaDados;
	}

	public Parceiro getCliente() {
		return cliente;
	}

	public void setCliente(Parceiro cliente) {
		this.cliente = cliente;
	}

	public Date getDt1() {
		return dt1;
	}

	public void setDt1(Date dt1) {
		this.dt1 = dt1;
	}

	public Date getDt2() {
		return dt2;
	}

	public void setDt2(Date dt2) {
		this.dt2 = dt2;
	}

	public void imprimir() {
		try{                                         
        	//parametros
            Map<String,Object> parameters = new HashMap<String, Object>();
            parameters.put("dt1", dt1);    
        	parameters.put("dt2", dt2);   
        	                	
        	String filtroSql = "";
        	
        	parameters.put("filtroSql", filtroSql);
     
        	//String baseDirImg = Definicoes.diretorioContext+"/img/";
        	//parameters.put("basedirimg", baseDirImg);
        	
        	//parameters.put("id_venda", selectedVenda.getId_venda());
        	            	
            FacesContext ctx = FacesContext.getCurrentInstance();            
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);                         
     
            session.setAttribute("relatorio", "RelVendas.jasper");
            session.setAttribute("parameters", parameters);  
            
            //redireciona para o servlet do relatorio     
            ExternalContext ec = ctx.getExternalContext();            
            try {            	
                ec.redirect(ec.getRequestContextPath() + "/Report");                
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
            }                    
            
         }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));   
         }
	}
	
}
