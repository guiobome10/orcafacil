package br.com.jgsi.orcafacil.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Currency;

import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class Conta implements Serializable {

    private Long id;
    private String nome;
    private Double saldo;
    private Moeda moeda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getSaldoFormatado() {
        return DecimalFormatter.formataDinheiro(saldo);
    }

    public Conta() {
        setNome("");
        setMoeda(Moeda.REAL);
        setSaldo(0.0);
    }

    @Override
    public String toString() {
        return nome;
    }

    public void deposita(Double valor) {
        this.setSaldo(saldo + valor);
    }

    public void saca(Double valor) {
        this.setSaldo(saldo - valor);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof  Conta){
            if(((Conta) o).getId().equals(getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().intValue();
    }
}
