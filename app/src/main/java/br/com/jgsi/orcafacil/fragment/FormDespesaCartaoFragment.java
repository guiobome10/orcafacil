package br.com.jgsi.orcafacil.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import br.com.jgsi.orcafacil.activities.DespesaCartaoActivity;
import br.com.jgsi.orcafacil.adapter.CategoriaIconesAdapter;
import br.com.jgsi.orcafacil.constants.RequestCode;
import br.com.jgsi.orcafacil.dao.CategoriaDespesaDAO;
import br.com.jgsi.orcafacil.dao.DespesaCartaoDAO;
import br.com.jgsi.orcafacil.dao.FaturaDAO;
import br.com.jgsi.orcafacil.dao.FaturaDAO;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.util.DateTimeMask;
import br.com.jgsi.orcafacil.util.DecimalFormatter;
import br.com.jgsi.orcafacil.util.DummyObjects;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class FormDespesaCartaoFragment extends Fragment {

    private Fatura faturaSelecionada;
    private DespesaCartaoActivity activity;
    private DespesaCartao despesaCartao;
    private CategoriaDespesa categoriaSelecionada;
    private Orcamento orcamento;

    //Listas
    private List<Fatura> faturas;
    private List<Categoria> categorias;

    //DAO's
    private FaturaDAO faturaDAO;
    private DespesaCartaoDAO despesaCartaoDAO;
    private CategoriaDespesaDAO categoriaDAO;
    private OrcamentoDAO orcamentoDAO;

    //HELPER
    private FormularioHelper helper;

    //Adapters
    private ArrayAdapter<Fatura> faturaAdapter;
    private CategoriaIconesAdapter categoriaAdapter;

    //Campos da tela
    private Spinner campoFatura;
    private EditText campoData;
    private EditText campoDetalhe;
    private EditText campoQuantidadeParcelas;
    private EditText campoValor;
    private CheckBox campoParcelada;
    private ImageView campoCategoria;
    private TextView campoSaldoRestante;
    private Button botaoSalvar;

    public FormDespesaCartaoFragment() {
        despesaCartao = new DespesaCartao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.despesa_cartao_form, null);

        activity = (DespesaCartaoActivity) getActivity();

        recuperaCamposView(layout);

        return layout;
    }

    private void recuperaCamposView(View layout) {
        campoFatura = (Spinner) layout.findViewById(R.id.despesa_cartao_form_fatura);

        campoData = (EditText) layout.findViewById(R.id.despesa_cartao_form_data);
        campoData.setOnKeyListener(new DateTimeMask());

        campoValor = (EditText) layout.findViewById(R.id.despesa_cartao_form_valor);
        campoValor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                despesaCartao.setValor(DecimalFormatter.formata(campoValor.getText().toString()));
                atualizaCampoSaldoRestante();
            }
        });

        campoDetalhe = (EditText) layout.findViewById(R.id.despesa_cartao_form_detalhes);

        campoParcelada = (CheckBox) layout.findViewById(R.id.despesa_cartao_form_parcelada);
        campoParcelada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                despesaCartao.setParcelada(isChecked);
                if(isChecked){
                    campoQuantidadeParcelas.setVisibility(View.VISIBLE);
                } else {
                    campoQuantidadeParcelas.setVisibility(View.INVISIBLE);
                }
            }
        });

        campoQuantidadeParcelas = (EditText) layout.findViewById(R.id.despesa_cartao_form_quantidade_parcelas);
        campoCategoria = (ImageView) layout.findViewById(R.id.despesa_cartao_form_categoria);
        campoSaldoRestante = (TextView) layout.findViewById(R.id.despesa_cartao_form_saldo_restante);


        botaoSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    private void carregaDespesaCartao(DespesaCartao despesaCartao) {
        if(despesaCartao.getFatura() != null) campoFatura.setSelection(faturaAdapter.getPosition(despesaCartao.getFatura()));
        if(despesaCartao.getCategoria() != null) campoCategoria.setImageBitmap(despesaCartao.getCategoria().getImagem().getBitmap());
        campoData.setText(despesaCartao.getDataFormatada());
        campoValor.setText(despesaCartao.getValorFormatado());
        campoDetalhe.setText(despesaCartao.getDetalhes());
        if(despesaCartao.getQuantidadeParcelas() > 1){
            campoQuantidadeParcelas.setText(despesaCartao.getQuantidadeParcelas());
            campoQuantidadeParcelas.setVisibility(View.VISIBLE);
            campoParcelada.setChecked(true);
        } else {
            campoQuantidadeParcelas.setVisibility(View.INVISIBLE);
            campoParcelada.setChecked(false);
        }
        if(despesaCartao.getOrcamento() != null){
            campoSaldoRestante.setText(getString(R.string.saldo_restante_orcamento) + despesaCartao.getOrcamento().getSaldoFormatoDinheiro());
        } else {
            campoSaldoRestante.setText("");
        }
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.despesa_cartao_form);
        if(helper.validaDadosForm()){
            despesaCartao = helper.pegaDadosForm(despesaCartao);
            despesaCartaoDAO = new DespesaCartaoDAO(activity);
            despesaCartaoDAO.salvar(despesaCartao);
            Toast.makeText(activity, R.string.despesa_cartao_salva, Toast.LENGTH_LONG).show();
            activity.listar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaFaturas();
        carregaCategorias();
        atualizaCampoSaldoRestante();

        if(getArguments() != null){
            despesaCartao = (DespesaCartao) getArguments().getSerializable(getString(R.string.despesa_cartao));
            carregaDespesaCartao(despesaCartao);
        }
    }

    private void carregaFaturas() {
        faturaDAO = new FaturaDAO(activity);
        faturas = faturaDAO.listar();

        faturaAdapter = new ArrayAdapter<Fatura>(activity, new android.R.layout().simple_spinner_item, faturas);
        faturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        campoFatura.setAdapter(faturaAdapter);

        campoFatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faturaSelecionada = (Fatura) parent.getItemAtPosition(position);
                despesaCartao.setFatura(faturaSelecionada);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void carregaCategorias() {
        this.categoriaDAO = new CategoriaDespesaDAO(activity);
        categorias = new ArrayList<Categoria>();
        categorias.addAll(categoriaDAO.getLista());
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

    private void criaDialogSelecaoIcone(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha a Categoria");

        View layoutIcones = getLayoutInflater(savedInstanceState).inflate(R.layout.categoria_lista_icones, null);

        ListView listaIcones = (ListView) layoutIcones.findViewById(R.id.categoria_lista_icones);

        categoriaAdapter = new CategoriaIconesAdapter(categorias, activity);
        listaIcones.setAdapter(categoriaAdapter);

        builder.setView(layoutIcones);

        final AlertDialog dialog = builder.create();

        listaIcones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                categoriaSelecionada = (CategoriaDespesa) adapter.getItemAtPosition(position);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
                despesaCartao.setCategoria((CategoriaDespesa) categoriaSelecionada);

                orcamentoDAO = new OrcamentoDAO(activity);
                orcamento = orcamentoDAO.getByCategoriaDataDespesaCartao(categoriaSelecionada, despesaCartao);
                despesaCartao.setOrcamento(orcamento);
                atualizaCampoSaldoRestante();

                dialog.dismiss();
                if (categoriaSelecionada.getId().equals(DummyObjects.getCategoriaDespesa().getId())) {
                    Intent irParaCategoria = new Intent(activity, CategoriaDespesaActivity.class);
                    irParaCategoria.putExtra(getResources().getString(R.string.nova), true);
                    startActivityForResult(irParaCategoria, RequestCode.CATEGORIA_DESPESA_REQUEST_CODE);
                }
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
                despesaCartao.setCategoria((CategoriaDespesa)categoriaSelecionada);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
            } else {
                despesaCartao.setCategoria(null);
            }
        }
    }

    private void atualizaCampoSaldoRestante() {
        if(orcamento != null) {
            orcamento.setSaldo(orcamento.getSaldo() - despesaCartao.getValor());
            campoSaldoRestante.setText(
                    getContext().getResources().getString(R.string.saldo_restante_orcamento)
                            + DecimalFormatter.formataDinheiro(orcamento.getSaldo()));
        } else {
            campoSaldoRestante.setText("    ");
        }

    }


}
