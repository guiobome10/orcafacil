package br.com.jgsi.orcafacil.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormCategoriasDespesaFragment;
import br.com.jgsi.orcafacil.fragment.ListaCategoriasDespesaFragment;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;

/**
 * Created by guilherme.costa on 29/01/2016.
 */
public class CategoriaDespesaActivity extends FragmentActivity {


    private boolean hasExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        hasExtra = getIntent().getBooleanExtra(getResources().getString(R.string.nova), false);

        if(hasExtra){
            novaCategoriaDespesa();
        }else {
            listaCategoriaDespesas();
        }

    }

    public void novaCategoriaDespesa(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.categorias_view, new FormCategoriasDespesaFragment());
        ft.commit();
    }

    public void listaCategoriaDespesas(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.categorias_view, new ListaCategoriasDespesaFragment());
        fragmentTransaction.commit();
    }

    public void selecionarCategoriaDespesa(CategoriaDespesa categoria) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.categoria), categoria);

        FormCategoriasDespesaFragment formCategoriaDespesasFragment = new FormCategoriasDespesaFragment();
        formCategoriaDespesasFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.categorias_view, formCategoriaDespesasFragment);
        ft.commit();
    }


    public void retornaAoChamador(CategoriaDespesa categoria) {
        if(hasExtra){
            //Devolver a categoria cadastrada para o chamador(FormDespesa).
            getIntent().putExtra(getResources().getString(R.string.categoria_id), categoria.getId());
            getIntent().putExtra(getResources().getString(R.string.categoria_drawable_id), categoria.getImagem().getDrawableID());
            setResult(Activity.RESULT_OK, getIntent());
            finish();
        } else {
            listaCategoriaDespesas();
        }
    }
}
