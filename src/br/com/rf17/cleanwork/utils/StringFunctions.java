package br.com.rf17.cleanwork.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class StringFunctions {
	
	/**
	 * Character Description # Qualquer numero valido.
	 *
	 * ' Usado para não usar nenhum caracter especial na formatacao ("\n", "\t"....)
	 *
	 * U Qualquer caracter Todas as letras minusculas sao passadas para maiuscula.
	 *
	 * L Qualquer caracter Todas as letras maiusculas sao passadas para minusculas
	 *
	 * A Qualquer caracter ou numero ( Character.isLetter or Character.isDigit )
	 *
	 * ? Qualquer caracter ( Character.isLetter ).
	 *
	 * * Qualquer Coisa.
	 *
	 * H Qualquer caracter hexa (0-9, a-f ou A-F).
	 *
	 * ==================================== 
	 * ex: value = "A1234B567Z" mask = "A-AAAA-AAAA-A" output : A-1234-B567-Z
	 * ===================================
	 * 
	 * @param string
	 * @param mask
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String formatString(String string, String mask) throws java.text.ParseException {
		javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter(mask);
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(string);
	}
	
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	/**
	 * Formata Double para String com virgulas e 2 casas decimais 
	 * 
	 * @param casasDecimais (Número de casas decimais maximais e minimas depois da virgula)
	 * @param valor numero (Ex.: '1212.95')
	 * @return String formatada (Ex.: '1.212,95' (se param casasDecimais igual a 2) 
	 */
	public static String formataDouble(double valor, int casasDecimais) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(casasDecimais);
		numberFormat.setMinimumFractionDigits(casasDecimais);
		numberFormat.setRoundingMode(RoundingMode.HALF_UP);//Arrendodamento
		return numberFormat.format(valor);	    
	}
	
}
