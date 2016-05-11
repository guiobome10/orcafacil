package br.com.jgsi.orcafacil.dao;

import java.util.List;

/**
 * Created by guilherme.costa on 11/03/2016.
 */
public interface CrudDAO<T> {

    void salvar(T modelClass);
    void remover(T modelClass);
    List<T> listar();

}
