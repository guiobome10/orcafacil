package br.com.jgsi.orcafacil.model;

import java.io.Serializable;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class Categoria implements Serializable {

    private Long id;
    private String nome;
    private transient Icone imagem;

    public Categoria() {    }

    public Categoria(String nome, Icone imagem) {
        this.id = null;
        this.nome = nome;
        this.imagem = imagem;
    }

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

    public Icone getImagem() {
        return imagem;
    }

    public void setImagem(Icone imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return nome;
    }
}
