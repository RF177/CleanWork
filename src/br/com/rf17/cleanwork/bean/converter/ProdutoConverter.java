package br.com.rf17.cleanwork.bean.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
 
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.rf17.cleanwork.dao.cadastros.ProdutoDao;
import br.com.rf17.cleanwork.model.cadastro.Produto;

@FacesConverter(value = "produto")
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {
		if (submittedValue.trim().equals("") || submittedValue.trim().equals("null")) {
			return null;
		} else {
			try {
				return new ProdutoDao().getById(Long.parseLong(submittedValue));
			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error","Produto n�o � valido!"));
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error","Produto n�o � valido!"));
			}
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Produto) value).getId_produto());
		}
	}
}