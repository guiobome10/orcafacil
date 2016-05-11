package br.com.jgsi.orcafacil.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public enum Moeda {

    REAL("R$", "Real"),
    EURO("€", "Euro"),
    DOLAR("$", "Dolar");

    private String simbolo;
    private String nome;

    Moeda(String simbolo, String nome) {
        this.simbolo = simbolo;
        this.nome = nome;
    }

    public String simbolo() {
        return simbolo;
    }

    public String nome() {
        return nome;
    }

    public static Moeda get(String nome){
        for(Moeda moeda : Moeda.values()){
            if(moeda.nome.toUpperCase().equals(nome.toUpperCase()))
                return moeda;
        }
        return Moeda.REAL;
    }

    public static List<Moeda> getAsList(){
        List<Moeda> moedas = new ArrayList<Moeda>();
        for(Moeda moeda : Moeda.values()){
            moedas.add(moeda);
        }
        return moedas;

    }

    public static List<String> getAsListString(){
        List<String> moedas = new ArrayList<String>();
        for(Moeda moeda : Moeda.values()){
            moedas.add(moeda.nome);
        }
        return moedas;

    }

}
