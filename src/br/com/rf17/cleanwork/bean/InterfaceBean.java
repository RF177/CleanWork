package br.com.rf17.cleanwork.bean;

import org.primefaces.event.SelectEvent;

public interface InterfaceBean {
	
	public abstract void onRowSelect(SelectEvent event);
	public abstract void novo();
	public abstract void salvar(boolean voltar);
	public abstract void excluir();
	public abstract void atualizar();
	
	//public abstract void mensagem(String msg_principal, Exception e);
	
}
