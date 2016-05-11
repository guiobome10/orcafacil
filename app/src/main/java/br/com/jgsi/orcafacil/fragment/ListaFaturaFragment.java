package br.com.jgsi.orcafacil.fragment;

import android.content.Intent;
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
import br.com.jgsi.orcafacil.activities.FaturaActivity;
import br.com.jgsi.orcafacil.adapter.FaturaAdapter;
import br.com.jgsi.orcafacil.dao.FaturaDAO;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Fatura;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ListaFaturaFragment extends Fragment {

    private ListView listaFaturas;
    private FaturaActivity activity;
    private Fatura fatura;
    private FaturaDAO dao;
    private List<Fatura> faturas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fatura_lista, null);

        activity = (FaturaActivity)getActivity();

        recuperaCamposView(layout);

        return layout;
    }

    private void recuperaCamposView(View layout) {
        Button novaFatura = (Button) layout.findViewById(R.id.btn_novo);

        novaFatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novo();
            }
        });

        listaFaturas = (ListView) layout.findViewById(R.id.faturas);
        registerForContextMenu(listaFaturas);

        listaFaturas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                fatura = (Fatura) adapter.getItemAtPosition(position);
                //activity.selecionarConta(conta);
            }
        });

        listaFaturas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                fatura = (Fatura) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    private void carregaLista() {
        dao = new FaturaDAO(activity);

        if(getArguments() != null){
            fatura = (Fatura) getArguments().getSerializable(getString(R.string.fatura));
            faturas = dao.getByCartao(fatura.getCartaoDeCredito());
        } else {
            faturas = dao.listar();
        }

        FaturaAdapter adapter = new FaturaAdapter(faturas, activity);
        listaFaturas.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem adicionarDespesa = menu.add(getString(R.string.adicionar_despesa_cartao));
        adicionarDespesa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form nova despesa do cartão.
                Intent irParaDespesaCartaoActicity = new Intent(activity, DespesaCartaoActivity.class);
                DespesaCartao despesaCartao = new DespesaCartao();
                despesaCartao.setFatura(fatura);
                irParaDespesaCartaoActicity.putExtra(getString(R.string.despesa_cartao), despesaCartao);
                startActivity(irParaDespesaCartaoActicity);
                return false;
            }
        });

        MenuItem listarDespesas = menu.add(getString(R.string.lista_despesa_cartao));
        listarDespesas.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form nova despesa do cartão.
                Intent irParaDespesaCartaoActicity = new Intent(activity, DespesaCartaoActivity.class);
                DespesaCartao despesaCartao = new DespesaCartao();
                despesaCartao.setFatura(fatura);
                irParaDespesaCartaoActicity.putExtra(getString(R.string.despesa_cartao), despesaCartao);
                irParaDespesaCartaoActicity.putExtra(getString(R.string.nova), getString(R.string.listar));
                startActivity(irParaDespesaCartaoActicity);
                return false;
            }
        });

        MenuItem alterar = menu.add(getString(R.string.alterar));
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionar(fatura);
                return false;
            }
        });

        MenuItem excluir = menu.add(getString(R.string.excluir));

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new FaturaDAO(activity);
                dao.remover(fatura);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
