package br.com.rf17.cleanwork.bean.pcp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.rf17.cleanwork.bean.InterfaceBean;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTable;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTableDados;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaUtils;
import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.dao.pcp.FichaTecnicaDao;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.model.pcp.FichaTecnica;
import br.com.rf17.cleanwork.model.pcp.FichaTecnicaInsumo;
import br.com.rf17.cleanwork.service.pcp.PcpService;

@ManagedBean
@ViewScoped
public class FichaTecnicaBean implements InterfaceBean, Serializable {

	private static final long serialVersionUID = 1L;

	private FichaTecnica selectedFichaTecnica;
	private FichaTecnicaInsumo selectedInsumo;
	private List<FichaTecnica> fichaTecnicas;

	private ProdutoDao produtoDao = new ProdutoDao();
	private FichaTecnicaDao fichaTecnicaDao = new FichaTecnicaDao();

	private String filtro = "";

	// Variaveis para pesquisa generica
	private PesquisaGenericaTable pesquisaGenericaTable;// os dados da pesquisa
	private List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados;// o filtro da pesquisa
	private PesquisaGenericaTableDados selectedPesquisaGenericaDados;// o item selecionado
	private String tipoPesquisa;
	
	//Custos indiretos
	private Date dt1 = new Date();
	private Date dt2 = new Date();
	private Double custos_indiretos;
	
	public FichaTecnicaBean() {
		atualizar();
	}
	
	
	public Double getCustos_indiretos() {
		return custos_indiretos;
	}

	public void setCustos_indiretos(Double custos_indiretos) {
		this.custos_indiretos = custos_indiretos;
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

	public List<FichaTecnica> getFichaTecnicas() {
		return fichaTecnicas;
	}

	public void setFichaTecnicas(List<FichaTecnica> fichaTecnicas) {
		this.fichaTecnicas = fichaTecnicas;
	}

	public FichaTecnica getSelectedFichaTecnica() {
		return selectedFichaTecnica;
	}

	public void setSelectedFichaTecnica(FichaTecnica selectedFichaTecnica) {
		this.selectedFichaTecnica = selectedFichaTecnica;
	}

	public FichaTecnicaInsumo getSelectedInsumo() {
		return selectedInsumo;
	}

	public void setSelectedInsumo(FichaTecnicaInsumo selectedInsumo) {
		this.selectedInsumo = selectedInsumo;
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

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
		atualizar();
	}

	@Override
	public void onRowSelect(SelectEvent event) {
		try {
			RequestContext.getCurrentInstance().reset("form_data_fichatecnica");// reseta o formulario
			selectedFichaTecnica = fichaTecnicaDao.getById(selectedFichaTecnica.getId_fichatecnica());
			fichaTecnicas = null;

			calculaCustoTotal();
			
			RequestContext.getCurrentInstance().update("form_table_fichatecnica");
			RequestContext.getCurrentInstance().update("form_data_fichatecnica");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void novo() {
		selectedFichaTecnica = new FichaTecnica();

		fichaTecnicas = null;
		RequestContext.getCurrentInstance().update("form_table_fichatecnica");
	}

	@Override
	public void salvar(boolean voltar) {
		try {
			if (selectedFichaTecnica.getCusto_total() <= 0.0) {
				throw new Exception("Preço de custo do deve ser maior que R$ 0,00!");
			} else {
				
				calculaCustoTotal();
				
				fichaTecnicaDao.save(selectedFichaTecnica);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "A ficha técnica " + selectedFichaTecnica.getId_fichatecnica() + " foi salva com sucesso!"));

				if (voltar) {
					atualizar();
				}

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void excluir() {
		try {
			fichaTecnicaDao.delete(selectedFichaTecnica);
			FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído!", "A ficha técnica " + selectedFichaTecnica.getId_fichatecnica() + " foi excluída com sucesso!"));
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar() {
		try {
			selectedFichaTecnica = null;
			fichaTecnicas = fichaTecnicaDao.listAll(filtro);
			RequestContext.getCurrentInstance().update("form_table_fichatecnica");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void novoInsumo() {
		if (selectedFichaTecnica.getInsumos() == null) {
			selectedFichaTecnica.setInsumos(new ArrayList<FichaTecnicaInsumo>());
		}

		selectedInsumo = new FichaTecnicaInsumo();
		selectedInsumo.setFichaTecnica(selectedFichaTecnica);
	}

	public void salvaInsumo() {
		if (!selectedFichaTecnica.getInsumos().contains(selectedInsumo)) {
			selectedFichaTecnica.getInsumos().add(selectedInsumo);
		}

		calculaCustoInsumo();
		calculaCustoTotal();

		RequestContext.getCurrentInstance().execute("PF('insumo_dialog').hide()");
		RequestContext.getCurrentInstance().update("form_data_fichatecnica");
	}

	public void excluiInsumo() {
		selectedFichaTecnica.getInsumos().remove(selectedInsumo);
		
		calculaCustoTotal();
		
		RequestContext.getCurrentInstance().update("form_data_fichatecnica");
	}
	
	public void lancaCustosIndiretos(){
		try{
			PcpService.lancaCustosIndiretos(dt1, dt2, custos_indiretos);
			RequestContext.getCurrentInstance().execute("PF('indireto_dialog').hide()");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	//## PRODUTO FINAL ##
	public List<Produto> completeProduto(String query) {
		try {
			return produtoDao.listAll(query, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void handleSelectProduto() {
		calculaCustoTotal();
	}
	//## PRODUTO FINAL ##
	
	//## INSUMO ##
	public List<Produto> completeProdutoInsumo(String query) {
		try {
			return produtoDao.listAll(query, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void handleSelectProdutoInsumo() {
		if(selectedInsumo.getProduto() != null){
			selectedInsumo.setVl_custo(selectedInsumo.getProduto().getPreco_custo());
		}
	}
	//## INSUMO ##

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
			if (tipoPesquisa.equals("produtoinsumo")) {
				selectedInsumo.setProduto(produtoDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				selectedInsumo.setVl_custo(selectedInsumo.getProduto().getPreco_custo());
				RequestContext.getCurrentInstance().update("form_data_insumo:dados_produto_insumo");
				RequestContext.getCurrentInstance().update("form_data_insumo:bloco_custo");
			} else if (tipoPesquisa.equals("produtofinal")) {
				selectedFichaTecnica.setProduto(produtoDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				calculaCustoTotal();
				RequestContext.getCurrentInstance().update("form_data_fichatecnica:dados_produto");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		pesquisaGenericaTable = null;
	}
	// ## PESQUISA ##

	public void calculaCustoInsumo() {
		selectedInsumo = PcpService.calculaCustoInsumo(selectedInsumo);
	}

	public void calculaCustoTotal() {
		selectedFichaTecnica = PcpService.calculaFichaTecnica(selectedFichaTecnica);
	}

}
