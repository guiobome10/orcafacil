package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public class ContaDAO {


    private DAO dao;
    private ReceitaDAO receitaDAO;
    private DespesaDAO despesaDAO;
    private Context context;

    public ContaDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public List<Conta> getLista() {
        Cursor cursor = dao.exeqQuery(Statements.SELECT_ALL + Tabelas.TB_CONTA_NAME, null);
        List<Conta> contas =  new ArrayList<Conta>();
        while(cursor.moveToNext()){
            Conta conta = new Conta();
            conta.setId(cursor.getLong(cursor.getColumnIndex("id")));
            conta.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            conta.setMoeda(Moeda.get(cursor.getString(cursor.getColumnIndex("moeda"))));
            conta.setSaldo(cursor.getDouble(cursor.getColumnIndex("saldo")));
            contas.add(conta);
        }

        cursor.close();
        return contas;
    }

    public void inserir(Conta conta){
        dao.inserir(Tabelas.TB_CONTA_NAME, toContentValues(conta));
    }

    public void atualizar(Conta conta){
        dao.atualizar(Tabelas.TB_CONTA_NAME, toContentValues(conta), "id=?", new String[]{conta.getId().toString()});
    }

    public void deletar(Conta conta){
        dao.deletar(Tabelas.TB_CONTA_NAME, "id=?", new String[]{conta.getId().toString()});
        this.receitaDAO = new ReceitaDAO(context);
        this.despesaDAO = new DespesaDAO(context);
        receitaDAO.excluir(conta);
        despesaDAO.excluir(conta);
    }


    private ContentValues toContentValues(Conta conta) {
        ContentValues cv = new ContentValues();
        cv.put("nome", conta.getNome());
        cv.put("saldo", conta.getSaldo());
        cv.put("moeda", conta.getMoeda().nome());
        return cv;
    }

    public boolean contaPodeSerAlterada(Conta conta) {
        this.receitaDAO = new ReceitaDAO(context);
        this.despesaDAO = new DespesaDAO(context);
        return receitaDAO.getCountConta(conta) == 0 && despesaDAO.getCountConta(conta) == 0;
    }
}
