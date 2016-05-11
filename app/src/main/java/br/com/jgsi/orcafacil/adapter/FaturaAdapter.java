package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.Fatura;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class FaturaAdapter extends BaseAdapter {

    private List<Fatura> faturas;
    private Activity activity;

    public FaturaAdapter(List<Fatura> faturas, Activity activity) {
        this.faturas = faturas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return faturas.size();
    }

    @Override
    public Object getItem(int position) {
        return faturas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Fatura)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = activity.getLayoutInflater().inflate(R.layout.fatura_item, null);

        Fatura fatura = (Fatura) getItem(position);

        //Recuperar e popula os campos do xml.
        TextView campoCartao = (TextView) item.findViewById(R.id.item_fatura_cartao);
        if(campoCartao != null) campoCartao.setText(fatura.getCartaoDeCredito().getAsReadableString());
        TextView campoDataInicialCiclo = (TextView) item.findViewById(R.id.item_fatura_data_inicio_ciclo);
        if(campoDataInicialCiclo != null) campoDataInicialCiclo.setText(fatura.getDataInicialCicloFormatada());
        TextView campoDataFinalCiclo = (TextView) item.findViewById(R.id.item_fatura_data_final_ciclo);
        if(campoDataFinalCiclo != null) campoDataFinalCiclo.setText(fatura.getDataFinalCicloFormatada());
        TextView campoDataVencimento = (TextView) item.findViewById(R.id.item_fatura_data_vencimento);
        if(campoDataVencimento != null) campoDataVencimento.setText(fatura.getDataVencimentoFormatada());
        TextView campoValor = (TextView) item.findViewById(R.id.item_fatura_valor);
        if(campoValor != null) campoValor.setText(fatura.getValorFormatoDinheiro());

        return item;

    }
}
