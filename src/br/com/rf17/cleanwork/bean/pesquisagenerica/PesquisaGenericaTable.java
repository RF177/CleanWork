package br.com.rf17.cleanwork.bean.pesquisagenerica;

import java.io.Serializable;
import java.util.List;

public class PesquisaGenericaTable implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pesquisaHeader;
	private List<ColumnModel> pesquisaGenericacolumns;//modelo da pesquisa
	private List<PesquisaGenericaTableDados> pesquisaGenericaDados;//os dados da pesquisa
	
	public PesquisaGenericaTable(String pesquisaHeader,
			List<ColumnModel> pesquisaGenericacolumns,
			List<PesquisaGenericaTableDados> pesquisaGenericaDados) {
		super();
		this.pesquisaHeader = pesquisaHeader;
		this.pesquisaGenericacolumns = pesquisaGenericacolumns;
		this.pesquisaGenericaDados = pesquisaGenericaDados;
	}
	
	public String getPesquisaHeader() {
		return pesquisaHeader;
	}
	public void setPesquisaHeader(String pesquisaHeader) {
		this.pesquisaHeader = pesquisaHeader;
	}
	public List<ColumnModel> getPesquisaGenericacolumns() {
		return pesquisaGenericacolumns;
	}
	public void setPesquisaGenericacolumns(List<ColumnModel> pesquisaGenericacolumns) {
		this.pesquisaGenericacolumns = pesquisaGenericacolumns;
	}
	public List<PesquisaGenericaTableDados> getPesquisaGenericaDados() {
		return pesquisaGenericaDados;
	}
	public void setPesquisaGenericaDados(
			List<PesquisaGenericaTableDados> pesquisaGenericaDados) {
		this.pesquisaGenericaDados = pesquisaGenericaDados;
	}
}
