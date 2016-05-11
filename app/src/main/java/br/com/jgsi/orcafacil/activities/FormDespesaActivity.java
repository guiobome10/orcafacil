package br.com.jgsi.orcafacil.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormDespesasFragment;

/**
 * Created by guilherme.costa on 29/03/2016.
 */
public class FormDespesaActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.despesa_form_container);
        getIntent().getExtras();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.despesas_view, new FormDespesasFragment());
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
