package br.com.jgsi.orcafacil.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;

import br.com.jgsi.orcafacil.fragment.ListaDespesasFragment;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 04/04/2016.
 */
public abstract class DefaultPagerAdapter extends FragmentStatePagerAdapter {

    public static final Calendar DEFAULT_ITEM = DateUtil.dataInicioMesAtual();
    private Fragment fragment;

    public DefaultPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        fragment = getNewFragment();
        Bundle args = new Bundle();
        args.putString("dataListada", DateFormatter.formataAnoMesDia(DateUtil.addMonthToDate(DEFAULT_ITEM, position - getCentralItemPosition())));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 25;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    public Fragment getCentralItem() {
        return getItem(getCentralItemPosition());
    }

    public int getCentralItemPosition(){
        return getCount() / 2 + 1;
    }

    protected abstract Fragment getNewFragment();
}
