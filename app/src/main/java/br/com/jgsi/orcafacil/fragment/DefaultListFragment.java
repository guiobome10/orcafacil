package br.com.jgsi.orcafacil.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CrudActivity;
import br.com.jgsi.orcafacil.constants.ParameterConstants;
import br.com.jgsi.orcafacil.dao.CrudDAO;

/**
 * Created by guilherme.costa on 11/03/2016.
 */
public class DefaultListFragment<T> extends Fragment {

    private ListView lista;
    private CrudActivity<T> activity;
    private T modelClass;
    private CrudDAO<T> dao;
    private int layoutResID;
    private int listViewID;
    private BaseAdapter adapter;
    private List<T> models;

    public DefaultListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null){
            modelClass = (T)getArguments().get(ParameterConstants.modelClass);
            dao = (CrudDAO<T>)getArguments().get(ParameterConstants.dao);
            layoutResID = getArguments().getInt(ParameterConstants.layoutResID);
            listViewID = getArguments().getInt(ParameterConstants.listViewID);
            adapter = (BaseAdapter)getArguments().get(ParameterConstants.adapter);
        }


        View layout = inflater.inflate(layoutResID, null);

        activity = (CrudActivity)getActivity();

        Button novo = (Button) layout.findViewById(R.id.btn_novo);

        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novo();
            }
        });

        lista = (ListView) layout.findViewById(listViewID);
        registerForContextMenu(lista);
        carregaLista();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                modelClass = (T) adapter.getItemAtPosition(position);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                modelClass = (T) adapter.getItemAtPosition(position);
                return false;
            }
        });

        return layout;
    }

    private void carregaLista() {
        models = dao.listar();
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem alterar = menu.add(activity.getResources().getString(R.string.alterar));
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionar((Serializable) modelClass);
                return false;
            }
        });

        MenuItem excluir = menu.add(activity.getResources().getString(R.string.excluir));

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao.remover(modelClass);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
