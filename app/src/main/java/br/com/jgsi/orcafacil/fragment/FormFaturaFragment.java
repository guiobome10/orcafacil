package br.com.jgsi.orcafacil.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.FaturaActivity;
import br.com.jgsi.orcafacil.dao.CartaoDeCreditoDAO;
import br.com.jgsi.orcafacil.dao.FaturaDAO;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.util.DateTimeMask;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class FormFaturaFragment extends Fragment {

    private CartaoDeCredito cartaoSelecionado;
    private FaturaActivity activity;
    private Fatura fatura;

    //Listas
    private List<CartaoDeCredito> cartoes;

    //DAO's
    private CartaoDeCreditoDAO cartaoDAO;
    private FaturaDAO faturaDAO;

    //HELPER
    private FormularioHelper helper;

    //Adapters
    private ArrayAdapter<CartaoDeCredito> cartaoAdapter;

    //Campos da tela
    private Spinner campoCartaoDeCredito;
    private EditText campoDataInicioCiclo;
    private EditText campoDataFinalCiclo;
    private EditText campoDataVencimento;
    private EditText campoValor;
    private Button botaoSalvar;

    public FormFaturaFragment() {
        fatura = new Fatura();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fatura_form, null);

        activity = (FaturaActivity) getActivity();

        recuperaCamposView(layout);

        return layout;
    }

    private void recuperaCamposView(View layout) {
        campoCartaoDeCredito = (Spinner) layout.findViewById(R.id.fatura_form_cartao);

        campoDataInicioCiclo = (EditText) layout.findViewById(R.id.fatura_form_data_inicio_ciclo);
        campoDataInicioCiclo.setOnKeyListener(new DateTimeMask());

        campoDataFinalCiclo = (EditText) layout.findViewById(R.id.fatura_form_data_final_ciclo);
        campoDataFinalCiclo.setOnKeyListener(new DateTimeMask());

        campoDataVencimento = (EditText) layout.findViewById(R.id.fatura_form_data_vencimento);
        campoDataVencimento.setOnKeyListener(new DateTimeMask());

        campoValor = (EditText) layout.findViewById(R.id.fatura_form_valor);

        botaoSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    private void carregaFatura(Fatura fatura) {
        if(fatura.getCartaoDeCredito() != null) campoCartaoDeCredito.setSelection(cartaoAdapter.getPosition(fatura.getCartaoDeCredito()));
        campoDataInicioCiclo.setText(fatura.getDataInicialCicloFormatada());
        campoDataFinalCiclo.setText(fatura.getDataFinalCicloFormatada());
        campoDataVencimento.setText(fatura.getDataVencimentoFormatada());
        campoValor.setText(fatura.getValorFormatado());
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.fatura_form);
        if(helper.validaDadosForm()){
            fatura = helper.pegaDadosForm(fatura);
            faturaDAO = new FaturaDAO(activity);
            faturaDAO.salvar(fatura);
            Toast.makeText(activity, R.string.fatura_salva, Toast.LENGTH_LONG).show();
            activity.listar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaCartoesDeCredito();

        if(getArguments() != null){
            fatura = (Fatura) getArguments().getSerializable(getString(R.string.fatura));
        }
        carregaFatura(fatura);
    }

    private void carregaCartoesDeCredito() {
        cartaoDAO = new CartaoDeCreditoDAO(activity);
        cartoes = cartaoDAO.getLista();

        cartaoAdapter = new ArrayAdapter<CartaoDeCredito>(activity, new android.R.layout().simple_spinner_item, cartoes);
        cartaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        campoCartaoDeCredito.setAdapter(cartaoAdapter);

        campoCartaoDeCredito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cartaoSelecionado = (CartaoDeCredito) parent.getItemAtPosition(position);
                fatura.setCartaoDeCredito(cartaoSelecionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



}
