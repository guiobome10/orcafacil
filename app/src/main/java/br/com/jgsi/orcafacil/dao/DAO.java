package br.com.jgsi.orcafacil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.jgsi.orcafacil.sql.Statements;
import br.com.jgsi.orcafacil.sql.Tabelas;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class DAO {

    private SQLiteOpenHelper helper;
    private static final String DATABASE = "orcaFacilDB";
    private static final int VERSION = 8;
    private Context context;

    public DAO(Context context) {
        this.context = context;
        helper = new SQLiteOpenHelper(context, DATABASE, null, VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(Tabelas.TB_CATEGORIA_DESPESA_CREATE);
                db.execSQL(Tabelas.TB_CATEGORIA_RECEITA_CREATE);
                db.execSQL(Tabelas.TB_CONTA_CREATE);
                db.execSQL(Tabelas.TB_DESPESA_CREATE);
                db.execSQL(Tabelas.TB_ORCAMENTO_CREATE);
                db.execSQL(Tabelas.TB_RECEITA_CREATE);
                db.execSQL(Tabelas.TB_CARTAO_DE_CREDITO_CREATE);
                db.execSQL(Tabelas.TB_FATURA_CREATE);
                db.execSQL(Tabelas.TB_DESPESA_CARTAO_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL(Tabelas.TB_CATEGORIA_DESPESA_DROP);
                db.execSQL(Tabelas.TB_CATEGORIA_RECEITA_DROP);
                db.execSQL(Tabelas.TB_CONTA_DROP);
                db.execSQL(Tabelas.TB_DESPESA_DROP);
                db.execSQL(Tabelas.TB_ORCAMENTO_DROP);
                db.execSQL(Tabelas.TB_RECEITA_DROP);
                db.execSQL(Tabelas.TB_CARTAO_DE_CREDITO_DROP);
                db.execSQL(Tabelas.TB_FATURA_DROP);
                db.execSQL(Tabelas.TB_DESPESA_CARTAO_DROP);

                onCreate(db);
            }
        };
    }

    public long inserir(String tabela, ContentValues contentValues) {
        long id = helper.getWritableDatabase().insert(tabela, null, contentValues);
        helper.close();
        return  id;
    }

    public void atualizar(String tabela, ContentValues contentValues, String whereClause, String[] whereArgs) {
        helper.getWritableDatabase().update(tabela, contentValues, whereClause, whereArgs);
        helper.close();
    }

    public void deletar(String tabela, String whereClause, String[] whereArgs) {
        helper.getWritableDatabase().delete(tabela, whereClause, whereArgs);
        helper.close();
    }

    public Cursor exeqQuery(String query, String[] args ){
        /*Log.i("br.com.jgsi.debuginfo", "Query executada: ");
        Log.i("br.com.jgsi.debuginfo", query);*/
        if(args != null) Log.i("br.com.jgsi.debuginfo", args.toString());
        return helper.getReadableDatabase().rawQuery(query, args);
    }

    public Context getContext() {
        return context;
    }

    public List<Object> getLista(){ return null;};
}
