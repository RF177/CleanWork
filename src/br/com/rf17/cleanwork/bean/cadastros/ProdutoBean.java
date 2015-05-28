package br.com.rf17.cleanwork.bean.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.rf17.cleanwork.bean.InterfaceBean;
import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.model.cadastro.Produto;
import br.com.rf17.cleanwork.service.cadastro.ProdutoService;

@ManagedBean
@ViewScoped
public class ProdutoBean implements InterfaceBean, Serializable {

	private static final long serialVersionUID = 1L;

	private Produto selectedProduto;
	private List<Produto> produtos = new ArrayList<Produto>();

	private ProdutoDao produtoDao = new ProdutoDao();

	private String filtro = "";

	// Sugestao preco de venda
	private double sugestao_preco_venda;

	public ProdutoBean() {
		atualizar();
	}

	public double getSugestao_preco_venda() {
		return sugestao_preco_venda;
	}

	public void setSugestao_preco_venda(double sugestao_preco_venda) {
		this.sugestao_preco_venda = sugestao_preco_venda;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
		atualizar();
	}

	public Produto getSelectedProduto() {
		return selectedProduto;
	}

	public void setSelectedProduto(Produto selectedProduto) {
		this.selectedProduto = selectedProduto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public void onRowSelect(SelectEvent event) {
		try {
			RequestContext.getCurrentInstance().reset("form_data_produto");// reseta o formulario
			selectedProduto = produtoDao.getById(selectedProduto.getId_produto());
			produtos = null;

			calculaSugestaoPrecoVenda();
			
			RequestContext.getCurrentInstance().update("form_table_produto");
			RequestContext.getCurrentInstance().update("form_data_produto");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void novo() {
		selectedProduto = new Produto();

		produtos = null;
		RequestContext.getCurrentInstance().update("form_table_produto");
	}

	@Override
	public void salvar(boolean voltar) {
		try {
			if (selectedProduto.getPreco_venda() <= 0.0 && selectedProduto.getTipo() == 1) {
				throw new Exception("Preço de venda do Produto Final deve ser maior que R$ 0,00!");
			} else {

				produtoDao.save(selectedProduto);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "O produto " + selectedProduto.getId_produto() + " foi salvo com sucesso!"));

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
			produtoDao.delete(selectedProduto);
			FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído!", "O produto " + selectedProduto.getId_produto() + " foi excluído com sucesso!"));
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar() {
		try {
			selectedProduto = null;
			produtos = produtoDao.listAll(filtro, 0);
			RequestContext.getCurrentInstance().update("form_table_produto");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void calculaSugestaoPrecoVenda() {
		sugestao_preco_venda = ProdutoService.calculaSugestaoPrecoVenda(selectedProduto);
	}

}
