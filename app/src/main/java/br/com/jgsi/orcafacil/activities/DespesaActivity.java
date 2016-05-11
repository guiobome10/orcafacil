package br.com.jgsi.orcafacil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.adapter.DespesaPagerAdapter;
import br.com.jgsi.orcafacil.fragment.FormDespesasFragment;
import br.com.jgsi.orcafacil.model.Despesa;

/**
 * Created by guilherme.costa on 28/01/2016.
 */
public class DespesaActivity extends FragmentActivity {

    private ViewPager viewPager;
    private DespesaPagerAdapter despesaPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.despesas);
        viewPager = (ViewPager) findViewById(R.id.despesas_view_pager);
    }

    @Override
    protected void onResume() {

        if(getIntent().getExtras() == null){
            listaDespesas();
        } else {
            selecionarDespesa((Despesa)getIntent().getExtras().getSerializable(getResources().getString(R.string.despesa)));
        }
        super.onResume();
    }

    public void listaDespesas() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        despesaPagerAdapter = new DespesaPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(despesaPagerAdapter);
        viewPager.setCurrentItem(despesaPagerAdapter.getCentralItemPosition());
        fragmentTransaction.replace(R.id.despesas_view, despesaPagerAdapter.getCentralItem());
        fragmentTransaction.commit();
    }

    public void novaDespesa(){
        Intent irParaFormDespesa = new Intent(this, FormDespesaActivity.class);
        startActivity(irParaFormDespesa);
    }

    public void selecionarDespesa(Despesa despesa) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.despesa), despesa);

        Intent irParaFormDespesa = new Intent(this, FormDespesaActivity.class);
        irParaFormDespesa.putExtras(args);
        FormDespesasFragment formDespesasFragment = new FormDespesasFragment();
        formDespesasFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.despesas_view, formDespesasFragment);
        ft.commit();
    }

}
