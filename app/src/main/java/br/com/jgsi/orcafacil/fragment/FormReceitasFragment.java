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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CategoriaReceitaActivity;
import br.com.jgsi.orcafacil.activities.ReceitaActivity;
import br.com.jgsi.orcafacil.adapter.CategoriaIconesAdapter;
import br.com.jgsi.orcafacil.constants.RequestCode;
import br.com.jgsi.orcafacil.dao.CategoriaReceitaDAO;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.dao.ReceitaDAO;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Icone;
import br.com.jgsi.orcafacil.model.Receita;
import br.com.jgsi.orcafacil.util.DateTimeMask;
import br.com.jgsi.orcafacil.util.DummyObjects;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class FormReceitasFragment extends Fragment {



    private CategoriaReceitaDAO dao;
    private ContaDAO contaDAO;
    private ReceitaActivity activity;
    private Receita receita;
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
    private ReceitaDAO receitaDAO;
    private EditText campoValor;
    private ArrayAdapter<Conta> contaAdapter;
    private EditText campoData;

    public FormReceitasFragment() {
        this.receita = new Receita();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.receita_form, null);

        activity = (ReceitaActivity) getActivity();

        campoData = (EditText) layout.findViewById(R.id.receita_form_data);
        campoData.setOnKeyListener(new DateTimeMask());
        campoData.setText(receita.getDataFormatada());

        campoValor = (EditText) layout.findViewById(R.id.receita_form_valor);
        campoValor.setText(receita.getValor().toString());

        campoCategoria = (ImageView) layout.findViewById(R.id.receita_form_categoria);

        campoConta = (Spinner) layout.findViewById(R.id.receita_form_conta);

        botaoSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        return layout;
    }

    private void carregaReceita(Receita receita) {
        if(receita.getCategoria() != null) campoCategoria.setImageBitmap(receita.getCategoria().getImagem().getBitmap());
        if(receita.getConta() != null) campoConta.setSelection(contaAdapter.getPosition(receita.getConta()));
        campoData.setText(receita.getDataFormatada());
        campoValor.setText(receita.getValorFormatado());
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.receita_form);
        if(helper.validaDadosForm()){
            Double valorAnteriorReceita = receita.getValor();
            receita = helper.pegaDadosForm(receita);
            receitaDAO = new ReceitaDAO(activity);
            if(receita.getId() == null){
                receitaDAO.inserir(receita);
            } else {
                Double diferencaValorNovoAnterior = receita.obtemDiferencaValorNovoAnterior(valorAnteriorReceita, receita.getValor());
                receita.getConta().deposita(diferencaValorNovoAnterior);
                contaDAO.atualizar(receita.getConta());
                receitaDAO.atualizar(receita);
            }
            Toast.makeText(activity, R.string.receita_salva, Toast.LENGTH_LONG).show();
            activity.listaReceitas();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaCategorias();
        carregaContas();
        if(categoriaSelecionada != null){
            campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
        }

        if(getArguments() != null){
            receita = (Receita) getArguments().getSerializable(getResources().getString(R.string.receita));
            carregaReceita(receita);
        }
    }

    private void carregaCategorias() {
        this.dao = new CategoriaReceitaDAO(activity);
        categorias = new ArrayList<Categoria>();
        categorias.addAll(dao.getLista());
        Categoria aux = DummyObjects.getCategoriaReceita();
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
                receita.setConta(contaSelecionada);
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
                categoriaSelecionada = (CategoriaReceita) adapter.getItemAtPosition(position);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
                receita.setCategoria((CategoriaReceita) categoriaSelecionada);
                dialog.dismiss();
                if (categoriaSelecionada.getId().equals(DummyObjects.getCategoriaReceita().getId())) {
                    Intent irParaCategoria = new Intent(activity, CategoriaReceitaActivity.class);
                    irParaCategoria.putExtra(getResources().getString(R.string.nova), true);
                    startActivityForResult(irParaCategoria, RequestCode.CATEGORIA_RECEITA_REQUEST_CODE);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RequestCode.CATEGORIA_RECEITA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                categoriaSelecionada = new CategoriaReceita();
                categoriaSelecionada.setId(data.getLongExtra(getResources().getString(R.string.categoria_id), 0));
                categoriaSelecionada.setImagem(CategoriaFactory.criaIcone(getResources(), data.getIntExtra((getResources().getString(R.string.categoria_drawable_id)), 0)));
                receita.setCategoria((CategoriaReceita)categoriaSelecionada);
                campoCategoria.setImageBitmap(categoriaSelecionada.getImagem().getBitmap());
            } else {
                receita.setCategoria(null);
            }
        }
    }

}
