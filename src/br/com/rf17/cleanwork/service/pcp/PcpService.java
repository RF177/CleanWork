package br.com.rf17.cleanwork.service.pcp;

import br.com.rf17.cleanwork.model.pcp.FichaTecnica;
import br.com.rf17.cleanwork.model.pcp.FichaTecnicaInsumo;

public class PcpService {

	public static FichaTecnica calculaFichaTecnica(FichaTecnica fichaTecnica){
		if(fichaTecnica.getInsumos() != null){
			double custosMaterias = 0.0;
			for(FichaTecnicaInsumo insumo : fichaTecnica.getInsumos()){
				custosMaterias += insumo.getVl_total();
			}
			
			fichaTecnica.setCusto_total(custosMaterias);
		}
		
		return fichaTecnica;
	}
	
	public static FichaTecnicaInsumo calculaCustoInsumo(FichaTecnicaInsumo fichaTecnicaInsumo){
		
		fichaTecnicaInsumo.setVl_total(fichaTecnicaInsumo.getQtd() * fichaTecnicaInsumo.getVl_custo());
		
		return fichaTecnicaInsumo;
	}
	
}
