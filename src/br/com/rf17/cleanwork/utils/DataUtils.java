package br.com.rf17.cleanwork.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtils {

	public static Date primeiroDiaMes(Date data){		
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime( data == null ? new Date() : data );							      
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), 1);
		return cal.getTime();
	}
	
	public static Date ultimoDiaMes(Date data){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime( data == null ? new Date() : data );		
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.getActualMaximum( Calendar.DAY_OF_MONTH ));
		return cal.getTime();
	}
	
	public static Date primeiroDiaDoMesAnterior(Date data) {  
		Calendar cal = GregorianCalendar.getInstance();  
		cal.add(Calendar.MONTH, -1);  
		cal.set(Calendar.DAY_OF_MONTH, 1);  
	    return cal.getTime();  
	}  
	  
	public static Date ultimoDiaDoMesAnterior(Date data) {  
		Calendar cal = GregorianCalendar.getInstance();  
		cal.add(Calendar.MONTH, -1);  
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    return cal.getTime();  
	}  
	
	/*
	 *  Retirado pois funcionava no windows, mas no linux estava jogando as datas erradas
	 * por causa da localidade da data. Tem que rever se for utilizado!!!
	 * 
	public static Date primeiroDiaDaSemana(Date date){
		Calendar cal = GregorianCalendar.getInstance();  
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	    return cal.getTime();  
	}
	
	public static Date ultimoDiaDaSemana(Date date){
		Calendar cal = GregorianCalendar.getInstance();  
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
	    return cal.getTime(); 
	}
	*/
	
	/**
	 * Verifica se duas datas sao iguais
	 * 
	 * @param dt1
	 * @param dt2
	 * @return boolean
	 */
	public static boolean dataIgual(Date dt1, Date dt2){
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");		
		return format.format(dt1).equals(format.format(dt2));
	}
	
	
	
	public static int[] decodeDate(java.util.Date dtSel) {
		GregorianCalendar gcSel = new GregorianCalendar();
		int[] iRetorno = { 0, 0, 0 };
		try {
			gcSel.setTime(dtSel);
			iRetorno[0] = gcSel.get(Calendar.YEAR);
			iRetorno[1] = gcSel.get(Calendar.MONTH) + 1;
			iRetorno[2] = gcSel.get(Calendar.DAY_OF_MONTH);
		}
		finally {
			gcSel = null;
		}
		return iRetorno;
	}
	
	public static Date encodeDate(int iAno, int iMes, int iDia) {
		Date dtRetorno = new Date();
		GregorianCalendar gcSel = new GregorianCalendar();
		try {
			gcSel.setTime(dtRetorno);
			gcSel.set(Calendar.YEAR, iAno);
			gcSel.set(Calendar.MONTH, iMes - 1);
			gcSel.set(Calendar.DAY_OF_MONTH, iDia);
			dtRetorno = gcSel.getTime();
		}
		finally {
			gcSel = null;
		}
		return dtRetorno;
	}
	
	
    public static String milisegundosParaHora(long tempo) {  
        String hora = "";  
        int secs = (int) tempo / 1000;  
        if (tempo == 0) {  
            hora = "0";  
        } else {  
            int[] ret = new int[3];  
            // calcula nmero de horas, minutos e segundos  
            ret[0] = secs / 3600;  
            secs = secs % 3600;  
            ret[1] = secs / 60;  
            secs = secs % 60;  
            ret[2] = secs;  
            if (ret[0] != 0) {  
                hora += ret[0] + "h";  
            }  
            if (ret[1] != 0) {  
                hora += ret[1] + "min";  
            }  
        }       // fim do if (tempo == 0)  
        return hora;  
    } 
    
    
    /**
     * O método abaixo recebe duas datas no formato Date (java.util) e retorna a diferença em dias entre essas duas datas:
     * 
     * @param date1
     * @param date2
     * @return
     */
	public static  long getDateDifference(Date date1, Date date2) {
		return ((date1.getTime()-date2.getTime())/86400000);
	} 
			
	
	/**
	 * Verifica se Calendar passado como parametro é um dia útil (retorna true) ou se é
	 * um final de semana (retorna false, caso for sabado ou domingo)
	 * 
	 * @param cal
	 * @return boolean
	 */
	public static boolean isDiaUtil(Calendar cal) {  
        return !((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY));  
    }  

	
	/**
	 * Verifica se data passada como parametro é um dia útil (retorna true) ou se é
	 * um final de semana (retorna false, caso for sabado ou domingo)
	 * 
	 * @param dia
	 * @return boolean
	 */
    public static boolean isDiaUtil(Date dia) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(dia);  
        return isDiaUtil(cal);  
    }  
  
    
    /**
     * Verifica se data cai em um dia útil (dia da semana), se nao cair, retorna
     * o proximo dia útil.
     * 
     * Ex.:
     * 
     * 17/01/2015 cai em um sabado, ele vai verificar e vai retornar
     * a data de 19/01/2015, que é uma segunda feira.
     * 
     * 
     * @param data
     * @return proximoDiaUtil
     */
    public static Date verificaDiaUtil(Date data) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(data);  
          
        while(!isDiaUtil(cal))  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
      
        return cal.getTime();  
    } 
	   
}
