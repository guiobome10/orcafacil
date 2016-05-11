package br.com.jgsi.orcafacil.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guilherme.costa on 10/03/2016.
 */
public class CartaoDeCredito implements Serializable, Cloneable {

    private Long id;
    private Conta conta;
    private String ultimosQuatroDigitos;
    private Bandeira bandeira;
    private List<Fatura> faturas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getUltimosQuatroDigitos() {
        return ultimosQuatroDigitos;
    }

    public void setUltimosQuatroDigitos(String ultimosQuatroDigitos) {
        this.ultimosQuatroDigitos = ultimosQuatroDigitos;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }

    public String getAsReadableString() {
        return bandeira.toString() + " - " + ultimosQuatroDigitos;
    }

    @Override
    public String toString() {
        return getAsReadableString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartaoDeCredito that = (CartaoDeCredito) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Fatura getFaturaAtual() {
        return faturas == null ? null : faturas.get(0);
    }
}
