package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.model.Bandeira;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class CartaoDeCreditoDAO {

    private ContaDAO contaDAO;
    private DAO dao;
    private Context context;

    public CartaoDeCreditoDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public List<CartaoDeCredito> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL_CARTOES_DE_CREDITO, null);
        List<CartaoDeCredito> cartoesDeCredito =  new ArrayList<CartaoDeCredito>();
        carregaListaDoCursor(cursor, cartoesDeCredito);

        cursor.close();
        return cartoesDeCredito;
    }

    public List<CartaoDeCredito> listarCartoesComSaldoFatura(){
        List<CartaoDeCredito> cartoesDeCredito = getLista();
        FaturaDAO faturaDAO = new FaturaDAO(context);
        for(CartaoDeCredito cartaoDeCredito : cartoesDeCredito){
            cartaoDeCredito.setFaturas(Arrays.asList(faturaDAO.getByCartaoData(cartaoDeCredito, Calendar.getInstance())));
        }
        return cartoesDeCredito;
    }

    private void carregaListaDoCursor(Cursor cursor, List<CartaoDeCredito> cartoesDeCredito) {
        while(cursor.moveToNext()){
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setId(cursor.getLong(cursor.getColumnIndex("id")));
            Conta conta = new Conta();
            cartaoDeCredito.setConta(conta);
            conta.setId(cursor.getLong(cursor.getColumnIndex("id_conta")));
            conta.setNome(cursor.getString(cursor.getColumnIndex("nome_conta")));
            conta.setSaldo(cursor.getDouble(cursor.getColumnIndex("saldo_conta")));
            conta.setMoeda(Moeda.get(cursor.getString(cursor.getColumnIndex("moeda_conta"))));
            cartaoDeCredito.setBandeira(Bandeira.valueOf(cursor.getString(cursor.getColumnIndex("bandeira"))));
            cartaoDeCredito.setUltimosQuatroDigitos(cursor.getString(cursor.getColumnIndex("ultimos_quatro_digitos")));
            cartoesDeCredito.add(cartaoDeCredito);
        }
    }

    public void inserir(CartaoDeCredito cartaoDeCredito){
        dao.inserir(Tabelas.TB_CARTAO_DE_CREDITO_NAME, toContentValues(cartaoDeCredito));
    }

    public void atualizar(CartaoDeCredito cartaoDeCredito){
        dao.atualizar(Tabelas.TB_CARTAO_DE_CREDITO_NAME, toContentValues(cartaoDeCredito), "id=?", new String[]{cartaoDeCredito.getId().toString()});
    }

    public void deletar(CartaoDeCredito cartaoDeCredito){
        dao.deletar(Tabelas.TB_CARTAO_DE_CREDITO_NAME, "id=?", new String[]{cartaoDeCredito.getId().toString()});
    }


    private ContentValues toContentValues(CartaoDeCredito cartaoDeCredito) {
        ContentValues cv = new ContentValues();
        cv.put("id", cartaoDeCredito.getId());
        cv.put("id_conta", cartaoDeCredito.getConta().getId());
        cv.put("ultimos_quatro_digitos", cartaoDeCredito.getUltimosQuatroDigitos());
        cv.put("bandeira", String.valueOf(cartaoDeCredito.getBandeira()));
        return cv;
    }

    public List<CartaoDeCredito> getByConta(Conta conta) {
        Cursor cursor = queryCartaoDeCreditoPorConta(conta);
        List<CartaoDeCredito> cartoesDeCredito = new ArrayList<CartaoDeCredito>();
        carregaListaDoCursor(cursor, cartoesDeCredito);
        cursor.close();
        return cartoesDeCredito;
    }

    private Cursor queryCartaoDeCreditoPorConta(Conta conta) {
        return dao.exeqQuery(Statements.SELECT_CARTAO_DE_CREDITO_POR_CONTA, new String[]{conta.getId().toString()});
    }

    public long getCountConta(Conta conta) {
        return queryCartaoDeCreditoPorConta(conta).getCount();
    }

    public void excluir(Conta conta) {
        dao.deletar(Tabelas.TB_CARTAO_DE_CREDITO_NAME, "id_conta=?", new String[]{conta.getId().toString()});
    }

}
