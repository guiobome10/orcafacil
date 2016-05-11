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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CategoriaDespesaActivity;
import br.com.jgsi.orcafacil.activities.OrcamentoActivity;
import br.com.jgsi.orcafacil.adapter.CategoriaIconesAdapter;
import br.com.jgsi.orcafacil.dao.CategoriaDespesaDAO;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.factory.OrcamentoFactory;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Periodicidade;
import br.com.jgsi.orcafacil.util.DateTimeMask;
import br.com.jgsi.orcafacil.util.DummyObjects;

/**
 * Created by guilherme.costa on 28/01/2016.
 */
public class FormOrcamentosFragment extends Fragment {

    private static final int CATEGORIA_DESPESA_REQUEST_CODE = 1;
    private Orcamento orcamento;
    private OrcamentoActivity activity;
    private ImageView campoCategoria;
    private EditText campoValor;
    private EditText campoDataFim;
    private Button botaoSalvar;
    private FormularioHelper helper;
    private OrcamentoDAO orcamentoDAO;
    private CategoriaDespesaDAO categoriaDAO;
    private List<Categoria> categorias;
    private CategoriaDespesa categoriaSelecionada;
    private ArrayAdapter<String> adapter;
    private CategoriaIconesAdapter categoriaAdapter;
    private Spinner campoPeriodicidade;
    private Periodicidade periodicidadeSelecionada;
    private ToggleButton campoRepetir;
    private EditText campoQuantidadeRepeticoes;
    private Integer quantidadeRepeticoes =0;

    public FormOrcamentosFragment() {
        this.orcamento = new Orcamento();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.orcamento_form, null);

        activity = (OrcamentoActivity) getActivity();

        campoValor = (EditText) layout.findViewById(R.id.orcamento_form_valor);

        campoCategoria = (ImageView) layout.findViewById(R.id.orcamento_form_categoria);

        campoPeriodicidade = (Spinner) layout.findViewById(R.id.orcamento_form_periodicidade);
        campoPeriodicidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodicidadeSelecionada = Periodicidade.get(adapter.getItem(position));
                if(periodicidadeSelecionada.equals(Periodicidade.PERZONALIZADA)){
                    desabilitarCampoRepetir();
                } else {
                    habilitarCampoRepetir();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        campoDataFim = (EditText) layout.findViewById(R.id.orcamento_form_data_fim);
        campoDataFim.setOnKeyListener(new DateTimeMask());

        campoQuantidadeRepeticoes = (EditText) layout.findViewById(R.id.orcamento_form_quantidade_repeticoes);

        campoRepetir = (ToggleButton) layout.findViewById(R.id.orcamento_form_repetir);
        campoRepetir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    campoQuantidadeRepeticoes.setVisibility(View.VISIBLE);
                } else {
                    campoQuantidadeRepeticoes.setVisibility(View.INVISIBLE);
                }
            }
        });


        botaoSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        return layout;
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.orcamento_form);
        if(helper.validaFormOrcamento(activity, orcamento)){
            orcamento = helper.pegaDadosForm(orcamento);
            orcamentoDAO = new OrcamentoDAO(activity);
            if(orcamento.getId() == null){
                orcamentoDAO.inserir(orcamento);
                if(!orcamento.getPeriodicidade().equals(Periodicidade.PERZONALIZADA)){
                    pegaQuantidadeRepeticoesDoForm();
                    if(quantidadeRepeticoes > 0){
                        orcamentoDAO.inserirLista(OrcamentoFactory.criarListaDeOrcamentosParaRepeticoesDo(orcamento, quantidadeRepeticoes));
                    }
                }
            } else {
                orcamentoDAO.atualizar(orcamento);
            }
            Toast.makeText(activity, R.string.orcamento_salvo, Toast.LENGTH_LONG).show();
            activity.listaOrcamentos();
        }
    }

    private void pegaQuantidadeRepeticoesDoForm() {
        quantidadeRepeticoes = Integer.valueOf(campoQuantidadeRepeticoes.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaCategorias();
        carregaPeriodicidades();
        if(categoriaSelecionada != null){
            campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
        }
        if(periodicidadeSelecionada != null){
            campoPeriodicidade.setSelection(adapter.getPosition(periodicidadeSelecionada.nome()));
        }

        if(getArguments() != null){
            orcamento = (Orcamento) getArguments().getSerializable(getResources().getString(R.string.orcamento));
            carregaOrcamento(orcamento);
        }
    }

    private void carregaOrcamento(Orcamento orcamento) {
        if(orcamento.getCategoria() != null) campoCategoria.setImageBitmap(orcamento.getCategoria().getImagem().getBitmap());
        campoValor.setText(orcamento.getValorFormatado());
        campoPeriodicidade.setSelection(adapter.getPosition(orcamento.getPeriodicidade().nome()));
        if(orcamento.getId() != null){
            desabilitarCampoRepetir();
        }
    }

    private void desabilitarCampoRepetir() {
        campoRepetir.setVisibility(View.INVISIBLE);
        campoRepetir.setChecked(false);
    }

    private void habilitarCampoRepetir() {
        campoRepetir.setVisibility(View.VISIBLE);
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

    private void carregaPeriodicidades() {
        adapter = new ArrayAdapter<String>(getContext(), new android.R.layout().simple_spinner_item, Periodicidade.asListString());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoPeriodicidade.setAdapter(adapter);

        campoPeriodicidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodicidadeSelecionada = Periodicidade.get((String) parent.getItemAtPosition(position));

                if (periodicidadeSelecionada.equals(Periodicidade.PERZONALIZADA)) {
                    campoDataFim.setText(DummyObjects.getDataAtualUltimoDiaMesFormatado());
                    campoDataFim.setVisibility(View.VISIBLE);
                } else {
                    campoDataFim.setVisibility(View.INVISIBLE);
                }

                orcamento.setPeriodicidade(periodicidadeSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CATEGORIA_DESPESA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                categoriaSelecionada = new CategoriaDespesa();
                categoriaSelecionada.setId(data.getLongExtra(getResources().getString(R.string.categoria_id), 0));
                categoriaSelecionada.setImagem(CategoriaFactory.criaIcone(getResources(), data.getIntExtra((getResources().getString(R.string.categoria_drawable_id)), 0)));
                orcamento.setCategoria((CategoriaDespesa) categoriaSelecionada);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
            } else {
                orcamento.setCategoria(null);
            }
        }
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
                orcamento.setCategoria((CategoriaDespesa) categoriaSelecionada);
                dialog.dismiss();
                if(categoriaSelecionada.getId().equals(DummyObjects.getCategoriaDespesa().getId())){
                    Intent irParaCategoria = new Intent(activity, CategoriaDespesaActivity.class);
                    irParaCategoria.putExtra(getResources().getString(R.string.nova), true);
                    startActivityForResult(irParaCategoria, CATEGORIA_DESPESA_REQUEST_CODE);
                }
            }
        });

        dialog.show();
    }
}
