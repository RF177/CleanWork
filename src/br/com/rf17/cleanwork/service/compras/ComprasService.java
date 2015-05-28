package br.com.rf17.cleanwork.service.compras;

import br.com.rf17.cleanwork.model.compras.Entrada;
import br.com.rf17.cleanwork.model.compras.EntradaItem;

public class ComprasService {

	public static EntradaItem calculaItem(EntradaItem item){
		
		item.setVl_total(item.getQtd() * item.getVl_unitario());
		
		return item;		
	}
	
	public static Entrada calculaCompra(Entrada entrada){
		
		entrada.setValor_total(0.0);
		
		if(entrada.getEntradaItems() != null){			
			for(EntradaItem item : entrada.getEntradaItems()){
				entrada.setValor_total(entrada.getValor_total() + item.getVl_total());
			}
		}
		
		entrada.setValor_total(entrada.getValor_total() + entrada.getValor_frete());
		
		return entrada;
	}
	
}
