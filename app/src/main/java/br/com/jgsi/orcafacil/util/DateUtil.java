package br.com.jgsi.orcafacil.util;

import java.util.Calendar;

/**
 * Created by guilherme.costa on 02/02/2016.
 */
public class DateUtil {


    public static Calendar addDayToDate(Calendar dataInicio, int quantidade) {
        Calendar dataFim = (Calendar) dataInicio.clone();
        dataFim.add(Calendar.DAY_OF_YEAR, quantidade);
        return dataFim;
    }

    public static Calendar addMonthToDate(Calendar data, int quantidade) {
        Calendar dataRetorno = (Calendar) data.clone();
        dataRetorno.add(Calendar.MONTH, quantidade);
        return dataRetorno;
    }

    public static Calendar dataInicioMesAtual() {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_MONTH, data.getActualMinimum(Calendar.DAY_OF_MONTH));
        return data;
    }

    public static Calendar dataFimMesAtual() {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_MONTH, data.getActualMaximum(Calendar.DAY_OF_MONTH));
        return data;
    }

    public static boolean dataEhMenorOuIgual(Calendar data, Calendar dataFinalCiclo) {
        return data.before(dataFinalCiclo) || data.compareTo(dataFinalCiclo) ==0;
    }

    public static boolean dataEhMaiorOuIgual(Calendar dataFinal, Calendar dataInicial) {
        return dataFinal.after(dataInicial) || dataFinal.compareTo(dataInicial) ==0;
    }

    public static Calendar ultimoDiaMes(Calendar data) {
        Calendar dataRetorno = (Calendar) data.clone();
        dataRetorno.set(Calendar.DAY_OF_MONTH, dataRetorno.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dataRetorno;
    }
}
