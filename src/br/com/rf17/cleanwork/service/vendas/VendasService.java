package br.com.rf17.cleanwork.service.vendas;

import br.com.rf17.cleanwork.model.vendas.Venda;
import br.com.rf17.cleanwork.model.vendas.VendaItem;

public class VendasService {

	public static VendaItem calculaItem(VendaItem item){
		
		item.setVl_total(item.getQtd() * item.getVl_unitario());
		
		return item;		
	}
	
	public static Venda calculaVenda(Venda venda){
		
		venda.setValor_total(0.0);
		
		if(venda.getVendaItems() != null){			
			for(VendaItem item : venda.getVendaItems()){
				venda.setValor_total(venda.getValor_total() + item.getVl_total());
			}
		}
		
		return venda;
	}
	
}
