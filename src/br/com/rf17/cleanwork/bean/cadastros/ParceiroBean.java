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
import br.com.rf17.cleanwork.dao.cadastros.ParceiroDao;
import br.com.rf17.cleanwork.model.cadastro.Contato;
import br.com.rf17.cleanwork.model.cadastro.Endereco;
import br.com.rf17.cleanwork.model.cadastro.Parceiro;

@ManagedBean
@ViewScoped
public class ParceiroBean implements InterfaceBean, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Parceiro selectedParceiro;
	private List<Parceiro> parceiros = new ArrayList<Parceiro>();
	
	private Contato selectedContato;
	private Endereco selectedEndereco;
	
	private ParceiroDao parceiroDao = new ParceiroDao();

	private String filtro = "";	
	
	public ParceiroBean() {
		atualizar();
	}

	
	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;		
		atualizar();				
	}
	
	public Parceiro getSelectedParceiro() {
		return selectedParceiro;
	}

	public void setSelectedParceiro(Parceiro selectedParceiro) {
		this.selectedParceiro = selectedParceiro;
	}

	public List<Parceiro> getParceiros() {
		return parceiros;
	}

	public void setParceiros(List<Parceiro> parceiros) {
		this.parceiros = parceiros;
	}
	
	public Contato getSelectedContato() {
		return selectedContato;
	}

	public void setSelectedContato(Contato selectedContato) {
		this.selectedContato = selectedContato;
	}

	public Endereco getSelectedEndereco() {
		return selectedEndereco;
	}

	public void setSelectedEndereco(Endereco selectedEndereco) {
		this.selectedEndereco = selectedEndereco;
	}

	@Override
	public void onRowSelect(SelectEvent event) {
		try {
			RequestContext.getCurrentInstance().reset("form_data_parceiro");// reseta o formulario
			selectedParceiro = parceiroDao.getById(selectedParceiro.getId_parceiro());		
			parceiros = null;
			
			RequestContext.getCurrentInstance().update("form_table_parceiro");			
			RequestContext.getCurrentInstance().update("form_data_parceiro");					
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro:",e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void novo() {
		selectedParceiro = new Parceiro();
		
		parceiros = null;
		RequestContext.getCurrentInstance().update("form_table_parceiro");
	}

	@Override
	public void salvar(boolean voltar) {
		try {
			if(!selectedParceiro.isCliente() && !selectedParceiro.isFornecedor()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Alerta", "Parceiro precisar ter pelo menos um tipo selecionado (Cliente e/ou Fornecedor)!"));				
			}else if(selectedParceiro.getEnderecoPrincipal() == null){ //verifica se tem endereco principal cadastrado
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Alerta", "Parceiro sem endereço principal cadastrado!"));
			}else if(selectedParceiro.getTelefonePrincipal() == null){ //verifica se tem telefone principal cadastrado
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Alerta", "Parceiro sem telefone principal cadastrado!"));
			}else{	
			
				parceiroDao.save(selectedParceiro);
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Salvo!", "O Parceiro "+ selectedParceiro.getNome() +" foi salvo com sucesso!"));
				
				if(voltar){
					atualizar();
				}
				
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro:",e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void excluir() {
		try {
			parceiroDao.delete(selectedParceiro);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Excluído!", "O Parceiro "+selectedParceiro.getNome()+" foi excluído com sucesso!"));			
			atualizar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro:",e.getMessage()));
			e.printStackTrace();
		}		
	}

	@Override
	public void atualizar() {
		try{
			selectedParceiro = null;
			parceiros = parceiroDao.listAll(filtro, 0);
			RequestContext.getCurrentInstance().update("form_table_parceiro");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro:",e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void novoContato(){
		if(selectedParceiro.getContatos() == null){
			selectedParceiro.setContatos(new ArrayList<Contato>());		
		}				
		
		selectedContato  = new Contato();
		selectedContato.setParceiro(selectedParceiro);		
	}
	
	public void salvaContato(){
		if(!selectedParceiro.getContatos().contains(selectedContato)){
			selectedParceiro.getContatos().add(selectedContato);
		}
		
		RequestContext.getCurrentInstance().execute("PF('contato_dialog').hide()");
		RequestContext.getCurrentInstance().update("form_data_parceiro");
	}
	
	public void excluiContato(){
		selectedParceiro.getContatos().remove(selectedContato);
	}
	
	
	public void novoEndereco(){				
		if(selectedParceiro.getEnderecos() == null){
			selectedParceiro.setEnderecos(new ArrayList<Endereco>());		
		}				
		
		selectedEndereco = new Endereco();
		selectedEndereco.setParceiro(selectedParceiro);									
	}
	
	public void salvaEndereco(){
		if(!selectedParceiro.getEnderecos().contains(selectedEndereco)){
			selectedParceiro.getEnderecos().add(selectedEndereco);
		}
		
		RequestContext.getCurrentInstance().execute("PF('endereco_dialog').hide()");
		RequestContext.getCurrentInstance().update("form_data_parceiro");
	}

	public void excluiEndereco(){
		selectedParceiro.getEnderecos().remove(selectedEndereco);
	}
	
}
