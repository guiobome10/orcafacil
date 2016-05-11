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

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.ContaActivity;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.helper.FormularioHelper;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Moeda;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public class FormContasFragment extends Fragment{

    private ContaActivity activity;
    private Conta conta;
    private Button botaoSalvar;
    private Spinner campoMoeda;
    private FormularioHelper helper;
    private ContaDAO dao;
    private EditText campoSaldo;
    private EditText campoNome;

    public FormContasFragment() {
        this.conta = new Conta();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutFormConta = inflater.inflate(R.layout.conta_form, container, false);
        activity = (ContaActivity)getActivity();


        campoNome = (EditText) layoutFormConta.findViewById(R.id.conta_form_nome);
        campoSaldo = (EditText) layoutFormConta.findViewById(R.id.conta_form_saldo);
        botaoSalvar = (Button) layoutFormConta.findViewById(R.id.btn_salvar);
        campoMoeda = (Spinner) layoutFormConta.findViewById(R.id.conta_form_moeda);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), new android.R.layout().simple_spinner_item, Moeda.getAsListString());
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        campoMoeda.setAdapter(adapter);

        campoMoeda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conta.setMoeda(Moeda.get((String) parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper = new FormularioHelper(activity, R.layout.conta_form);
                if (helper.validaDadosForm()) {
                    conta = helper.pegaDadosForm(conta);
                    dao = new ContaDAO(activity);

                    if(conta.getId() != null){
                        dao.atualizar(conta);
                    }else {
                        dao.inserir(conta);
                    }

                    Toast.makeText(getContext(), getResources().getString(R.string.conta) + " " + conta.getNome() +" salva com sucesso", Toast.LENGTH_LONG).show();
                    activity.listaContas();
                }
            }
        });

        if(getArguments() != null){
            conta = (Conta) getArguments().getSerializable(getResources().getString(R.string.conta));
            carregaConta(conta);
        }


        return layoutFormConta;
    }

    private void carregaConta(Conta conta) {
        campoNome.setText(conta.getNome());
        campoSaldo.setText(conta.getSaldo().toString());
    }


}
