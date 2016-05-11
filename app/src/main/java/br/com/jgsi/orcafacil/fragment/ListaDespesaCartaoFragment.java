package br.com.jgsi.orcafacil.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.DespesaCartaoActivity;
import br.com.jgsi.orcafacil.adapter.DespesaCartaoAdapter;
import br.com.jgsi.orcafacil.dao.DespesaCartaoDAO;
import br.com.jgsi.orcafacil.model.DespesaCartao;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ListaDespesaCartaoFragment extends Fragment {

    private ListView listaDespesasCartao;
    private DespesaCartaoActivity activity;
    private DespesaCartao despesaCartao;
    private DespesaCartaoDAO dao;
    private List<DespesaCartao> despesasCartao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.despesa_cartao_lista, null);

        activity = (DespesaCartaoActivity)getActivity();

        recuperaCamposView(layout);

        return layout;
    }

    private void recuperaCamposView(View layout) {
        Button novaDespesaCartao = (Button) layout.findViewById(R.id.btn_novo);

        novaDespesaCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novo();
            }
        });

        listaDespesasCartao = (ListView) layout.findViewById(R.id.despesas_cartao);
        registerForContextMenu(listaDespesasCartao);

        listaDespesasCartao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                despesaCartao = (DespesaCartao) adapter.getItemAtPosition(position);
            }
        });

        listaDespesasCartao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                despesaCartao = (DespesaCartao) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        dao = new DespesaCartaoDAO(activity);

        if(getArguments() != null){
            despesaCartao = (DespesaCartao) getArguments().getSerializable(getString(R.string.despesa_cartao));
            despesasCartao = dao.getByFatura(despesaCartao.getFatura());
        } else {
            despesasCartao = dao.listar();
        }

        DespesaCartaoAdapter adapter = new DespesaCartaoAdapter(despesasCartao, activity);
        listaDespesasCartao.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem alterar = menu.add(getString(R.string.alterar));
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionar(despesaCartao);
                return false;
            }
        });

        MenuItem excluir = menu.add(getString(R.string.excluir));

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new DespesaCartaoDAO(activity);
                dao.remover(despesaCartao);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
