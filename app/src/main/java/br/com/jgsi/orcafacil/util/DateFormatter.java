package br.com.jgsi.orcafacil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class DateFormatter {


    public static String formata(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data.getTime());
    }
    public static Calendar formata(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar retorno = Calendar.getInstance();
        try {
            retorno.setTime(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static String formataAnoMesDia(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(data.getTime());
    }

    public static Calendar formataAnoMesDia(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar retorno = Calendar.getInstance();
        try {
            retorno.setTime(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static String formataMesDia(Calendar data){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
        return sdf.format(data.getTime());
    }
}
