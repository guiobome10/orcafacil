package br.com.jgsi.orcafacil.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class Receita implements Serializable, Cloneable {

    private Long id;
    private CategoriaReceita categoria;
    private Conta conta;
    private Double valor;
    private Calendar data;
    private String detalhes;

    public Receita() {
        this.conta = null;
        this.categoria = null;
        this.valor = 0.;
        this.data = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaReceita getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaReceita categoria) {
        this.categoria = categoria;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getValorFormatado() {
        return DecimalFormatter.formata(valor);
    }
    public String getValorFormatoDinheiro() {
        return DecimalFormatter.formataDinheiro(valor);
    }

    public String getDataFormatada() {
        return DateFormatter.formata(data);
    }

    public String getDataFormatadaParaBase() {
        return DateFormatter.formataAnoMesDia(data);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Double obtemDiferencaValorNovoAnterior(Double valorAnterior, Double novoValor) {
        if(valorAnterior > novoValor){
            return (valorAnterior - novoValor) *-1;
        } else if (valorAnterior < novoValor){
            return novoValor - valorAnterior;
        } else {
            return novoValor;
        }
    }
}
