package br.com.jgsi.orcafacil.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.fragment.FormFaturaFragment;
import br.com.jgsi.orcafacil.fragment.ListaFaturaFragment;
import br.com.jgsi.orcafacil.model.Fatura;

/**
 * Created by guilherme.costa on 10/03/2016.
 */
public class FaturaActivity extends CrudActivity<Fatura> {

    public FaturaActivity() {
        super(new Fatura(), R.layout.faturas, R.id.faturas_view, R.string.fatura, new FormFaturaFragment(), new ListaFaturaFragment());
    }

}
