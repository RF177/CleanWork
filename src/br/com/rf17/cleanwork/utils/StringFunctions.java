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
	
	/**
	 * Remove acentos do texto
	 * 
	 * @param sTexto
	 * @return
	 */
	public static String clearAccents(String sTexto) {

		if (sTexto == null)
			return "";

		char cVals[] = sTexto.toCharArray();

		for (int i = 0; i < cVals.length; i++) {
			cVals[i] = clearAccent(cVals[i]);
		}
		return new String(cVals);
	}
	
	/**
	 * Remove acento do caracter
	 * 
	 * @param char
	 * @return char
	 */
	public static char clearAccent(char cKey) {

		char cTmp = cKey;
		if (isContained(cTmp, "ãâáàä"))
			cTmp = 'a';
		else if (isContained(cTmp, "ÃÂÁÀÄ"))
			cTmp = 'A';
		else if (isContained(cTmp, "êéèë"))
			cTmp = 'e';
		else if (isContained(cTmp, "ÊÉÈË"))
			cTmp = 'E';
		else if (isContained(cTmp, "îíìï"))
			cTmp = 'i';
		else if (isContained(cTmp, "ÎÍÌÏ"))
			cTmp = 'I';
		else if (isContained(cTmp, "õôóòö"))
			cTmp = 'o';
		else if (isContained(cTmp, "ÕÔÓÒÖ"))
			cTmp = 'O';
		else if (isContained(cTmp, "ûúùü"))
			cTmp = 'u';
		else if (isContained(cTmp, "ÛÚÙÜ"))
			cTmp = 'U';
		else if (isContained(cTmp, "ç"))
			cTmp = 'c';
		else if (isContained(cTmp, "Ç"))
			cTmp = 'C';

		// else if (isContained(cTmp, "&"))
		// cTmp = 'E';

		return cTmp;
	}
	
	public static boolean isContained(char cTexto, String sTexto) {
		boolean bRetorno = false;
		for (int i = 0; i < sTexto.length(); i++) {
			if (cTexto == sTexto.charAt(i)) {
				bRetorno = true;
				break;
			}
		}
		return bRetorno;
	}
	
}
