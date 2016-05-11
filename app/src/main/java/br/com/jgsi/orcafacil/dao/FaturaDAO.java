package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.model.Bandeira;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class FaturaDAO implements CrudDAO<Fatura> {

    private ContaDAO contaDAO;
    private DAO dao;
    private Context context;

    public FaturaDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public List<Fatura> listar() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_FATURAS, null);
        List<Fatura> faturas =  new ArrayList<Fatura>();
        carregaListaDoCursor(cursor, faturas);
        cursor.close();
        return faturas;
    }

    private void carregaListaDoCursor(Cursor cursor, List<Fatura> faturas) {
        while(cursor.moveToNext()){
            Fatura fatura = new Fatura();
            fatura.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_FATURA_C_ID)));
            fatura.setDataInicialCiclo(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_C_DATA_INICIAL_CICLO))));
            fatura.setDataFinalCiclo(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_C_DATA_FINAL_CICLO))));
            fatura.setDataVencimento(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_C_DATA_VENCIMENTO))));
            fatura.setValor(cursor.getDouble(cursor.getColumnIndex(Statements.TB_FATURA_C_VALOR)));
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            Conta conta = new Conta();
            cartaoDeCredito.setConta(conta);
            fatura.setCartaoDeCredito(cartaoDeCredito);
            conta.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_FATURA_J_ID_CONTA)));
            conta.setNome(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_NOME_CONTA)));
            conta.setSaldo(cursor.getDouble(cursor.getColumnIndex(Statements.TB_FATURA_J_SALDO_CONTA)));
            conta.setMoeda(Moeda.get(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_MOEDA_CONTA))));
            cartaoDeCredito.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_FATURA_J_ID_CARTAO)));
            cartaoDeCredito.setBandeira(Bandeira.valueOf(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_BANDEIRA_CARTAO))));
            cartaoDeCredito.setUltimosQuatroDigitos(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_ULTIMOS_QUATRO_DIGITOS_CARTAO)));
            faturas.add(fatura);
        }
    }

    public void salvar(Fatura fatura){
        if(fatura.getId() == null){
            inserir(fatura);
        } else {
            atualizar(fatura);
        }
    }

    public void inserir(Fatura fatura){
        fatura.setId(dao.inserir(Statements.TB_FATURA_NAME, toContentValues(fatura)));
    }

    public void atualizar(Fatura fatura){
        dao.atualizar(Statements.TB_FATURA_NAME, toContentValues(fatura), "id=?", new String[]{fatura.getId().toString()});
    }

    public void remover(Fatura fatura){
        dao.deletar(Statements.TB_FATURA_NAME, "id=?", new String[]{fatura.getId().toString()});
        DespesaCartaoDAO despesaCartaoDAO = new DespesaCartaoDAO(context);
        despesaCartaoDAO.excluir(fatura);
    }


    private ContentValues toContentValues(Fatura fatura) {
        ContentValues cv = new ContentValues();
        cv.put(Statements.TB_FATURA_C_ID, fatura.getId());
        cv.put(Statements.TB_FATURA_C_ID_CARTAO_DE_CREDITO, fatura.getCartaoDeCredito().getId());
        cv.put(Statements.TB_FATURA_C_DATA_INICIAL_CICLO, fatura.getDataInicialCicloFormatadaParaBase());
        cv.put(Statements.TB_FATURA_C_DATA_FINAL_CICLO, fatura.getDataFinalCicloFormatadaParaBase());
        cv.put(Statements.TB_FATURA_C_DATA_VENCIMENTO, fatura.getDataVencimentoFormatadaParaBase());
        cv.put(Statements.TB_FATURA_C_VALOR, fatura.getValor());
        return cv;
    }

    public List<Fatura> getByCartao(CartaoDeCredito cartaoDeCredito) {
        Cursor cursor = queryFaturaPorCartao(cartaoDeCredito);
        List<Fatura> faturas = new ArrayList<Fatura>();
        carregaListaDoCursor(cursor, faturas);
        cursor.close();
        return faturas;
    }

    public Fatura getByCartaoDataInicioFimVencimento(Fatura fatura) {
        Cursor cursor = queryFaturaPorDataInicioFimVencimento(fatura.getCartaoDeCredito(), fatura.getDataInicialCiclo(), fatura.getDataFinalCiclo(), fatura.getDataVencimento());
        List<Fatura> faturas = new ArrayList<Fatura>();
        carregaListaDoCursor(cursor, faturas);
        cursor.close();
        return faturas.size() > 0 ? faturas.get(0) : null;
    }

    public Fatura getByCartaoData(CartaoDeCredito cartaoDeCredito, Calendar data) {
        Cursor cursor = queryFaturaPorCartao(cartaoDeCredito);
        List<Fatura> faturas = new ArrayList<Fatura>();
        carregaListaDoCursor(cursor, faturas);
        cursor.close();
        for(Fatura fatura : faturas){
            if(DateUtil.dataEhMenorOuIgual(data, fatura.getDataFinalCiclo())
            && DateUtil.dataEhMaiorOuIgual(data, fatura.getDataInicialCiclo())){
                return  fatura;
            }
        }
        return null;
    }

    private Cursor queryFaturaPorCartao(CartaoDeCredito cartaoDeCredito) {
        return dao.exeqQuery(Statements.SELECT_FATURA_POR_CARTAO_DE_CREDITO, new String[]{cartaoDeCredito.getId().toString()});
    }

    private Cursor queryFaturaPorDataInicioFimVencimento(CartaoDeCredito cartaoDeCredito, Calendar dataInicialCiclo, Calendar dataFinalCiclo, Calendar dataVencimento) {
        return dao.exeqQuery(Statements.SELECT_FATURA_POR_CARTAO_DATA_INICIO_FIM_VENCIMENTO
                , new String[]{cartaoDeCredito.getId().toString()
                , DateFormatter.formataAnoMesDia(dataInicialCiclo)
                , DateFormatter.formataAnoMesDia(dataFinalCiclo)
                , DateFormatter.formataAnoMesDia(dataVencimento)});
    }

    public long getCountCartao(CartaoDeCredito cartaoDeCredito) {
        return queryFaturaPorCartao(cartaoDeCredito).getCount();
    }

    public void excluir(CartaoDeCredito cartaoDeCredito) {
        dao.deletar(Statements.TB_FATURA_NAME, Statements.TB_FATURA_C_ID_CARTAO_DE_CREDITO + "=?", new String[]{cartaoDeCredito.getId().toString()});
    }

    public Fatura merge(Fatura fatura) {
        Fatura preExistente = getByCartaoDataInicioFimVencimento(fatura);
        if(preExistente != null){
            fatura.setId(preExistente.getId());
            preExistente.aumenta(fatura.getValor());
            atualizar(preExistente);
            return preExistente;
        }else {
            inserir(fatura);
        }
        return fatura;
    }
}
