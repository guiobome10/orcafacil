package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Conta;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public class ContaAdapter extends BaseAdapter {

    private List<Conta> contas;
    private Activity activity;

    public ContaAdapter(List<Conta> contas, Activity activity) {
        this.contas = contas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return contas.size();
    }

    @Override
    public Object getItem(int position) {
        return contas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = activity.getLayoutInflater().inflate(R.layout.conta_item, null);

        Conta conta = contas.get(position);

        //Recuperar e popula os campos do xml.
        TextView campoNome = (TextView) item.findViewById(R.id.item_conta_nome);
        if(campoNome != null) campoNome.setText(conta.getNome());
        TextView campoSaldo = (TextView) item.findViewById(R.id.item_conta_saldo);
        if(campoSaldo != null) {
            campoSaldo.setText(conta.getSaldoFormatado());
            if(conta.getSaldo() < 0){
                campoSaldo.setTextColor(Color.RED);
            }else {
                campoSaldo.setTextColor(Color.GREEN);
            }
        }

        return item;
    }
}
