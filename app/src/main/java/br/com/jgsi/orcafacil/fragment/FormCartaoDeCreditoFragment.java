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
import br.com.jgsi.orcafacil.activities.CartaoDeCreditoActivity;
import br.com.jgsi.orcafacil.dao.CartaoDeCreditoDAO;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Bandeira;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.Conta;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class FormCartaoDeCreditoFragment extends Fragment {

    private Conta contaSelecionada;
    private CartaoDeCreditoActivity activity;
    private CartaoDeCredito cartaoDeCredito;

    //Listas
    private Bandeira[] bandeiras;
    private List<Conta> contas;

    //DAO's
    private ContaDAO contaDAO;
    private CartaoDeCreditoDAO cartaoDAO;

    //HELPER
    private FormularioHelper helper;

    //Adapters
    private ArrayAdapter<Conta> contaAdapter;
    private ArrayAdapter<Bandeira> bandeiraAdapter;

    //Campos da tela
    private Spinner campoConta;
    private EditText campoUltimosQuatroDigitos;
    private Spinner campoBandeira;
    private Button botaoSalvar;

    public FormCartaoDeCreditoFragment() {
        cartaoDeCredito = new CartaoDeCredito();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cartao_de_credito_form, null);

        activity = (CartaoDeCreditoActivity) getActivity();

        campoConta = (Spinner) layout.findViewById(R.id.cartao_form_conta);

        campoUltimosQuatroDigitos = (EditText) layout.findViewById(R.id.cartao_form_quatro_ultimos_digitos);
        campoUltimosQuatroDigitos.setText(cartaoDeCredito.getUltimosQuatroDigitos());

        campoBandeira = (Spinner) layout.findViewById(R.id.cartao_form_bandeira);

        botaoSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        return layout;
    }

    private void carregaCartaoDeCredito(CartaoDeCredito cartaoDeCredito) {
        if(cartaoDeCredito.getConta() != null) campoConta.setSelection(contaAdapter.getPosition(cartaoDeCredito.getConta()));
        campoUltimosQuatroDigitos.setText(cartaoDeCredito.getUltimosQuatroDigitos());
        campoBandeira.setSelection(bandeiraAdapter.getPosition(cartaoDeCredito.getBandeira()));
    }

    private void salvar() {
        helper = new FormularioHelper(activity, R.layout.cartao_de_credito_form);
        if(helper.validaDadosForm()){
            cartaoDeCredito = helper.pegaDadosForm(cartaoDeCredito);
            cartaoDAO = new CartaoDeCreditoDAO(activity);
            if(cartaoDeCredito.getId() == null){
                cartaoDAO.inserir(cartaoDeCredito);
            } else {
                cartaoDAO.atualizar(cartaoDeCredito);
            }
            Toast.makeText(activity, R.string.cartao_salvo, Toast.LENGTH_LONG).show();
            activity.listaCartoes();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaContas();
        carregaBandeiras();

        if(getArguments() != null){
            cartaoDeCredito = (CartaoDeCredito) getArguments().getSerializable(getResources().getString(R.string.cartao_de_credito));
            carregaCartaoDeCredito(cartaoDeCredito);
        }
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
                cartaoDeCredito.setConta(contaSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void carregaBandeiras(){
        bandeiras = Bandeira.values();
        bandeiraAdapter = new ArrayAdapter<Bandeira>(activity, new android.R.layout().simple_spinner_item, bandeiras);
        bandeiraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoBandeira.setAdapter(bandeiraAdapter);

        campoBandeira.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cartaoDeCredito.setBandeira((Bandeira)parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}
