package br.com.rf17.cleanwork.service.pcp;

import br.com.rf17.cleanwork.model.pcp.FichaTecnica;
import br.com.rf17.cleanwork.model.pcp.FichaTecnicaInsumo;

public class PcpService {

	public static FichaTecnica calculaCustoTotalFicha(FichaTecnica fichaTecnica){
		fichaTecnica.setCusto_materiais(calculaCustosMaterias(fichaTecnica));
		fichaTecnica.setCusto_total(fichaTecnica.getCusto_maodeobra() + fichaTecnica.getCusto_ggf() + fichaTecnica.getCusto_outros() + fichaTecnica.getCusto_materiais());
		
		return fichaTecnica;
	}
	
	public static double calculaCustosMaterias(FichaTecnica fichaTecnica){
		if(fichaTecnica.getInsumos() != null){
			double custosMaterias = 0.0;
			for(FichaTecnicaInsumo insumo : fichaTecnica.getInsumos()){
				custosMaterias += insumo.getVl_total();
			}
			
			return custosMaterias;
		}
		return 0.0;
	}
	
	public static FichaTecnicaInsumo calculaCustoInsumo(FichaTecnicaInsumo fichaTecnicaInsumo){
		
		fichaTecnicaInsumo.setVl_total(fichaTecnicaInsumo.getQtd() * fichaTecnicaInsumo.getVl_custo());
		
		return fichaTecnicaInsumo;
	}
	
}
