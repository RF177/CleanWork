package br.com.rf17.cleanwork.model.estoque;

import br.com.rf17.cleanwork.model.cadastro.Produto;

public class Estoque {

	private Produto produto;
	private Long qtd;
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Long getQtd() {
		return qtd;
	}
	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}
	
}
