package br.com.rf17.cleanwork.bean.compras.relatorios;

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
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTable;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTableDados;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaUtils;
import br.com.rf17.cleanwork.dao.cadastros.ParceiroDao;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;
import br.com.rf17.cleanwork.utils.DataUtils;

@ManagedBean
@ViewScoped
public class RelComprasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ParceiroDao parceiroDao = new ParceiroDao();

	// Variaveis para pesquisa generica
	private PesquisaGenericaTable pesquisaGenericaTable;// os dados da pesquisa
	private List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados;// o filtro da pesquisa
	private PesquisaGenericaTableDados selectedPesquisaGenericaDados;// o item selecionado
	private String tipoPesquisa;

	// Filtros
	private Parceiro fornecedor;
	private Date dt1;
	private Date dt2;

	public RelComprasBean() {
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

	public Parceiro getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Parceiro fornecedor) {
		this.fornecedor = fornecedor;
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
        	
	    	StringBuilder filtroSql = new StringBuilder();
	    	
	    	if(fornecedor != null){
	    		filtroSql.append(" AND a.id_fornecedor = " + fornecedor.getId_parceiro());
	    	}
	    	
	    	parameters.put("filtroSql", filtroSql.toString());
	        	         	
            FacesContext ctx = FacesContext.getCurrentInstance();            
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);                         
     
            session.setAttribute("relatorio", "RelCompras.jasper");
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
	public List<Parceiro> completeFornecedor(String query) {
		try {
			return parceiroDao.listAll(query, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// ## PESQUISA ##
	public void openPesquisaGenerica(ActionEvent event) {
		try {
			tipoPesquisa = (String) event.getComponent().getAttributes().get("parameter");
			pesquisaGenericaTable = new PesquisaGenericaUtils().formaTabela(tipoPesquisa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pesquisaGenericaSelect() {
		try {
			if (tipoPesquisa.equals("fornecedor")) {
				fornecedor = parceiroDao.getById(selectedPesquisaGenericaDados.getId_registro());
				RequestContext.getCurrentInstance().update("form_data_relCompras:dados_fornecedor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pesquisaGenericaTable = null;
	}
	// ## PESQUISA ##
	
}
