package br.com.rf17.cleanwork.bean.pesquisagenerica;

import java.io.Serializable;

public class PesquisaGenericaTableDados implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id_registro;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	

	public PesquisaGenericaTableDados(long id_registro, String data1,
			String data2, String data3, String data4, String data5, String data6) {
		super();
		this.id_registro = id_registro;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
		this.data6 = data6;
	}
	
	public long getId_registro() {
		return id_registro;
	}
	public void setId_registro(long id_registro) {
		this.id_registro = id_registro;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getData5() {
		return data5;
	}
	public void setData5(String data5) {
		this.data5 = data5;
	}
	public String getData6() {
		return data6;
	}
	public void setData6(String data6) {
		this.data6 = data6;
	}
}
	
