package br.com.jgsi.orcafacil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.adapter.OrcamentoPagerAdapter;
import br.com.jgsi.orcafacil.fragment.FormOrcamentosFragment;
import br.com.jgsi.orcafacil.fragment.ListaOrcamentosFragment;
import br.com.jgsi.orcafacil.model.Orcamento;

/**
 * Created by guilherme.costa on 28/01/2016.
 */
public class OrcamentoActivity extends FragmentActivity {

    private ViewPager viewPager;
    private OrcamentoPagerAdapter orcamentoPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orcamentos);
        viewPager = (ViewPager) findViewById(R.id.orcamentos_view_pager);
    }

    @Override
    protected void onResume() {
        if(getIntent().getStringExtra(getResources().getString(R.string.orcamento)) == null){
            listaOrcamentos();
        } else {
            novoOrcamento();
        }
        super.onResume();
    }

    public void listaOrcamentos() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        orcamentoPagerAdapter = new OrcamentoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(orcamentoPagerAdapter);
        viewPager.setCurrentItem(orcamentoPagerAdapter.getCentralItemPosition());
        fragmentTransaction.replace(R.id.orcamentos_view, orcamentoPagerAdapter.getCentralItem());
        fragmentTransaction.commit();
    }

    public void novoOrcamento(){
        Intent irParaFormOrcamento = new Intent(this, FormOrcamentoActivity.class);
        startActivity(irParaFormOrcamento);
    }

    public void selecionarOrcamento(Orcamento orcamento) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.orcamento), orcamento);

        FormOrcamentosFragment formOrcamentosFragment = new FormOrcamentosFragment();
        formOrcamentosFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.orcamentos_view, formOrcamentosFragment);
        ft.commit();
    }
}
