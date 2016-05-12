package br.com.jgsi.orcafacil.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CategoriaDespesaActivity;
import br.com.jgsi.orcafacil.activities.CategoriaReceitaActivity;
import br.com.jgsi.orcafacil.activities.DespesaActivity;
import br.com.jgsi.orcafacil.activities.FormDespesaActivity;
import br.com.jgsi.orcafacil.activities.ReceitaActivity;
import br.com.jgsi.orcafacil.adapter.CategoriaIconesAdapter;
import br.com.jgsi.orcafacil.constants.RequestCode;
import br.com.jgsi.orcafacil.dao.CategoriaDespesaDAO;
import br.com.jgsi.orcafacil.dao.CategoriaReceitaDAO;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.dao.DespesaDAO;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.dao.ReceitaDAO;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.Icone;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Receita;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateTimeMask;
import br.com.jgsi.orcafacil.util.DateUtil;
import br.com.jgsi.orcafacil.util.DecimalFormatter;
import br.com.jgsi.orcafacil.util.DummyObjects;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class FormDespesasFragment extends Fragment {

    private CategoriaDespesaDAO dao;
    private ContaDAO contaDAO;
    private OrcamentoDAO orcamentoDAO;
    private FormDespesaActivity activity;
    private Despesa despesa;
    private ImageView campoCategoria;
    private ArrayList<Categoria> categorias;
    private List<Icone> icones;
    private CategoriaIconesAdapter adapter;
    private Categoria categoriaSelecionada;
    private Spinner campoConta;
    private List<Conta> contas;
    private Conta contaSelecionada;
    private Button botaoSalvar;
    private FormularioHelper helper;
    private DespesaDAO despesaDAO;
    private EditText campoValor;
    private TextView campoSaldoRestante;
    private Orcamento orcamento;
    private ArrayAdapter<Conta> contaAdapter;
    private EditText campoData;
    private EditText campoDetalhe;

    public FormDespesasFragment() {
        this.despesa = new Despesa();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.despesa_form, null);

        activity = (FormDespesaActivity) getActivity();

        campoData = (EditText) layout.findViewById(R.id.despesa_form_data);
        campoData.setOnKeyListener(new DateTimeMask());
        campoData.setText(despesa.getDataFormatada());
        campoData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                despesa.setData(DateFormatter.formata(campoData.getText().toString()));
                getOrcamentoDespesa();
            }
        });

        campoValor = (EditText) layout.findViewById(R.id.despesa_form_valor);
        //campoValor.setText(despesa.getValorFormatado());
        campoValor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                despesa.setValor(DecimalFormatter.formata(campoValor.getText().toString()));
                atualizaCampoSaldoRestante();
            }
        });

        campoCategoria = (ImageView) layout.findViewById(R.id.despesa_form_categoria);
        campoSaldoRestante = (TextView) layout.findViewById(R.id.despesa_form_saldo_restante);

        campoConta = (Spinner) layout.findViewById(R.id.despesa_form_conta);

        campoDetalhe = (EditText) layout.findViewById(R.id.despesa_form_detalhe);

        botaoSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        carregaCategorias();
        carregaContas();

        return layout;
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.despesa_form);
        getOrcamentoDespesa();
        if(helper.validaDadosForm()){
            Double valorAnterior = despesa.getValor();
            despesa = helper.pegaDadosForm(despesa);
            despesaDAO = new DespesaDAO(activity);
            if(despesa.getId() == null){
                despesaDAO.inserir(despesa);
            } else {
                Double diferencaValorNovoAnterior = despesa.obterDiferencaValorNovoAnterior(valorAnterior, despesa.getValor());
                despesa.getConta().deposita(diferencaValorNovoAnterior);
                contaDAO.atualizar(despesa.getConta());
                if(despesa.getOrcamento() != null){
                    despesa.getOrcamento().deposita(diferencaValorNovoAnterior);
                    orcamentoDAO.atualizar(despesa.getOrcamento());
                }
                despesaDAO.atualizar(despesa);
            }
            if(orcamento != null){
                atualizaCampoSaldoRestante();
                orcamentoDAO = new OrcamentoDAO(activity);
                orcamentoDAO.atualizar(orcamento);
            }
            Toast.makeText(activity, R.string.despesa_salva, Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() != null){
            despesa = (Despesa) getArguments().getSerializable(getResources().getString(R.string.despesa));
            if(despesa != null) carregaDespesa(despesa);
        }
        if(categoriaSelecionada != null){
            campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
        }

    }

    private void carregaDespesa(Despesa despesa) {
        if(despesa.getCategoria()!=null) campoCategoria.setImageBitmap(despesa.getCategoria().getImagem().getBitmap());
        if(despesa.getConta() != null) campoConta.setSelection(contaAdapter.getPosition(despesa.getConta()));
        campoData.setText(despesa.getDataFormatada());
        campoValor.setText(despesa.getValorFormatado());
        campoDetalhe.setText(despesa.getDetalhes());
    }

    private void carregaCategorias() {
        this.dao = new CategoriaDespesaDAO(activity);
        categorias = new ArrayList<Categoria>();
        categorias.addAll(dao.getLista());
        Categoria aux = DummyObjects.getCategoriaDespesa();
        aux.setImagem(CategoriaFactory.criaIcone(getResources(), R.drawable.sem_imagem));
        categorias.add(categorias.size(), aux);

        campoCategoria.setImageBitmap(aux.getImagem().getBitmap());

        campoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaDialogSelecaoIcone(null);
            }
        });
    }

    private void carregaContas() {
        contaDAO = new ContaDAO(activity);
        contas = contaDAO.getLista();

        contaAdapter = new ArrayAdapter<Conta>(activity, new android.R.layout().simple_spinner_item, contas);
        contaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        campoConta.setAdapter(contaAdapter);

        campoConta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                contaSelecionada = (Conta) parent.getItemAtPosition(position);
                despesa.setConta(contaSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void criaDialogSelecaoIcone(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha a Categoria");

        View layoutIcones = getLayoutInflater(savedInstanceState).inflate(R.layout.categoria_lista_icones, null);

        ListView listaIcones = (ListView) layoutIcones.findViewById(R.id.categoria_lista_icones);

        adapter = new CategoriaIconesAdapter(categorias, activity);
        listaIcones.setAdapter(adapter);

        builder.setView(layoutIcones);

        final AlertDialog dialog = builder.create();

        listaIcones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                categoriaSelecionada = (CategoriaDespesa) adapter.getItemAtPosition(position);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
                despesa.setCategoria((CategoriaDespesa) categoriaSelecionada);

                getOrcamentoDespesa();

                dialog.dismiss();
                if (categoriaSelecionada.getId().equals(DummyObjects.getCategoriaDespesa().getId())) {
                    Intent irParaCategoria = new Intent(activity, CategoriaDespesaActivity.class);
                    irParaCategoria.putExtra(getResources().getString(R.string.nova), true);
                    startActivityForResult(irParaCategoria, RequestCode.CATEGORIA_DESPESA_REQUEST_CODE);
                }
                campoValor.requestFocus();
            }
        });

        dialog.show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RequestCode.CATEGORIA_DESPESA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                categoriaSelecionada = new CategoriaDespesa();
                categoriaSelecionada.setId(data.getLongExtra(getResources().getString(R.string.categoria_id), 0));
                categoriaSelecionada.setImagem(CategoriaFactory.criaIcone(getResources(), data.getIntExtra((getResources().getString(R.string.categoria_drawable_id)), 0)));
                despesa.setCategoria((CategoriaDespesa)categoriaSelecionada);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
            } else {
                despesa.setCategoria(null);
            }
        }
    }

    private void atualizaCampoSaldoRestante() {
        if(orcamento != null) {
            orcamento.setSaldo(orcamento.getValor() - despesa.getValor());
            campoSaldoRestante.setText(
                    getContext().getResources().getString(R.string.saldo_restante_orcamento) + orcamento.getSaldoFormatoDinheiro());
        } else {
            campoSaldoRestante.setText("    ");
        }

    }

    public void getOrcamentoDespesa() {
        orcamentoDAO = new OrcamentoDAO(getActivity());
        orcamento = orcamentoDAO.getByCategoriaDataDespesa(categoriaSelecionada, despesa);
        despesa.setOrcamento(orcamento);
        atualizaCampoSaldoRestante();
    }
}
