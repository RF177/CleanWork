package br.com.rf17.cleanwork.service.cadastro;

import br.com.rf17.cleanwork.model.cadastro.Produto;

public class ProdutoService {
	
	public static double calculaSugestaoPrecoVenda(Produto produto){
		if(produto != null){
			return ((produto.getPreco_custo_total() * (produto.getTaxa_lucro()/100)) + produto.getPreco_custo());
		}
		return 0.0;
	}
	
}
