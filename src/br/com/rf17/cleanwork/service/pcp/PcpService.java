package br.com.rf17.cleanwork.service.pcp;

import java.util.Date;
import java.util.List;

import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.dao.pcp.ProducaoDao;
import br.com.rf17.cleanwork.model.pcp.FichaTecnica;
import br.com.rf17.cleanwork.model.pcp.FichaTecnicaInsumo;
import br.com.rf17.cleanwork.model.pcp.Producao;
import br.com.rf17.cleanwork.utils.StringFunctions;

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
	
	
	public static String lancaCustosIndiretos(Date dt1, Date dt2, Double vl_custo_indireto) throws Exception{
		
		ProducaoDao producaoDao = new ProducaoDao();
		ProdutoDao produtoDao = new ProdutoDao();
		
		String msg = "";
		
		try {
			Double producao_total_periodo = producaoDao.getQtdProducaoTotalPeriodo(dt1, dt2);
			
			List<Producao> producao_total_produto = producaoDao.getQtdProducaoTotalPeriodoProduto(dt1, dt2);
			
			if(producao_total_periodo != null && producao_total_periodo > 0 && 
			   producao_total_produto != null && !producao_total_produto.isEmpty()){
				
				msg += "--- Produção total no período: "+StringFunctions.formataDouble(producao_total_periodo,0)+" --- ";
				
				for(Producao producao : producao_total_produto){
					double v_perc_representacao = ((producao.getQtd() / producao_total_periodo) * 100);
					double v_custo_total_indireto = (vl_custo_indireto * (v_perc_representacao / 100));					
					//double v_custo_indireto = v_custo_total_indireto / producao.getQtd();//Por producao do produto
					
					producao.getProduto().setPreco_custo_indireto(v_custo_total_indireto);
					produtoDao.save(producao.getProduto());		
					
					msg += " Produto: "+producao.getProduto().getDescricao()+" - Representação: "+StringFunctions.formataDouble(v_perc_representacao,2)+"%  "+" Custo Indireto: "+StringFunctions.formataDouble(v_custo_total_indireto,2)+" | ";
				}
			}	
			
			return msg;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao lançar custos indiretos. (Motivo: "+e.getMessage()+")");
		}		
	}
	
}
