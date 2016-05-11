package br.com.jgsi.orcafacil.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.activities.OrcamentoActivity;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Periodicidade;
import br.com.jgsi.orcafacil.model.StatusOrcamento;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;
import br.com.jgsi.orcafacil.util.DateFormatter;

/**
 * Created by guilherme.costa on 29/01/2016.
 */
public class OrcamentoDAO {

    private DAO dao;
    private Context context;

    public OrcamentoDAO(Context context) {
        this.context = context;
        dao = new DAO(context);
    }

    public List<Orcamento> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_ORCAMENTOS_ORDERNADO_POR_DATA, null);
        List<Orcamento> orcamentos =  new ArrayList<Orcamento>();
        while(cursor.moveToNext()){
            Orcamento orcamento = new Orcamento();
            pegaDadosCursor(cursor, orcamento);
            orcamentos.add(orcamento);
        }

        cursor.close();
        return orcamentos;
    }

    public void inserir(Orcamento orcamento){
        dao.inserir(Tabelas.TB_ORCAMENTO_NAME, toContentValues(orcamento));
    }

    public void inserirLista(List<Orcamento> orcamentos){
        for(Orcamento orcamento : orcamentos){
            inserir(orcamento);
        }
    }

    public void atualizar(Orcamento orcamento){
        dao.atualizar(Tabelas.TB_ORCAMENTO_NAME, toContentValues(orcamento), "id=?", new String[]{orcamento.getId().toString()});
    }

    public void deletar(Orcamento orcamento){
        dao.deletar(Tabelas.TB_ORCAMENTO_NAME, "id=?", new String[]{orcamento.getId().toString()});
    }


    private ContentValues toContentValues(Orcamento orcamento) {
        ContentValues cv = new ContentValues();
        cv.put("id_categoria", orcamento.getCategoria().getId());
        cv.put("valor", orcamento.getValor());
        cv.put("saldo", orcamento.getSaldo());
        cv.put("data_inicio", orcamento.getDataInicioFormatadaParaBase());
        cv.put("data_fim", orcamento.getDataFimFormatadaParaBase());
        cv.put("status", orcamento.getStatus().id());
        cv.put("periodicidade", orcamento.getPeriodicidade().quantidade());
        return cv;
    }

    public Orcamento getByCategoriaDataDespesa(Categoria categoriaSelecionada, Despesa despesa) {
        if(categoriaSelecionada == null) return null;
        if(despesa == null) return null;
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ORCAMENTO_BY_CATEG_DATA_ORDENADO_DATA, new String[]{ categoriaSelecionada.getId().toString(), DateFormatter.formataAnoMesDia(despesa.getData()), DateFormatter.formataAnoMesDia(despesa.getData())});
        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        while(cursor.moveToNext()){
            Orcamento orcamento = new Orcamento();
            pegaDadosCursor(cursor, orcamento);
            orcamentos.add(orcamento);
        }
        if(orcamentos.size() > 0) return orcamentos.get(0);
        return null;
    }

    public Orcamento getByCategoriaDataDespesaCartao(CategoriaDespesa categoriaDespesa, DespesaCartao despesaCartao) {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ORCAMENTO_BY_CATEG_DATA_ORDENADO_DATA
                , new String[]{ categoriaDespesa.getId().toString()
                              , DateFormatter.formataAnoMesDia(despesaCartao.getData())
                              , DateFormatter.formataAnoMesDia(despesaCartao.getData())});
        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        while(cursor.moveToNext()){
            Orcamento orcamento = new Orcamento();
            pegaDadosCursor(cursor, orcamento);
            orcamentos.add(orcamento);
        }
        if(orcamentos.size() > 0) return orcamentos.get(0);
        return null;
    }


    private void pegaDadosCursor(Cursor cursor, Orcamento orcamento) {
        orcamento.setId(cursor.getLong(cursor.getColumnIndex("id")));
        CategoriaDespesa categoria = new CategoriaDespesa();
        orcamento.setCategoria(categoria);
        categoria.setId(cursor.getLong(cursor.getColumnIndex("id_categoria")));
        categoria.setNome(cursor.getString(cursor.getColumnIndex("nome_categoria")));
        categoria.setImagem(CategoriaFactory.criaIcone(dao.getContext().getResources(), cursor.getInt(cursor.getColumnIndex("imagem_categoria"))));
        orcamento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
        orcamento.setSaldo(cursor.getDouble(cursor.getColumnIndex("saldo")));
        orcamento.setDataInicio(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex("data_inicio"))));
        orcamento.setDataFim(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex("data_fim"))));
        orcamento.setStatus(StatusOrcamento.get(cursor.getInt(cursor.getColumnIndex("status"))));
        orcamento.setPeriodicidade(Periodicidade.get(cursor.getInt(cursor.getColumnIndex("periodicidade"))));
    }

    public List<Orcamento> getByPeriodoCategoriaComInterseccoes(Calendar dataInicio, Calendar dataFim, CategoriaDespesa categoria) {
        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ORCAMENTO_BY_CATEG_DATA_COM_INTERSECCOES
                , new String[]{ categoria.getId().toString()
                              ,DateFormatter.formataAnoMesDia(dataInicio)
                              ,DateFormatter.formataAnoMesDia(dataFim) });
        while (cursor.moveToNext()){
            Orcamento orcamento = new Orcamento();
            pegaDadosCursor(cursor, orcamento);
            orcamentos.add(orcamento);
        }
        return orcamentos;
    }

    public List<Orcamento> getListaPeriodo(Calendar dataInicio, Calendar dataFim) {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_ORCAMENTOS_POR_DATA,
                new String[]{DateFormatter.formataAnoMesDia(dataInicio), DateFormatter.formataAnoMesDia(dataFim)});
        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        carregaListaDoCursor(cursor, orcamentos);
        cursor.close();
        return orcamentos;

    }

    private void carregaListaDoCursor(Cursor cursor, List<Orcamento> orcamentos) {
        while(!cursor.isAfterLast()){
            Orcamento orcamento = new Orcamento();
            pegaDadosCursor(cursor, orcamento);
            orcamentos.add(orcamento);
        }
    }
}
