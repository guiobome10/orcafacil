package br.com.jgsi.orcafacil.dao;

import android.content.Context;
import android.database.Cursor;

import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 18/03/2016.
 */
public class SaldoDAO {

    private Context context;
    private DAO dao;

    public SaldoDAO(Context context) {
        this.context = context;
        this.dao = new DAO(context);
    }

    public Double getTotalDespesasMesAtual(){
        String[] parametros = new String[]{DateFormatter.formataAnoMesDia(DateUtil.dataInicioMesAtual()), DateFormatter.formataAnoMesDia(DateUtil.dataFimMesAtual())};
        Cursor cursor = dao.exeqQuery(Statements.SELECT_TOTAL_DESPESAS_MES_ATUAL, parametros);
        if(cursor.moveToNext()){
            return cursor.getDouble(cursor.getColumnIndex(Statements.AGG_SALDO_DESPESAS));
        }
        return 0.;
    }

    public Double getTotalReceitasMesAtual(){
        String[] parametros = new String[]{DateFormatter.formataAnoMesDia(DateUtil.dataInicioMesAtual()), DateFormatter.formataAnoMesDia(DateUtil.dataFimMesAtual())};
        Cursor cursor = dao.exeqQuery(Statements.SELECT_TOTAL_RECEITAS_MES_ATUAL, parametros);
        if(cursor.moveToNext()){
            return cursor.getDouble(cursor.getColumnIndex(Statements.AGG_SALDO_RECEITAS));
        }
        return 0.;
    }

}
