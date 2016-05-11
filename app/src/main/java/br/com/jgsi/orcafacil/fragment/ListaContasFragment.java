package br.com.jgsi.orcafacil.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import br.com.jgsi.orcafacil.activities.ContaActivity;
import br.com.jgsi.orcafacil.adapter.ContaAdapter;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.model.Conta;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public class ListaContasFragment extends Fragment {

    private ListView listaContas;
    private ContaActivity activity;
    private Conta conta;
    private ContaDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutLista = inflater.inflate(R.layout.conta_lista, container, false);
        activity = (ContaActivity) getActivity();

        //Carrega tela
        listaContas = (ListView) layoutLista.findViewById(R.id.contas);
        registerForContextMenu(listaContas);

        Button botaoNovaConta = (Button) layoutLista.findViewById(R.id.btn_nova_conta);

        botaoNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novaConta();
            }
        });

        listaContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                conta = (Conta) adapter.getItemAtPosition(position);
                //activity.selecionarConta(conta);
            }
        });

        listaContas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                conta = (Conta) adapter.getItemAtPosition(position);
                return false;
            }
        });

        return layoutLista;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        List<Conta> contas = new ContaDAO(getActivity()).getLista();

        listaContas.setAdapter(new ContaAdapter(contas, activity));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        dao = new ContaDAO(activity);
        if(dao.contaPodeSerAlterada(conta)){
            MenuItem alterar = menu.add("Alterar");
            alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Inicia form com dados para alteração.
                    activity.selecionarConta(conta);
                    return false;
                }
            });
        }

        MenuItem novaReceita = menu.add("Nova receita");
        novaReceita.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                activity.novaReceita(conta);
                return false;
            }
        });

        MenuItem novoOrcamento = menu.add("Novo orçamento");
        novoOrcamento.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                activity.novoOrcamento(conta);
                return false;
            }
        });

        MenuItem novaDespesa = menu.add("Nova despesa");
        novaDespesa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                activity.novaDespesa(conta);
                return false;
            }
        });

        MenuItem excluir = menu.add("Excluir");

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new ContaDAO(activity);
                dao.deletar(conta);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
