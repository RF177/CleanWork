package br.com.rf17.cleanwork.bean.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.rf17.cleanwork.dao.estoque.EstoqueDao;
import br.com.rf17.cleanwork.model.estoque.Estoque;

@ManagedBean
@ViewScoped
public class EstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Estoque> estoques = new ArrayList<Estoque>();

	private EstoqueDao estoqueDao = new EstoqueDao();

	private String filtro = "";

	public EstoqueBean() {
		atualizar();
	}

	public List<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
		atualizar();
	}

	public void atualizar() {
		try {
			estoques = estoqueDao.listAll(filtro);
			RequestContext.getCurrentInstance().update("form_table_estoque");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}

}
