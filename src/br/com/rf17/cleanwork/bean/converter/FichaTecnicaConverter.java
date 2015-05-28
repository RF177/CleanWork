package br.com.rf17.cleanwork.bean.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
 
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.rf17.cleanwork.dao.pcp.FichaTecnicaDao;
import br.com.rf17.cleanwork.model.pcp.FichaTecnica;

@FacesConverter(value = "fichatecnica")
public class FichaTecnicaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {
		if (submittedValue.trim().equals("") || submittedValue.trim().equals("null")) {
			return null;
		} else {
			try {
				return new FichaTecnicaDao().getById(Long.parseLong(submittedValue));
			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error","Ficha Técnica não é valido!"));
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error","Ficha Técnica não é valido!"));
			}
		}		
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((FichaTecnica) value).getId_fichatecnica());
		}
	}
}