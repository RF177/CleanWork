package br.com.rf17.cleanwork.bean.pesquisagenerica;

import java.io.Serializable;

public class ColumnModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String header;
    private String property;
    private int width;
    
	public ColumnModel(String header, String property, int width) {
		super();
		this.header = header;
		this.property = property;
		this.width = width;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}    
} 