package br.com.jgsi.orcafacil.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormOrcamentosFragment;

/**
 * Created by guilherme.costa on 04/04/2016.
 */
public class FormOrcamentoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orcamento_form_container);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.orcamentos_view, new FormOrcamentosFragment());
        ft.commit();

    }
}
