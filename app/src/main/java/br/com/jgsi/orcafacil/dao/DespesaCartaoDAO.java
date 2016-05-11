package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.spec.DESedeKeySpec;

import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.factory.FaturaFactory;
import br.com.jgsi.orcafacil.model.Bandeira;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.model.Icone;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Periodicidade;
import br.com.jgsi.orcafacil.model.StatusOrcamento;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilhermewesley on 14/03/2016.
 */
public class DespesaCartaoDAO implements CrudDAO<DespesaCartao> {

    private DAO dao;
    private Context context;
    private FaturaDAO faturaDAO;

    public DespesaCartaoDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public List<DespesaCartao> listar() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_DESPESAS_CARTAO_ORDENADA_POR_DATA, null);
        List<DespesaCartao> despesasCartao =  new ArrayList<DespesaCartao>();
        carregaListaDoCursor(cursor, despesasCartao);
        cursor.close();
        return despesasCartao;
    }

    private void carregaListaDoCursor(Cursor cursor, List<DespesaCartao> despesasCartao) {
        while(cursor.moveToNext()){
            DespesaCartao despesaCartao = new DespesaCartao();
            despesaCartao.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_DESP_CARTAO_C_ID)));
            despesaCartao.setValor(cursor.getDouble(cursor.getColumnIndex(Statements.TB_DESP_CARTAO_C_VALOR)));
            despesaCartao.setData(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_DESP_CARTAO_C_DATA))));
            despesaCartao.setDetalhes(cursor.getString(cursor.getColumnIndex(Statements.TB_DESP_CARTAO_C_DETALHES)));
            despesaCartao.setQuantidadeParcelas(cursor.getInt(cursor.getColumnIndex(Statements.TB_DESP_CARTAO_C_QT_PARCELAS)));
            despesaCartao.setNumeroParcela(cursor.getInt(cursor.getColumnIndex(Statements.TB_DESP_CARTAO_C_NR_PARCELA)));
            Fatura fatura = new Fatura();
            fatura.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_FATURA_J_ID_FATURA)));
            fatura.setDataInicialCiclo(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_DT_INI_CICLO_FATURA))));
            fatura.setDataFinalCiclo(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_DT_FIN_CICLO_FATURA))));
            fatura.setDataVencimento(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_FATURA_J_DT_VENC_FATURA))));
            fatura.setValor(cursor.getDouble(cursor.getColumnIndex(Statements.TB_FATURA_J_VALOR_FATURA)));
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_CARTAO_J_ID)));
            cartaoDeCredito.setUltimosQuatroDigitos(cursor.getString(cursor.getColumnIndex(Statements.TB_CARTAO_J_ULTIMOS_QUATRO_DIGITOS)));
            cartaoDeCredito.setBandeira(Bandeira.valueOf(cursor.getString(cursor.getColumnIndex(Statements.TB_CARTAO_J_BANDEIRA))));
            Conta conta = new Conta();
            conta.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_CARTAO_J_ID_CONTA)));
            cartaoDeCredito.setConta(conta);
            fatura.setCartaoDeCredito(cartaoDeCredito);
            despesaCartao.setFatura(fatura);
            CategoriaDespesa categoria = new CategoriaDespesa();
            categoria.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_CAT_DESP_J_ID)));
            categoria.setNome(cursor.getString(cursor.getColumnIndex(Statements.TB_CAT_DESP_J_NOME)));
            categoria.setImagem(CategoriaFactory.criaIcone(context.getResources(), cursor.getInt(cursor.getColumnIndex(Statements.TB_CAT_DESP_J_IMAGEM))));
            despesaCartao.setCategoria(categoria);
            Orcamento orcamento = null;
            if( cursor.getString(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_DATA_INICIO)) != null){
                orcamento = new Orcamento();
                orcamento.setCategoria(categoria);
                orcamento.setId(cursor.getLong(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_ID)));
                orcamento.setValor(cursor.getDouble(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_VALOR)));
                orcamento.setSaldo(cursor.getDouble(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_SALDO)));
                orcamento.setDataInicio(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_DATA_INICIO))));
                orcamento.setDataFim(DateFormatter.formataAnoMesDia(cursor.getString(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_DATA_FIM))));
                orcamento.setStatus(StatusOrcamento.get(cursor.getInt(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_STATUS))));
                orcamento.setPeriodicidade(Periodicidade.get(cursor.getInt(cursor.getColumnIndex(Statements.TB_ORCAMENTO_J_PERIODICIDADE))));
            }
            despesaCartao.setOrcamento(orcamento);
            despesasCartao.add(despesaCartao);
        }
    }

    public void salvar(DespesaCartao despesaCartao){
        if(despesaCartao.getId() == null){
            inserir(despesaCartao);
        } else {
            atualizar(despesaCartao);
        }
    }

    public void inserir(DespesaCartao despesaCartao){
        faturaDAO = new FaturaDAO(context);
        if(despesaCartao.isParcelada()){
            List<Fatura> faturas = FaturaFactory.geraFaturas(despesaCartao);
            for(int i = 0; i < faturas.size(); i++){
                faturaDAO.merge(faturas.get(i));
                DespesaCartao despesa = (DespesaCartao) despesaCartao.clone();
                despesa.setNumeroParcela(i + 1);
                despesa.setData(DateUtil.addMonthToDate(despesa.getData(), i));
                despesa.setFatura(faturas.get(i));
                despesa.calculaValorParcela();
                dao.inserir(Statements.TB_DESPESA_CARTAO_NAME, toContentValues(despesa));
            }
        } else {
            dao.inserir(Statements.TB_DESPESA_CARTAO_NAME, toContentValues(despesaCartao));
            despesaCartao.aumentaFatura();
            faturaDAO.salvar(despesaCartao.getFatura());
        }
    }

    public void atualizar(DespesaCartao despesaCartao){
        dao.atualizar(Statements.TB_DESPESA_CARTAO_NAME, toContentValues(despesaCartao), "id=?", new String[]{despesaCartao.getId().toString()});
    }

    public void remover(DespesaCartao despesaCartao){
        dao.deletar(Statements.TB_DESPESA_CARTAO_NAME, "id=?", new String[]{despesaCartao.getId().toString()});
        faturaDAO = new FaturaDAO(context);
        despesaCartao.diminuiFatura();
        faturaDAO.atualizar(despesaCartao.getFatura());
    }


    private ContentValues toContentValues(DespesaCartao despesaCartao) {
        ContentValues cv = new ContentValues();
        cv.put(Statements.TB_DESP_CARTAO_C_ID, despesaCartao.getId());
        cv.put(Statements.TB_DESP_CARTAO_C_ID_CATEGORIA, despesaCartao.getCategoria().getId());
        if(despesaCartao.getOrcamento() != null){
            cv.put(Statements.TB_DESP_CARTAO_C_ID_ORCAMENTO, despesaCartao.getOrcamento().getId());
        }
        cv.put(Statements.TB_DESP_CARTAO_C_ID_FATURA, despesaCartao.getFatura().getId());
        cv.put(Statements.TB_DESP_CARTAO_C_VALOR, despesaCartao.getValor());
        cv.put(Statements.TB_DESP_CARTAO_C_DATA, despesaCartao.getDataFormatadaParaBase());
        cv.put(Statements.TB_DESP_CARTAO_C_DETALHES, despesaCartao.getDetalhes());
        cv.put(Statements.TB_DESP_CARTAO_C_QT_PARCELAS, despesaCartao.getQuantidadeParcelas());
        return cv;
    }

    public List<DespesaCartao> getByFatura(Fatura fatura) {
        Cursor cursor = queryDespesaCartaoPorFatura(fatura);
        List<DespesaCartao> despesasCartao = new ArrayList<DespesaCartao>();
        carregaListaDoCursor(cursor, despesasCartao);
        cursor.close();
        return despesasCartao;
    }

    private Cursor queryDespesaCartaoPorFatura(Fatura fatura) {
        return dao.exeqQuery(Statements.SELECT_ALL_DESPESAS_CARTAO_POR_FATURA, new String[]{fatura.getId().toString()});
    }

    public long getCountCartao(Fatura fatura) {
        return queryDespesaCartaoPorFatura(fatura).getCount();
    }

    public void excluir(Fatura fatura) {
        dao.deletar(Statements.TB_DESPESA_CARTAO_NAME, Statements.TB_DESP_CARTAO_C_ID_FATURA+"=?", new String[]{fatura.getId().toString()});
    }

}
