package br.com.rf17.cleanwork.bean.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;

@FacesConverter(value = "cnpjcpfConverter")
public class CNPJCPFConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		/*valor= valor.trim();			
		if (valor != null || valor != "") {		
			CNPJFormatter cnpjFormatter = new CNPJFormatter();
			valor = cnpjFormatter.format(valor);	
		}*/
		return valor;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {
				
		String v= valor.toString().trim();	

		if (v != null || v != "") {	
			try {				
				if(v.length() == 11){//CPF
					CPFFormatter cpfFormatter = new CPFFormatter();
					v = cpfFormatter.format(v);
				}else if(v.length() == 14) {//CNPJ
					CNPJFormatter cnpjFormatter = new CNPJFormatter();
					v = cnpjFormatter.format(v);
				}
			} catch (Exception e) {		
				e.printStackTrace();
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error: ","CNPJ/CPF '"+v+"' invalidos! "));
			}
		}
				
		return v.toString();
	}
}