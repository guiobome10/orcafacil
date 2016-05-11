package br.com.jgsi.orcafacil.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public enum Periodicidade {

    //DIA           (1, "Dia"),
    SEMANA        (  7, "Semana"),
    QUINZENA      ( 15, "Quinzena"),
    MES           ( 30, "Mes"),
    TRIMESTRE     ( 90, "Trimestre"),
    SEMESTRE      (180, "Semestre"),
    ANO           (365, "Ano"),
    PERZONALIZADA ( -1, "Personalizado");

    private int quantidade;
    private String nome;

    Periodicidade(int quantidade, String nome) {
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public int quantidade(){ return quantidade;};
    public String nome(){ return nome;};

    public static List<Periodicidade> asList() {
        List<Periodicidade> periodicidades = new ArrayList<Periodicidade>();
        for(Periodicidade periodicidade : Periodicidade.values()){
            periodicidades.add(periodicidade);
        }
        return periodicidades;
    }

    public static List<String> asListString(){
        List<String> periodicidades = new ArrayList<String>();
        for(Periodicidade periodicidade : Periodicidade.values()){
            periodicidades.add(periodicidade.nome);
        }
        return periodicidades;

    }

    public static Periodicidade get(String nome){
        for(Periodicidade periodicidade : Periodicidade.values()){
            if(periodicidade.nome.toUpperCase().equals(nome.toUpperCase()))
                return periodicidade;
        }
        return Periodicidade.MES;
    }


    public static Periodicidade get(int periodicidade) {
        for(Periodicidade p : Periodicidade.values()){
            if(p.quantidade == periodicidade)
                return p;
        }
        return Periodicidade.PERZONALIZADA;
    }
}
