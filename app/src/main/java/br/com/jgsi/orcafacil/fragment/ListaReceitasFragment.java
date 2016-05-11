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

import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.ReceitaActivity;
import br.com.jgsi.orcafacil.adapter.ReceitaAdapter;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.dao.ReceitaDAO;
import br.com.jgsi.orcafacil.model.Receita;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ListaReceitasFragment extends Fragment {

    private ListView listaReceitas;
    private ReceitaActivity activity;
    private Receita receita;
    private ReceitaDAO dao;
    private List<Receita> receitas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.receita_lista, null);

        activity = (ReceitaActivity)getActivity();

        Button novaReceita = (Button) layout.findViewById(R.id.btn_nova_receita);

        novaReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novaReceita();
            }
        });

        listaReceitas = (ListView) layout.findViewById(R.id.receitas);
        registerForContextMenu(listaReceitas);
        carregaLista();

        listaReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                receita = (Receita) adapter.getItemAtPosition(position);
                //activity.selecionarConta(conta);
            }
        });

        listaReceitas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                receita = (Receita) adapter.getItemAtPosition(position);
                return false;
            }
        });

        return layout;
    }

    private void carregaLista() {
        dao = new ReceitaDAO(activity);
        receitas = dao.getLista();
        ReceitaAdapter adapter = new ReceitaAdapter(receitas, activity);
        listaReceitas.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem alterar = menu.add("Alterar");
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionarReceita(receita);
                return false;
            }
        });

        MenuItem duplicar = menu.add("Duplicar receita");
        duplicar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Cria clone da receita com a data atual
                Receita aux = (Receita)receita.clone();
                aux.setData(Calendar.getInstance());
                //Inicia form com dados para alteração com a data setada para data atual.
                activity.selecionarReceita(aux);
                return false;
            }
        });

        MenuItem excluir = menu.add("Excluir");

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new ReceitaDAO(activity);
                ContaDAO contaDAO = new ContaDAO(activity);
                receita.getConta().saca(receita.getValor());
                contaDAO.atualizar(receita.getConta());
                dao.deletar(receita);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
