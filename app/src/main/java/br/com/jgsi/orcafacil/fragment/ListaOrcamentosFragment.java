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
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.OrcamentoActivity;
import br.com.jgsi.orcafacil.activities.OrcamentoActivity;
import br.com.jgsi.orcafacil.adapter.OrcamentoAdapter;
import br.com.jgsi.orcafacil.adapter.OrcamentoAdapter;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 28/01/2016.
 */
public class ListaOrcamentosFragment extends Fragment {

    private OrcamentoActivity activity;
    private ListView listaOrcamentos;
    private OrcamentoDAO dao;
    private List<Orcamento> orcamentos;
    private Orcamento orcamento;
    private Button novoOrcamento;
    private TextView dataListada;
    private Calendar dataInicio;
    private Calendar dataFim;

    public ListaOrcamentosFragment() {
        this.dataInicio = DateUtil.dataInicioMesAtual();
        this.dataFim = DateUtil.dataFimMesAtual();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.orcamento_lista, null);

        activity = (OrcamentoActivity)getActivity();

        novoOrcamento = (Button) layout.findViewById(R.id.btn_novo_orcamento);

        novoOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novoOrcamento();
            }
        });

        dataListada = (TextView) layout.findViewById(R.id.data_lista);

        listaOrcamentos = (ListView) layout.findViewById(R.id.orcamentos);
        registerForContextMenu(listaOrcamentos);

        if(getArguments().getString("dataListada") != null){
            dataInicio = DateFormatter.formataAnoMesDia(getArguments().getString("dataListada"));
            dataFim = DateUtil.ultimoDiaMes(dataInicio);
        }
        carregaLista();

        listaOrcamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                orcamento = (Orcamento) adapter.getItemAtPosition(position);
                //activity.selecionarConta(conta);
            }
        });

        listaOrcamentos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                orcamento = (Orcamento) adapter.getItemAtPosition(position);
                return false;
            }
        });

        return layout;

    }

    private void carregaLista() {
        setDataListada();
        dao = new OrcamentoDAO(activity);
        orcamentos = dao.getListaPeriodo(dataInicio, dataFim);
        OrcamentoAdapter adapter = new OrcamentoAdapter(orcamentos, activity);
        listaOrcamentos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem alterar = menu.add("Alterar");
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionarOrcamento(orcamento);
                return false;
            }
        });

        MenuItem excluir = menu.add("Excluir");

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new OrcamentoDAO(activity);
                dao.deletar(orcamento);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    private void setDataListada() {
        dataListada.setText(DateFormatter.formataMesDia(dataInicio).toUpperCase());
    }
}
