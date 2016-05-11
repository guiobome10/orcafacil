package br.com.jgsi.orcafacil.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import br.com.jgsi.orcafacil.activities.DespesaActivity;
import br.com.jgsi.orcafacil.adapter.DespesaAdapter;
import br.com.jgsi.orcafacil.adapter.DespesaPagerAdapter;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.dao.DespesaDAO;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ListaDespesasFragment extends Fragment {

    private int posicaoAtual;
    private Calendar dataInicio;
    private Calendar dataFim;
    private ListView listaDespesas;
    private DespesaActivity activity;
    private Despesa despesa;
    private DespesaDAO dao;
    private List<Despesa> despesas;
    private OrcamentoDAO orcamentoDAO;
    private TextView dataListada;
    private Button botaoNova;


    public ListaDespesasFragment(){
        dataInicio = DateUtil.dataInicioMesAtual();
        dataFim = DateUtil.dataFimMesAtual();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.despesa_lista, null);

        activity = (DespesaActivity)getActivity();

        botaoNova = (Button) layout.findViewById(R.id.btn_nova_despesa);

        botaoNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novaDespesa();
            }
        });

        dataListada = (TextView) layout.findViewById(R.id.data_lista);

        listaDespesas = (ListView) layout.findViewById(R.id.despesas);
        registerForContextMenu(listaDespesas);

        if(getArguments().getString("dataListada") != null){
            dataInicio = DateFormatter.formataAnoMesDia(getArguments().getString("dataListada"));
            dataFim = DateUtil.ultimoDiaMes(dataInicio);
        }
        carregaLista();

        listaDespesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                despesa = (Despesa) adapter.getItemAtPosition(position);
                //activity.selecionarConta(conta);
            }
        });

        listaDespesas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                despesa = (Despesa) adapter.getItemAtPosition(position);
                return false;
            }
        });

        return layout;
    }

    private void setDataListada() {
        dataListada.setText(DateFormatter.formataMesDia(dataInicio).toUpperCase());
    }

    private void diminuiDatasEmUmMes() {
        dataInicio = DateUtil.addMonthToDate(dataInicio, -1);
        dataFim= DateUtil.addMonthToDate(dataFim, -1);
    }

    private void adicionaDatasEmUmMes() {
        dataInicio = DateUtil.addMonthToDate(dataInicio, 1);
        dataFim= DateUtil.addMonthToDate(dataFim, 1);
    }

    private void carregaLista() {
        setDataListada();
        dao = new DespesaDAO(activity);
        despesas = dao.getListaPeriodo(dataInicio, dataFim);
        DespesaAdapter adapter = new DespesaAdapter(despesas, activity);
        listaDespesas.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem alterar = menu.add("Alterar");
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Inicia form com dados para alteração.
                activity.selecionarDespesa(despesa);
                return false;
            }
        });

        MenuItem duplicar = menu.add("Duplicar despesa");
        duplicar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Cria clone da despesa com a data atual
                Despesa aux = (Despesa)despesa.clone();
                aux.setData(Calendar.getInstance());
                //Inicia form com dados para alteração com a data setada para data atual.
                activity.selecionarDespesa(aux);
                return false;
            }
        });

        MenuItem excluir = menu.add("Excluir");

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new DespesaDAO(activity);
                ContaDAO contaDAO = new ContaDAO(activity);
                despesa.getConta().deposita(despesa.getValor());
                contaDAO.atualizar(despesa.getConta());
                if(despesa.getOrcamento() != null){
                    despesa.getOrcamento().deposita(despesa.getValor());
                    orcamentoDAO = new OrcamentoDAO(activity);
                    orcamentoDAO.atualizar(despesa.getOrcamento());
                }
                dao.deletar(despesa);
                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);

    }
}
