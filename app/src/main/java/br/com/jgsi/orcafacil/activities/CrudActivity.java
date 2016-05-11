package br.com.jgsi.orcafacil.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.io.Serializable;

import br.com.jgsi.orcafacil.R;

/**
 * Created by guilherme.costa on 11/03/2016.
 */
public class CrudActivity<T> extends FragmentActivity {

    private T modelClass;
    private int layoutResID;
    private int containerViewID;
    private int serializableID;
    private Fragment formFragment;
    private Fragment listFragment;

    public CrudActivity(T modelClass, int layoutResID, int containerViewID, int serializableID, Fragment formFragment, Fragment listFragment) {
        this.modelClass = modelClass;
        this.layoutResID = layoutResID;
        this.containerViewID = containerViewID;
        this.serializableID = serializableID;
        this.formFragment = formFragment;
        this.listFragment = listFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        if(getIntent().getExtras() == null){
            listar();
        } else {
            if(getIntent().getExtras().getString( getString( R.string.action) ) != null){
                listarComParametro();
            }else{
                selecionar(getIntent().getExtras().getSerializable(getResources().getString(serializableID)));
            }
        }
    }

    private void listarComParametro() {
        Bundle args = new Bundle();
        args.putSerializable(getString(serializableID), getIntent().getExtras().getSerializable(getResources().getString(serializableID)));
        listFragment.setArguments(args);
        listar();
    }

    public void listar() {
        iniciaFragment(containerViewID, listFragment);
    }

    public void novo(){
        iniciaFragment(containerViewID, formFragment);
    }

    public void selecionar(Serializable modelClass) {
        Bundle args = new Bundle();
        args.putSerializable(getString(serializableID), modelClass);
        formFragment.setArguments(args);
        iniciaFragment(containerViewID, formFragment);
    }

    protected void iniciaFragment(int containerViewID, Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerViewID, fragment);
        ft.commit();
    }

}
