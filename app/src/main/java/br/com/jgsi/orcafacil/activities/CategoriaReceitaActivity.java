package br.com.jgsi.orcafacil.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.constants.RequestCode;
import br.com.jgsi.orcafacil.fragment.FormCategoriasReceitaFragment;
import br.com.jgsi.orcafacil.fragment.ListaCategoriasReceitaFragment;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaReceita;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class CategoriaReceitaActivity extends FragmentActivity {

    private boolean hasExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        hasExtra = getIntent().getBooleanExtra(getResources().getString(R.string.nova), false);

        if(hasExtra){
            novaCategoriaReceita();
        }else {
            listaCategoriaReceitas();
        }

    }

    public void novaCategoriaReceita(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.categorias_view, new FormCategoriasReceitaFragment());
        ft.commit();
    }

    public void listaCategoriaReceitas(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.categorias_view, new ListaCategoriasReceitaFragment());
        fragmentTransaction.commit();
    }

    public void selecionarCategoriaReceita(CategoriaReceita categoria) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.categoria), categoria);

        FormCategoriasReceitaFragment formCategoriaReceitasFragment = new FormCategoriasReceitaFragment();
        formCategoriaReceitasFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.categorias_view, formCategoriaReceitasFragment);
        ft.commit();
    }


    public void retornaAoChamador(CategoriaReceita categoria) {
        if(hasExtra){
            //Devolver a categoria cadastrada para o chamador(FormReceita).
            getIntent().putExtra(getResources().getString(R.string.categoria_id), categoria.getId());
            getIntent().putExtra(getResources().getString(R.string.categoria_drawable_id), categoria.getImagem().getDrawableID());
            setResult(Activity.RESULT_OK, getIntent());
            finish();
        } else {
            listaCategoriaReceitas();
        }
    }
}
