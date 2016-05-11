package br.com.jgsi.orcafacil.activities;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormDespesaCartaoFragment;
import br.com.jgsi.orcafacil.fragment.FormFaturaFragment;
import br.com.jgsi.orcafacil.fragment.ListaDespesaCartaoFragment;
import br.com.jgsi.orcafacil.fragment.ListaFaturaFragment;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Fatura;

/**
 * Created by guilherme.costa on 10/03/2016.
 */
public class DespesaCartaoActivity extends CrudActivity<DespesaCartao> {

    public DespesaCartaoActivity() {
        super(new DespesaCartao(), R.layout.despesas_cartao, R.id.despesas_cartao_view, R.string.despesa_cartao
                , new FormDespesaCartaoFragment(), new ListaDespesaCartaoFragment());
    }

}
