package br.com.jgsi.orcafacil.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormCartaoDeCreditoFragment;
import br.com.jgsi.orcafacil.fragment.ListaCartaoDeCreditoFragment;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;

/**
 * Created by guilherme.costa on 10/03/2016.
 */
public class CartaoDeCreditoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartoes_de_credito);
        if(getIntent().getExtras() == null){
            listaCartoes();
        } else {
            selecionarCartaoDeCredito((CartaoDeCredito) getIntent().getExtras().getSerializable(getResources().getString(R.string.cartao_de_credito)));
        }

    }

    public void listaCartoes() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.faturas_view, new ListaCartaoDeCreditoFragment());
        ft.commit();
    }

    public void novoCartao(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.faturas_view, new FormCartaoDeCreditoFragment());
        ft.commit();
    }

    public void selecionarCartaoDeCredito(CartaoDeCredito cartaoDeCredito) {
        Bundle args = new Bundle();
        args.putSerializable(getResources().getString(R.string.cartao_de_credito), cartaoDeCredito);

        FormCartaoDeCreditoFragment formCartaoDeCreditoFragment = new FormCartaoDeCreditoFragment();
        formCartaoDeCreditoFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.faturas_view, formCartaoDeCreditoFragment);
        ft.commit();
    }

}
