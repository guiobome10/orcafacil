package br.com.jgsi.orcafacil.model;

import java.io.Serializable;
import java.util.Calendar;

import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 11/03/2016.
 */
public class DespesaCartao implements Serializable, Cloneable {

    private Long id;
    private CategoriaDespesa categoria;
    private Fatura fatura;
    private Orcamento orcamento;
    private Double valor;
    private Calendar data;
    private String detalhes;
    private boolean parcelada;
    private Integer quantidadeParcelas;
    private Integer numeroParcela;

    public DespesaCartao() {
        this.valor = 0.;
        this.data = Calendar.getInstance();
        this.parcelada = false;
        this.quantidadeParcelas = 1;
        this.numeroParcela = 1;
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

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
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

    public boolean isParcelada() {
        return parcelada;
    }

    public void setParcelada(boolean parcelada) {
        this.parcelada = parcelada;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DespesaCartao that = (DespesaCartao) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getDataFormatadaParaBase() {
        return DateFormatter.formataAnoMesDia(data);
    }

    public String getValorFormatado() {
        return DecimalFormatter.formata(valor);
    }

    public String getDataFormatada() {
        return DateFormatter.formata(data);
    }

    public String getValorFormatoDinheiro() {
        return DecimalFormatter.formataDinheiro(valor);
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

    public void calculaValorParcela() {
        this.valor = getValorParcela();
    }

    public Double getValorParcela() {
        return quantidadeParcelas > 0 ? valor / quantidadeParcelas : valor;
    }

    public void aumentaFatura(){
        fatura.aumenta(valor);
    }

    public void diminuiFatura() {
        fatura.diminui(valor);
    }
}
