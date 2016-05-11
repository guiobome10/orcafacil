package br.com.jgsi.orcafacil.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.jgsi.orcafacil.activities.OrcamentoActivity;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;

/**
 * Created by guilherme.costa on 29/01/2016.
 */
public class CategoriaDespesaDAO {


    private final DAO dao;

    public CategoriaDespesaDAO(Activity activity) {
        this.dao = new DAO(activity);
    }


    public List<CategoriaDespesa> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL + Tabelas.TB_CATEGORIA_DESPESA_NAME, null);
        List<CategoriaDespesa> categorias =  new ArrayList<CategoriaDespesa>();
        while(cursor.moveToNext()){
            CategoriaDespesa categoria = new CategoriaDespesa();
            categoria.setId(cursor.getLong(cursor.getColumnIndex("id")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            categoria.setImagem(CategoriaFactory.criaIcone(dao.getContext().getResources(), cursor.getInt(cursor.getColumnIndex("imagem"))));
            categorias.add(categoria);
        }

        cursor.close();
        return categorias;
    }

    public void inserir(CategoriaDespesa categoria){
        categoria.setId(dao.inserir(Tabelas.TB_CATEGORIA_DESPESA_NAME, toContentValues(categoria)));
    }

    public void atualizar(CategoriaDespesa categoria){
        dao.atualizar(Tabelas.TB_CATEGORIA_DESPESA_NAME, toContentValues(categoria), "id=?", new String[]{categoria.getId().toString()});
    }

    public void deletar(CategoriaDespesa categoria){
        dao.deletar(Tabelas.TB_CATEGORIA_DESPESA_NAME, "id=?", new String[]{categoria.getId().toString()});
    }


    private ContentValues toContentValues(CategoriaDespesa categoria) {
        ContentValues cv = new ContentValues();
        cv.put("nome", categoria.getNome());
        cv.put("imagem", categoria.getImagem().getDrawableID());
        return cv;
    }
}
