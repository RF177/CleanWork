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
import br.com.rf17.cleanwork.dao.pcp.ProducaoDao;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.model.pcp.FichaTecnica;
import br.com.rf17.cleanwork.model.pcp.Producao;

@ManagedBean
@ViewScoped
public class ProducaoBean implements InterfaceBean, Serializable {

	private static final long serialVersionUID = 1L;

	private Producao selectedProducao;
	private List<Producao> producaos = new ArrayList<Producao>();

	private ProducaoDao producaoDao = new ProducaoDao();
	private ProdutoDao produtoDao = new ProdutoDao();
	private FichaTecnicaDao fichaTecnicaDao = new FichaTecnicaDao();

	private String filtro = "";

	private List<FichaTecnica> fichaTecnicas;

	// Variaveis para pesquisa generica
	private PesquisaGenericaTable pesquisaGenericaTable;// os dados da pesquisa
	private List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados;// o filtro da pesquisa
	private PesquisaGenericaTableDados selectedPesquisaGenericaDados;// o item selecionado
	private String tipoPesquisa;

	public ProducaoBean() {
		fichaTecnicas = new ArrayList<FichaTecnica>();
		atualizar();		
	}

	public List<FichaTecnica> getFichaTecnicas() {
		return fichaTecnicas;
	}

	public void setFichaTecnicas(List<FichaTecnica> fichaTecnicas) {
		this.fichaTecnicas = fichaTecnicas;
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

	public Producao getSelectedProducao() {
		return selectedProducao;
	}

	public void setSelectedProducao(Producao selectedProducao) {
		this.selectedProducao = selectedProducao;
	}

	public List<Producao> getProducaos() {
		return producaos;
	}

	public void setProducaos(List<Producao> producaos) {
		this.producaos = producaos;
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
			RequestContext.getCurrentInstance().reset("form_data_producao");// reseta o formulario
			selectedProducao = producaoDao.getById(selectedProducao.getId_producao());
			producaos = null;
			
			handleSelectProduto();

			RequestContext.getCurrentInstance().update("form_table_producao");
			RequestContext.getCurrentInstance().update("form_data_producao");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void novo() {
		selectedProducao = new Producao();
		selectedProducao.setData(new Date());

		producaos = null;
		RequestContext.getCurrentInstance().update("form_table_producao");
	}

	@Override
	public void salvar(boolean voltar) {
		try {
			if (selectedProducao.getQtd() <= 0.0) {
				throw new Exception("Quantidade deve ser maior que 0!");
			} else {

				producaoDao.save(selectedProducao);
				
				// Atualiza preço de custo do produto final				
				selectedProducao.getProduto().setPreco_custo(selectedProducao.getFichaTecnica().getCusto_total());
				produtoDao.save(selectedProducao.getProduto());				

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "A produção " + selectedProducao.getId_producao() + " foi salva com sucesso!"));

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
			producaoDao.delete(selectedProducao);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído!", "A produção " + selectedProducao.getId_producao() + " foi excluída com sucesso!"));
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar() {
		try {
			selectedProducao = null;
			producaos = producaoDao.listAll(filtro);
			RequestContext.getCurrentInstance().update("form_table_producao");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public List<Produto> completeProduto(String query) {
		try {
			return produtoDao.listAll(query, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void handleSelectProduto() {
		try {
			if(selectedProducao != null && selectedProducao.getProduto() != null){
				fichaTecnicas = fichaTecnicaDao.listAllByProduto(selectedProducao.getProduto());				
			}else{
				fichaTecnicas = new ArrayList<FichaTecnica>();
			}
			
			RequestContext.getCurrentInstance().update("form_data_producao:ficha_tecnica");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			if (tipoPesquisa.equals("produtofinal")) {
				selectedProducao.setProduto(produtoDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				RequestContext.getCurrentInstance().update("form_data_producao:dados_produto");
				handleSelectProduto();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pesquisaGenericaTable = null;
	}
	// ## PESQUISA ##

}
