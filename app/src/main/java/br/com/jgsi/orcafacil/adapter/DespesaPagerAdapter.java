package br.com.jgsi.orcafacil.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;

import br.com.jgsi.orcafacil.fragment.ListaDespesasFragment;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 28/03/2016.
 */
public class DespesaPagerAdapter extends DefaultPagerAdapter {


    public static final Calendar DEFAULT_ITEM = DateUtil.dataInicioMesAtual();

    public DespesaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    protected Fragment getNewFragment() {
        return new ListaDespesasFragment();
    }

}
