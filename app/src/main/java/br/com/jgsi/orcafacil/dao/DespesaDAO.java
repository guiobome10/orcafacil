package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Periodicidade;
import br.com.jgsi.orcafacil.model.StatusOrcamento;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;
import br.com.jgsi.orcafacil.util.DateFormatter;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class DespesaDAO {

    private ContaDAO contaDAO;
    private DAO dao;
    private Context context;

    public DespesaDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public List<Despesa> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_DESPESAS_ORDENADO_POR_DATA, null);
        List<Despesa> despesas =  new ArrayList<Despesa>();
        carregaListaDoCursor(cursor, despesas);

        cursor.close();
        return despesas;
    }

    public List<Despesa> getListaPeriodo(Calendar dataInicio, Calendar dataFim){
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_DESPESAS_POR_DATA,
                new String[]{DateFormatter.formataAnoMesDia(dataInicio), DateFormatter.formataAnoMesDia(dataFim)});
        List<Despesa> despesas = new ArrayList<Despesa>();
        carregaListaDoCursor(cursor, despesas);
        cursor.close();
        return despesas;
    }

    private void carregaListaDoCursor(Cursor cursor, List<Despesa> despesas) {
        while(cursor.moveToNext()){
            Despesa despesa = new Despesa();
            despesa.setId(cursor.getLong(cursor.getColumnIndex("id")));
            despesa.setData(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex("data"))));
            despesa.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            despesa.setDetalhes(cursor.getString(cursor.getColumnIndex("detalhes")));
            CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
            despesa.setCategoria(categoriaDespesa);
            categoriaDespesa.setId(cursor.getLong(cursor.getColumnIndex("id_categoria")));
            categoriaDespesa.setNome(cursor.getString(cursor.getColumnIndex("nome_categoria")));
            categoriaDespesa.setImagem(CategoriaFactory.criaIcone(dao.getContext().getResources(), cursor.getInt(cursor.getColumnIndex("imagem_categoria"))));
            Conta conta = new Conta();
            despesa.setConta(conta);
            conta.setId(cursor.getLong(cursor.getColumnIndex("id_conta")));
            conta.setNome(cursor.getString(cursor.getColumnIndex("nome_conta")));
            conta.setSaldo(cursor.getDouble(cursor.getColumnIndex("saldo_conta")));
            conta.setMoeda(Moeda.get(cursor.getString(cursor.getColumnIndex("moeda_conta"))));
            Orcamento orcamento = null;
            if(cursor.getString(cursor.getColumnIndex("data_inicio_orcamento")) != null){
                orcamento = new Orcamento();
                orcamento.setId(cursor.getLong(cursor.getColumnIndex("id_orcamento")));
                orcamento.setValor(cursor.getDouble(cursor.getColumnIndex("valor_orcamento")));
                orcamento.setSaldo(cursor.getDouble(cursor.getColumnIndex("saldo_orcamento")));
                orcamento.setDataInicio(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex("data_inicio_orcamento"))));
                orcamento.setDataFim(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex("data_fim_orcamento"))));
                orcamento.setStatus(StatusOrcamento.get(cursor.getInt(cursor.getColumnIndex("status_orcamento"))));
                orcamento.setPeriodicidade(Periodicidade.get(cursor.getInt(cursor.getColumnIndex("periodicidade_orcamento"))));
                orcamento.setCategoria(categoriaDespesa);
            }
            despesa.setOrcamento(orcamento);
            despesas.add(despesa);
        }
    }

    public void inserir(Despesa despesa){
        dao.inserir(Tabelas.TB_DESPESA_NAME, toContentValues(despesa));
        despesa.getConta().saca(despesa.getValor());
        this.contaDAO = new ContaDAO(context);
        contaDAO.atualizar(despesa.getConta());
    }

    public void atualizar(Despesa despesa){
        dao.atualizar(Tabelas.TB_DESPESA_NAME, toContentValues(despesa), "id=?", new String[]{despesa.getId().toString()});
        contaDAO = new ContaDAO(context);
        despesa.getConta().saca(despesa.getValor());
        contaDAO.atualizar(despesa.getConta());
    }

    public void deletar(Despesa despesa){
        dao.deletar(Tabelas.TB_DESPESA_NAME, "id=?", new String[]{despesa.getId().toString()});
    }


    private ContentValues toContentValues(Despesa despesa) {
        ContentValues cv = new ContentValues();
        cv.put("id_categoria", despesa.getCategoria().getId());
        cv.put("id_conta", despesa.getConta().getId());
        if(despesa.getOrcamento() != null) cv.put("id_orcamento", despesa.getOrcamento().getId());
        cv.put("data", despesa.getDataFormatadaParaBase());
        cv.put("valor", despesa.getValor());
        cv.put("detalhes", despesa.getDetalhes());
        return cv;
    }

    public List<Despesa> getByConta(Conta conta) {
        Cursor cursor = queryDespesaPorConta(conta);
        List<Despesa> despesas = new ArrayList<Despesa>();
        carregaListaDoCursor(cursor, despesas);
        cursor.close();
        return despesas;
    }

    private Cursor queryDespesaPorConta(Conta conta) {
        return dao.exeqQuery(Statements.SELECT_DESPESAS_POR_CONTA, new String[]{conta.getId().toString()});
    }

    public long getCountConta(Conta conta) {
        return queryDespesaPorConta(conta).getCount();
    }

    public void excluir(Conta conta) {
        dao.deletar(Tabelas.TB_DESPESA_NAME, "id_conta=?", new String[]{conta.getId().toString()});
    }

}
