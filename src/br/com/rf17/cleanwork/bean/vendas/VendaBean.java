package br.com.rf17.cleanwork.bean.vendas;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.SelectEvent;

import br.com.rf17.cleanwork.bean.InterfaceBean;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTable;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaTableDados;
import br.com.rf17.cleanwork.bean.pesquisagenerica.PesquisaGenericaUtils;
import br.com.rf17.cleanwork.dao.cadastros.ParceiroDao;
import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.dao.vendas.VendaDao;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.model.vendas.Venda;
import br.com.rf17.cleanwork.model.vendas.VendaItem;
import br.com.rf17.cleanwork.service.vendas.VendasService;

@ManagedBean
@ViewScoped
public class VendaBean implements InterfaceBean, Serializable {

	private static final long serialVersionUID = 1L;

	private Venda selectedVenda;
	private VendaItem selectedItem;
	private List<Venda> vendas = new ArrayList<Venda>();

	private VendaDao vendaDao = new VendaDao();
	private ProdutoDao produtoDao = new ProdutoDao();
	private ParceiroDao parceiroDao = new ParceiroDao();

	private String filtro = "";

	// Variaveis para pesquisa generica
	private PesquisaGenericaTable pesquisaGenericaTable;// os dados da pesquisa
	private List<PesquisaGenericaTableDados> filteredpesquisaGenericaDados;// o filtro da pesquisa
	private PesquisaGenericaTableDados selectedPesquisaGenericaDados;// o item selecionado
	private String tipoPesquisa;

	public VendaBean() {
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

	public Venda getSelectedVenda() {
		return selectedVenda;
	}

	public void setSelectedVenda(Venda selectedVenda) {
		this.selectedVenda = selectedVenda;
	}

	public VendaItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(VendaItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	@Override
	public void onRowSelect(SelectEvent event) {
		try {
			RequestContext.getCurrentInstance().reset("form_data_venda");// reseta o formulario
			selectedVenda = vendaDao.getById(selectedVenda.getId_venda());
			vendas = null;

			RequestContext.getCurrentInstance().update("form_table_venda");
			RequestContext.getCurrentInstance().update("form_data_venda");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void novo() {
		selectedVenda = new Venda();

		vendas = null;
		RequestContext.getCurrentInstance().update("form_table_venda");
	}

	@Override
	public void salvar(boolean voltar) {
		try {
			vendaDao.save(selectedVenda);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "A venda " + selectedVenda.getId_venda() + " foi salva com sucesso!"));

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
			vendaDao.delete(selectedVenda);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído!", "A venda " + selectedVenda.getId_venda() + " foi excluída com sucesso!"));
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar() {
		try {
			selectedVenda = null;
			vendas = vendaDao.listAll(filtro, 0);//FIXME
			RequestContext.getCurrentInstance().update("form_table_venda");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void emitir() {
		try {
			selectedVenda.setData_emissao(new Date());
			selectedVenda.setSituacao(2);// Passa para emitido
			vendaDao.save(selectedVenda);
			
			RequestContext.getCurrentInstance().execute("executaimprimir();");	
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Emitido!", "A venda " + selectedVenda.getId_venda() + " foi emitida e impressa com sucesso!"));

			//atualizar();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void cancelar() {
		try {
			selectedVenda.setSituacao(3);// Passa para cancelado
			vendaDao.save(selectedVenda);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Emtiido!", "A venda " + selectedVenda.getId_venda() + " foi cancelada com sucesso!"));
			
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void novoItem() {
		if (selectedVenda.getVendaItems() == null) {
			selectedVenda.setVendaItems(new ArrayList<VendaItem>());
		}

		selectedItem = new VendaItem();
		selectedItem.setVenda(selectedVenda);
	}

	public void salvaItem() {
		if (!selectedVenda.getVendaItems().contains(selectedItem)) {
			selectedVenda.getVendaItems().add(selectedItem);
		}

		calculaVenda();

		RequestContext.getCurrentInstance().update("form_data_venda");
	}

	public void excluiItem() {
		selectedVenda.getVendaItems().remove(selectedItem);
		calculaVenda();
	}

	public void imprimir() {
		try{                                         
        	//parametros
            Map<String,Object> parameters = new HashMap<String, Object>();
            
        	parameters.put("id_venda", selectedVenda.getId_venda());
        	            	
            FacesContext ctx = FacesContext.getCurrentInstance();            
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);                         
     
            session.setAttribute("relatorio", "venda.jasper");
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

	public List<Produto> completeProduto(String query) {
		try {
			return produtoDao.listAll(query, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void handleSelectProduto(SelectEvent event) throws Exception {
		selectedItem.setVl_unitario(selectedItem.getProduto().getPreco_venda());
	}

	public List<Parceiro> completeCliente(String query) {
		try {
			return parceiroDao.listAll(query, 1);
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
			if (tipoPesquisa.equals("produtofinal")) {
				selectedItem.setProduto(produtoDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				selectedItem.setVl_unitario(selectedItem.getProduto().getPreco_venda());
				RequestContext.getCurrentInstance().update("form_data_item:dados_produto");
				RequestContext.getCurrentInstance().update("form_data_item:valores");
			} else if (tipoPesquisa.equals("cliente")) {
				selectedVenda.setCliente(parceiroDao.getById(selectedPesquisaGenericaDados.getId_registro()));
				RequestContext.getCurrentInstance().update("form_data_venda:dados_cliente");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pesquisaGenericaTable = null;
	}
	// ## PESQUISA ##

	public void calculaItem() {
		VendasService.calculaItem(selectedItem);
	}

	public void calculaVenda() {
		VendasService.calculaVenda(selectedVenda);
	}

}
