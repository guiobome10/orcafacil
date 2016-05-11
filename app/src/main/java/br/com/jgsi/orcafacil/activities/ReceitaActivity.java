package br.com.jgsi.orcafacil.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.io.Serializable;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormContasFragment;
import br.com.jgsi.orcafacil.fragment.FormReceitasFragment;
import br.com.jgsi.orcafacil.fragment.ListaContasFragment;
import br.com.jgsi.orcafacil.fragment.ListaReceitasFragment;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Receita;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ReceitaActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receitas);
        if(getIntent().getExtras() == null){
            listaReceitas();
        } else {
            selecionarReceita((Receita)getIntent().getExtras().getSerializable(getResources().getString(R.string.receita)));
        }
    }

    public void listaReceitas() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.receitas_view, new ListaReceitasFragment());
        fragmentTransaction.commit();
    }

    public void novaReceita(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.receitas_view, new FormReceitasFragment());
        ft.commit();
    }

    public void selecionarReceita(Receita receita) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.receita), receita);

        FormReceitasFragment formReceitasFragment = new FormReceitasFragment();
        formReceitasFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.receitas_view, formReceitasFragment);
        ft.commit();
    }

}
