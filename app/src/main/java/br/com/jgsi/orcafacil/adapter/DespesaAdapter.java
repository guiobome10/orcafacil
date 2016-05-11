package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Despesa;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class DespesaAdapter extends BaseAdapter {

    private List<Despesa> despesas;
    private Activity activity;

    public DespesaAdapter(List<Despesa> despesas, Activity activity) {
        this.despesas = despesas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return despesas.size();
    }

    @Override
    public Object getItem(int position) {
        return despesas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Despesa)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = activity.getLayoutInflater().inflate(R.layout.despesa_item, null);

        Despesa despesa = (Despesa) getItem(position);

        //Recuperar e popula os campos do xml.
        ImageView campoImagemCategoria = (ImageView) item.findViewById(R.id.item_despesa_imagem_categoria);
        if(campoImagemCategoria != null) campoImagemCategoria.setImageBitmap(despesa.getCategoria().getImagem().getBitmap());
        TextView campoCategoria = (TextView) item.findViewById(R.id.item_despesa_categoria);
        if(campoCategoria != null) campoCategoria.setText(despesa.getCategoria().getNome());
        TextView campoValor = (TextView) item.findViewById(R.id.item_despesa_valor);
        if(campoValor != null) campoValor.setText(despesa.getValorFormatoDinheiro());
        TextView campoConta = (TextView) item.findViewById(R.id.item_despesa_conta);
        if(campoConta != null) campoConta.setText(despesa.getConta().getNome());
        TextView campoData = (TextView) item.findViewById(R.id.item_despesa_data);
        if(campoData != null) campoData.setText(despesa.getDataFormatada());


        return item;

    }
}
