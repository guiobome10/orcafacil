package br.com.jgsi.orcafacil.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class Orcamento implements Serializable, Cloneable {

    private Long id;
    private CategoriaDespesa categoria;
    private Double valor;
    private Double saldo;
    private Calendar dataInicio;
    private Calendar dataFim;
    private StatusOrcamento status;
    private Periodicidade periodicidade;

    public Orcamento() {
        categoria = null;
        valor = 0.;
        saldo = 0.;
        dataInicio = Calendar.getInstance();
        dataFim = Calendar.getInstance();
        status = StatusOrcamento.EM_ABERTO;
        periodicidade = Periodicidade.MES;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public void setStatus(StatusOrcamento status) {
        this.status = status;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getValorFormatado() {
        return DecimalFormatter.formata(valor);
    }

    public String getValorFormatoDinheiro() {
        return DecimalFormatter.formataDinheiro(valor);
    }

    public String getDataFormatada() {
        return DateFormatter.formata(dataFim);
    }

    public String getDataInicioFormatada() {
        return DateFormatter.formata(dataInicio);
    }

    public void calculaDataFim() {
        if(!periodicidade.equals(Periodicidade.PERZONALIZADA)){
            setDataFim(DateUtil.addDayToDate(dataInicio, periodicidade.quantidade()));
        }
    }

    public String getDataInicioFormatadaParaBase() {
        return DateFormatter.formataAnoMesDia(dataInicio);
    }
    public String getDataFimFormatadaParaBase() {
        return DateFormatter.formataAnoMesDia(dataFim);
    }

    public void deposita(Double diferencaValorNovoAnterior) {
        this.saldo += diferencaValorNovoAnterior;
    }

    public String getSaldoFormatoDinheiro() {
        return DecimalFormatter.formataDinheiro(saldo);
    }

    public boolean estaDentroDoMesmoPeriodoDo(Orcamento orcamento){
        return  //Orcamento está dentro do período do orcamento informado no parâmetro.
                (  DateUtil.dataEhMaiorOuIgual(dataInicio, orcamento.getDataInicio())
                && DateUtil.dataEhMenorOuIgual(dataFim, orcamento.getDataFim()))
                ||
                //Orcamento está intersecionando o orçamento informado pela data de inicio.
                (  DateUtil.dataEhMenorOuIgual(dataInicio, orcamento.getDataInicio())
                && DateUtil.dataEhMaiorOuIgual(dataFim, orcamento.getDataInicio()))
                ||
                //Orcamento está intersecionando o orçamento informado pela data final.
                (  DateUtil.dataEhMenorOuIgual(dataInicio, orcamento.getDataFim())
                && DateUtil.dataEhMaiorOuIgual(dataFim, orcamento.getDataFim()))
                ||
                //Orcamento contém dentro do seu período o orcamento passado por parâmetro.
                (  DateUtil.dataEhMenorOuIgual(dataInicio, orcamento.getDataInicio())
                && DateUtil.dataEhMaiorOuIgual(dataFim, orcamento.getDataFim()))
                ;

    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e){
            Log.e("br.com.jgsi.error", e.toString());
            return null;
        }
    }

    public String getPeriodo() {
        return "Período: " + getDataInicioFormatada() + " à " + getDataFormatada();
    }
}
