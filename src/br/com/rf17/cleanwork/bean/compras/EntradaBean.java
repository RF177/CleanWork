package br.com.rf17.cleanwork.bean.compras;

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
import br.com.rf17.cleanwork.dao.cadastros.ParceiroDao;
import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.dao.compras.EntradaDao;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.model.compras.Entrada;
import br.com.rf17.cleanwork.model.compras.EntradaItem;
import br.com.rf17.cleanwork.service.compras.ComprasService;

@ManagedBean
@ViewScoped
public class EntradaBean implements InterfaceBean, Serializable {

	private static final long serialVersionUID = 1L;

	private Entrada selectedEntrada;
	private EntradaItem selectedItem;
	private List<Entrada> entradas = new ArrayList<Entrada>();

	private EntradaDao entradaDao = new EntradaDao();
	private ProdutoDao produtoDao = new ProdutoDao();
	private ParceiroDao parceiroDao = new ParceiroDao();

	private String filtro = "";

	// Variaveis para pesquisa generica
	private PesquisaGenericaTable pesquisaGenericaTable;// os dados da pesquisa
	private List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados;// o filtro da pesquisa
	private PesquisaGenericaTableDados selectedPesquisaGenericaDados;// o item selecionado
	private String tipoPesquisa;

	public EntradaBean() {
		atualizar();
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

	public Entrada getSelectedEntrada() {
		return selectedEntrada;
	}

	public void setSelectedEntrada(Entrada selectedEntrada) {
		this.selectedEntrada = selectedEntrada;
	}

	public EntradaItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(EntradaItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}

	@Override
	public void onRowSelect(SelectEvent event) {
		try {
			RequestContext.getCurrentInstance().reset("form_data_entrada");// reseta o formulario
			selectedEntrada = entradaDao.getById(selectedEntrada.getId_entrada());
			entradas = null;

			RequestContext.getCurrentInstance().update("form_table_entrada");
			RequestContext.getCurrentInstance().update("form_data_entrada");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void novo() {
		selectedEntrada = new Entrada();
		selectedEntrada.setData_entrada(new Date());

		entradas = null;
		RequestContext.getCurrentInstance().update("form_table_entrada");
	}

	@Override
	public void salvar(boolean voltar) {
		try {

			if(selectedEntrada.getData_entrada() == null){
				throw new Exception("Data de entrada não foi informada!");
			}
			
			calculaEntrada();

			entradaDao.save(selectedEntrada);

			// Atualiza preços de custo
			for (EntradaItem item : selectedEntrada.getEntradaItems()) {
				item.getProduto().setPreco_custo(item.getVl_unitario());
				produtoDao.save(item.getProduto());
			}

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "A entrada " + selectedEntrada.getId_entrada()
							+ " foi salva com sucesso e todos os produtos da entrada tiveram seus custos atualizados!"));

			if (voltar) {
				atualizar();
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void excluir() {
		try {
			entradaDao.delete(selectedEntrada);
			FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído!", "A entrada " + selectedEntrada.getId_entrada() + " foi excluído com sucesso!"));
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar() {
		try {
			selectedEntrada = null;
			entradas = entradaDao.listAll(filtro);
			RequestContext.getCurrentInstance().update("form_table_entrada");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void novoItem() {
		if (selectedEntrada.getEntradaItems() == null) {
			selectedEntrada.setEntradaItems(new ArrayList<EntradaItem>());
		}

		selectedItem = new EntradaItem();
		selectedItem.setEntrada(selectedEntrada);
	}

	public void salvaItem() {
		if (!selectedEntrada.getEntradaItems().contains(selectedItem)) {
			selectedEntrada.getEntradaItems().add(selectedItem);
		}

		calculaEntrada();
		
		RequestContext.getCurrentInstance().execute("PF('item_dialog').hide()");
		RequestContext.getCurrentInstance().update("form_data_entrada");
	}

	public void excluiItem() {
		selectedEntrada.getEntradaItems().remove(selectedItem);
		calculaEntrada();
	}

	public List<Produto> completeProduto(String query) {
		try {
			return produtoDao.listAll(query, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			if (tipoPesquisa.equals("produtoinsumo")) {
				selectedItem.setProduto(produtoDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				RequestContext.getCurrentInstance().update("form_data_item:dados_produto_insumo");
			} else if (tipoPesquisa.equals("fornecedor")) {
				selectedEntrada.setFornecedor(parceiroDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				RequestContext.getCurrentInstance().update("form_data_entrada:dados_fornecedor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pesquisaGenericaTable = null;
	}

	// ## PESQUISA ##

	public void calculaItem() {
		ComprasService.calculaItem(selectedItem);
	}

	public void calculaEntrada() {
		ComprasService.calculaCompra(selectedEntrada);
	}

}
