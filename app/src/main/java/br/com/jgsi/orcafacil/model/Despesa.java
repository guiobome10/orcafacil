package br.com.jgsi.orcafacil.model;

import java.io.Serializable;
import java.util.Calendar;

import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class Despesa implements Serializable, Cloneable {

    private Long id;
    private CategoriaDespesa categoria;
    private Conta conta;
    private Orcamento orcamento;
    private Double valor;
    private Calendar data;
    private String detalhes;
    private Periodicidade periodicidade;
    private int numeroRepeticoes;

    public Despesa() {
        this.conta = null;
        this.categoria = null;
        this.valor = 0.;
        this.data = Calendar.getInstance();
        this.detalhes = "";
        this.periodicidade = Periodicidade.PERZONALIZADA;
        this.numeroRepeticoes = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaDespesa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDespesa categoria) {
        this.categoria = categoria;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
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

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public int getNumeroRepeticoes() {
        return numeroRepeticoes;
    }

    public void setNumeroRepeticoes(int numeroRepeticoes) {
        this.numeroRepeticoes = numeroRepeticoes;
    }

    public String getDataFormatada() {
        return DateFormatter.formata(data);
    }

    public String getValorFormatado() {
        return DecimalFormatter.formata(valor);
    }

    public String getValorFormatoDinheiro() {
        return DecimalFormatter.formataDinheiro(valor);
    }

    public String getDataFormatadaParaBase() {
        return DateFormatter.formataAnoMesDia(data);
    }

    public Double obterDiferencaValorNovoAnterior(Double valorAnterior, Double valorNovo) {
        if(valorNovo > valorAnterior){
            return (valorNovo - valorAnterior) * -1 ;
        } else if( valorNovo < valorAnterior){
            return valorAnterior - valorNovo;
        } else {
            return valorNovo;
        }
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
