package br.com.jgsi.orcafacil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormContasFragment;
import br.com.jgsi.orcafacil.fragment.ListaContasFragment;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.Receita;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public class ContaActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contas);
        listaContas();
    }

    public void novaConta(){
        iniciarFragment(new FormContasFragment(), R.id.contas_view);
    }

    public void listaContas(){
        iniciarFragment(new ListaContasFragment(), R.id.contas_view);
    }

    public void selecionarConta(Conta conta) {
        FormContasFragment formContasFragment = new FormContasFragment();
        formContasFragment.setArguments(criaBundleComConta(conta));
        iniciarFragment(formContasFragment, R.id.contas_view);
    }

    public void novaReceita(Conta conta) {
        Intent irParaReceitaForm = new Intent(this, ReceitaActivity.class);
        irParaReceitaForm.putExtras(criaBundleComReceita(conta));
        startActivity(irParaReceitaForm);
    }

    private Bundle criaBundleComReceita(Conta conta) {
        Receita receita = new Receita();
        receita.setConta(conta);
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.receita), receita);
        return args;
    }

    public void novoOrcamento(Conta conta) {
        Intent irParaNovoOrcamento = new Intent(this, OrcamentoActivity.class);
        irParaNovoOrcamento.putExtra(getResources().getString(R.string.orcamento), getResources().getString(R.string.orcamento));
        startActivity(irParaNovoOrcamento);
    }


    public void novaDespesa(Conta conta) {
        Intent irParaNovaDespesa = new Intent(this, DespesaActivity.class);
        irParaNovaDespesa.putExtras(criaBundleComDespesa(conta));
        startActivity(irParaNovaDespesa);
    }

    private Bundle criaBundleComDespesa(Conta conta) {
        Bundle args = new Bundle();
        Despesa despesa = new Despesa();
        despesa.setConta(conta);
        args.putSerializable(getResources().getString(R.string.despesa), despesa);
        return args;
    }

    private Bundle criaBundleComConta(Conta conta) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.conta), conta);
        return args;
    }

    private void iniciarFragment(Fragment fragment, int viewID ) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(viewID, fragment);
        ft.commit();
    }

}
