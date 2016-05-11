package br.com.jgsi.orcafacil.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CartaoDeCreditoActivity;
import br.com.jgsi.orcafacil.activities.FaturaActivity;
import br.com.jgsi.orcafacil.adapter.CartaoDeCreditoAdapter;
import br.com.jgsi.orcafacil.dao.CartaoDeCreditoDAO;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.Fatura;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ListaCartaoDeCreditoFragment extends Fragment {

    private ListView listaCartoesDeCredito;
    private CartaoDeCreditoActivity activity;
    private CartaoDeCredito cartaoDeCredito;
    private CartaoDeCreditoDAO dao;
    private List<CartaoDeCredito> cartoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cartao_de_credito_lista, null);

        activity = (CartaoDeCreditoActivity)getActivity();

        Button novoCartao = (Button) layout.findViewById(R.id.btn_novo_cartao);

        novoCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novoCartao();
            }
        });

        listaCartoesDeCredito = (ListView) layout.findViewById(R.id.cartoes_de_credito);
        registerForContextMenu(listaCartoesDeCredito);
        carregaLista();

        listaCartoesDeCredito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                cartaoDeCredito = (CartaoDeCredito) adapter.getItemAtPosition(position);
                //activity.selecionarConta(conta);
            }
        });

        listaCartoesDeCredito.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                cartaoDeCredito = (CartaoDeCredito) adapter.getItemAtPosition(position);
                return false;
            }
        });

        return layout;
    }

    private void carregaLista() {
        dao = new CartaoDeCreditoDAO(activity);
        cartoes = dao.getLista();
        CartaoDeCreditoAdapter adapter = new CartaoDeCreditoAdapter(cartoes, activity);
        listaCartoesDeCredito.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem adicionarFatura = menu.add(getString(R.string.adicionar_fatura));
        adicionarFatura.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent irParaFaturaActivity  = new Intent(activity, FaturaActivity.class);
                Fatura fatura = new Fatura();
                fatura.setCartaoDeCredito(cartaoDeCredito);
                irParaFaturaActivity.putExtra(getString(R.string.fatura), fatura);
                startActivity(irParaFaturaActivity);
                return false;
            }
        });

        MenuItem listarFaturas = menu.add(getString(R.string.lista_faturas));
        listarFaturas.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent irParaFaturaActivity  = new Intent(activity, FaturaActivity.class);
                Fatura fatura = new Fatura();
                fatura.setCartaoDeCredito(cartaoDeCredito);
                irParaFaturaActivity.putExtra(getString(R.string.fatura), fatura);
                irParaFaturaActivity.putExtra(getString(R.string.action), getString(R.string.listar));
                startActivity(irParaFaturaActivity);
                return false;
            }
        });

        MenuItem alterar = menu.add("Alterar");
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionarCartaoDeCredito(cartaoDeCredito);
                return false;
            }
        });

        MenuItem excluir = menu.add("Excluir");

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new CartaoDeCreditoDAO(activity);
                dao.deletar(cartaoDeCredito);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
