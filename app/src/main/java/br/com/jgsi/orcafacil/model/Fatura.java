package br.com.jgsi.orcafacil.model;

import java.io.Serializable;
import java.util.Calendar;

import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 10/03/2016.
 */
public class Fatura implements Serializable, Cloneable {

    private Long id;
    private Calendar dataInicialCiclo;
    private Calendar dataFinalCiclo;
    private Calendar dataVencimento;
    private Double valor;
    private CartaoDeCredito cartaoDeCredito;

    public Fatura() {
        dataInicialCiclo = Calendar.getInstance();
        dataFinalCiclo = DateUtil.addDayToDate(Calendar.getInstance(), 30);
        dataVencimento = DateUtil.addDayToDate(Calendar.getInstance(), 35);
        valor = 0.;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDataInicialCiclo() {
        return dataInicialCiclo;
    }

    public String getDataInicialCicloFormatada() {
        return DateFormatter.formata(dataInicialCiclo);
    }

    public String getDataInicialCicloFormatadaParaBase() {
        return DateFormatter.formataAnoMesDia(dataInicialCiclo);
    }

    public void setDataInicialCiclo(Calendar dataInicialCiclo) {
        this.dataInicialCiclo = dataInicialCiclo;
    }

    public Calendar getDataFinalCiclo() {
        return dataFinalCiclo;
    }

    public String getDataFinalCicloFormatada(){
        return DateFormatter.formata(dataFinalCiclo);
    }

    public String getDataFinalCicloFormatadaParaBase(){
        return DateFormatter.formataAnoMesDia(dataFinalCiclo);
    }

    public void setDataFinalCiclo(Calendar dataFinalCiclo) {
        this.dataFinalCiclo = dataFinalCiclo;
    }

    public Calendar getDataVencimento() {
        return dataVencimento;
    }

    public String getDataVencimentoFormatada(){
        return DateFormatter.formata(dataVencimento);
    }

    public String getDataVencimentoFormatadaParaBase(){
        return DateFormatter.formataAnoMesDia(dataVencimento);
    }

    public void setDataVencimento(Calendar dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public String getValorFormatado(){
        return DecimalFormatter.formata(valor);
    }

    public String getValorFormatoDinheiro(){
        return DecimalFormatter.formataDinheiro(valor);
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public CartaoDeCredito getCartaoDeCredito() {
        return cartaoDeCredito;
    }

    public void setCartaoDeCredito(CartaoDeCredito cartaoDeCredito) {
        this.cartaoDeCredito = cartaoDeCredito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fatura fatura = (Fatura) o;

        return !(id != null ? !id.equals(fatura.id) : fatura.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return cartaoDeCredito.getAsReadableString() + ": " + getDataInicialCicloFormatada() + " à " + getDataFinalCicloFormatada();
    }

    public void aumenta(Double valor) {
        this.valor+= valor;
    }
    public void diminui(Double valor) {
        this.valor-= valor;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
