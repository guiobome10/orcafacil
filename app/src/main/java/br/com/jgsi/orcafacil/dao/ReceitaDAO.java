package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Receita;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;
import br.com.jgsi.orcafacil.util.DateFormatter;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class ReceitaDAO {

    private ContaDAO contaDAO;
    private DAO dao;
    private Context context;

    public ReceitaDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public List<Receita> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_RECEITAS_ORDENADO_POR_DATA, null);
        List<Receita> receitas =  new ArrayList<Receita>();
        carregaListaDoCursor(cursor, receitas);
        cursor.close();
        return receitas;
    }

    private void carregaListaDoCursor(Cursor cursor, List<Receita> receitas) {
        while(cursor.moveToNext()){
            Receita receita = new Receita();
            receita.setId(cursor.getLong(cursor.getColumnIndex("id")));
            receita.setData(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex("data"))));
            receita.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            receita.setDetalhes(cursor.getString(cursor.getColumnIndex("detalhes")));
            CategoriaReceita categoriaReceita = new CategoriaReceita();
            receita.setCategoria(categoriaReceita);
            categoriaReceita.setId(cursor.getLong(cursor.getColumnIndex("id_categoria")));
            categoriaReceita.setNome(cursor.getString(cursor.getColumnIndex("nome_categoria")));
            categoriaReceita.setImagem(CategoriaFactory.criaIcone(dao.getContext().getResources(), cursor.getInt(cursor.getColumnIndex("imagem_categoria"))));
            Conta conta = new Conta();
            receita.setConta(conta);
            conta.setId(cursor.getLong(cursor.getColumnIndex("id_conta")));
            conta.setNome(cursor.getString(cursor.getColumnIndex("nome_conta")));
            conta.setSaldo(cursor.getDouble(cursor.getColumnIndex("saldo_conta")));
            conta.setMoeda(Moeda.get(cursor.getString(cursor.getColumnIndex("moeda_conta"))));
            receitas.add(receita);
        }
    }

    public void inserir(Receita receita){
        dao.inserir(Tabelas.TB_RECEITA_NAME, toContentValues(receita));
        receita.getConta().deposita(receita.getValor());
        this.contaDAO = new ContaDAO(context);
        contaDAO.atualizar(receita.getConta());
    }

    public void atualizar(Receita receita){
        dao.atualizar(Tabelas.TB_RECEITA_NAME, toContentValues(receita), "id=?", new String[]{receita.getId().toString()});
    }

    public void deletar(Receita receita){
        dao.deletar(Tabelas.TB_RECEITA_NAME, "id=?", new String[]{receita.getId().toString()});
    }


    private ContentValues toContentValues(Receita receita) {
        ContentValues cv = new ContentValues();
        cv.put("id_categoria", receita.getCategoria().getId());
        cv.put("id_conta", receita.getConta().getId());
        cv.put("data", receita.getDataFormatadaParaBase());
        cv.put("valor", receita.getValor());
        cv.put("detalhes", receita.getDetalhes());
        return cv;
    }

    public List<Receita> getByConta(Conta conta) {
        Cursor cursor = queryReceitaPorConta(conta);
        List<Receita> receitas = new ArrayList<Receita>();
        carregaListaDoCursor(cursor, receitas);
        cursor.close();
        return receitas;
    }

    private Cursor queryReceitaPorConta(Conta conta) {
        return dao.exeqQuery(Statements.SELECT_RECEITAS_POR_CONTA, new String[]{conta.getId().toString()});
    }

    public long getCountConta(Conta conta) {
        return queryReceitaPorConta(conta).getCount();
    }

    public void excluir(Conta conta) {
        dao.deletar(Tabelas.TB_RECEITA_NAME, "id_conta=?", new String[]{conta.getId().toString()});
    }
}
