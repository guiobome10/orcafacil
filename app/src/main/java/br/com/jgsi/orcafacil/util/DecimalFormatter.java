package br.com.jgsi.orcafacil.util;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class DecimalFormatter {

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DecimalFormat dfd = new DecimalFormat("¤ ###,##0.00; ¤ -###,##0.00");

    public static String formata(Double valor) {
        return df.format(valor);
    }

    public static String formataDinheiro(Double valor) {
        return dfd.format(valor);
    }

    public static Double formata(String valor) {
        try {
            return df.parse(valor).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.;
        }
    }
}
