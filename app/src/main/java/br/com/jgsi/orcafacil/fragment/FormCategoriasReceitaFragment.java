package br.com.jgsi.orcafacil.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CategoriaReceitaActivity;
import br.com.jgsi.orcafacil.adapter.CategoriaIconesAdapter;
import br.com.jgsi.orcafacil.dao.CategoriaReceitaDAO;
import br.com.jgsi.orcafacil.factory.CategoriaFactory;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaReceita;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class FormCategoriasReceitaFragment extends Fragment {

    private EditText campoNome;
    private ImageView campoImagem;
    private Button botaoSalvar;
    private FormularioHelper helper;
    private CategoriaReceita categoria;
    private CategoriaReceitaDAO dao;
    private CategoriaReceitaActivity activity;
    private List<Categoria> categorias;
    private Categoria iconeSelecionado;
    private CategoriaIconesAdapter adapter;

    public FormCategoriasReceitaFragment() {
        this.categoria = new CategoriaReceita();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.categoria_form, null);

        activity = (CategoriaReceitaActivity) getActivity();

        campoNome = (EditText) layout.findViewById(R.id.categoria_form_nome);
        campoImagem = (ImageView) layout.findViewById(R.id.categoria_form_imagem);

        campoImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaDialogSelecaoIcone(savedInstanceState);

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

    private void criaDialogSelecaoIcone(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha o icone da categoria");

        View layoutIcones = getLayoutInflater(savedInstanceState).inflate(R.layout.categoria_lista_icones, null);

        ListView listaIcones = (ListView) layoutIcones.findViewById(R.id.categoria_lista_icones);

        categorias = CategoriaFactory.createCategoriaReceitaIconesPadrao(getResources());
        adapter = new CategoriaIconesAdapter(categorias, activity);
        listaIcones.setAdapter(adapter);

        builder.setView(layoutIcones);

        final AlertDialog dialog = builder.create();

        listaIcones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                iconeSelecionado = (Categoria) adapter.getItemAtPosition(position);
                campoImagem.setImageBitmap(iconeSelecionado.getImagem().getBitmap());
                categoria.setImagem(iconeSelecionado.getImagem());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.categoria_form);
        if(helper.validaDadosForm()){
            categoria = helper.pegaDadosForm(categoria);
            dao = new CategoriaReceitaDAO(activity);
            if(categoria.getId() != null){
                dao.atualizar(categoria);
            } else {
                dao.inserir(categoria);
            }
            activity.retornaAoChamador(categoria);
        }
    }

}
