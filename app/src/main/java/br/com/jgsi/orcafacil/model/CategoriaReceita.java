package br.com.jgsi.orcafacil.model;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class CategoriaReceita extends Categoria {


    public CategoriaReceita(){}

    public CategoriaReceita(String nome, Icone imagem) {
        super(nome, imagem);
    }

    @Override
    public String toString() {
        return getNome();
    }
}
