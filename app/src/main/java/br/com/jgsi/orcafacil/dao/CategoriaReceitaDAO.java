package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class CategoriaReceitaDAO {
    
    private DAO dao;

    public CategoriaReceitaDAO(Context context) {
        this.dao = new DAO(context);
        
    }

    public List<CategoriaReceita> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL + Tabelas.TB_CATEGORIA_RECEITA_NAME, null);
        List<CategoriaReceita> categorias =  new ArrayList<CategoriaReceita>();
        while(cursor.moveToNext()){
            CategoriaReceita categoria = new CategoriaReceita();
            categoria.setId(cursor.getLong(cursor.getColumnIndex("id")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            categoria.setImagem(CategoriaFactory.criaIcone(dao.getContext().getResources(), cursor.getInt(cursor.getColumnIndex("imagem"))));
            categorias.add(categoria);
        }

        cursor.close();
        return categorias;
    }

    public void inserir(CategoriaReceita categoria){
        categoria.setId(dao.inserir(Tabelas.TB_CATEGORIA_RECEITA_NAME, toContentValues(categoria)));
    }

    public void atualizar(CategoriaReceita categoria){
        dao.atualizar(Tabelas.TB_CATEGORIA_RECEITA_NAME, toContentValues(categoria), "id=?", new String[]{categoria.getId().toString()});
    }

    public void deletar(CategoriaReceita categoria){
        dao.deletar(Tabelas.TB_CATEGORIA_RECEITA_NAME, "id=?", new String[]{categoria.getId().toString()});
    }


    private ContentValues toContentValues(CategoriaReceita categoria) {
        ContentValues cv = new ContentValues();
        cv.put("nome", categoria.getNome());
        cv.put("imagem", categoria.getImagem().getDrawableID());
        return cv;
    }

}
