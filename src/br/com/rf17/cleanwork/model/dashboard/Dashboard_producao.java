package br.com.rf17.cleanwork.model.dashboard;

import java.util.Date;

public class Dashboard_producao {

	private int situacao;
	private Date data;
	private long qtd;

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public long getQtd() {
		return qtd;
	}

	public void setQtd(long qtd) {
		this.qtd = qtd;
	}

}
