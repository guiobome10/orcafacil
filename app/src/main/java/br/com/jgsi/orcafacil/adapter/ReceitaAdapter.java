package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Receita;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class ReceitaAdapter extends BaseAdapter {

    private List<Receita> receitas;
    private Activity activity;

    public ReceitaAdapter(List<Receita> receitas, Activity activity) {
        this.receitas = receitas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return receitas.size();
    }

    @Override
    public Object getItem(int position) {
        return receitas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Receita)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = activity.getLayoutInflater().inflate(R.layout.receita_item, null);

        Receita receita = (Receita) getItem(position);

        //Recuperar e popula os campos do xml.
        ImageView campoImagemCategoria = (ImageView) item.findViewById(R.id.item_receita_imagem_categoria);
        if(campoImagemCategoria != null) campoImagemCategoria.setImageBitmap(receita.getCategoria().getImagem().getBitmap());
        TextView campoCategoria = (TextView) item.findViewById(R.id.item_receita_categoria);
        if(campoCategoria != null) campoCategoria.setText(receita.getCategoria().getNome());
        TextView campoValor = (TextView) item.findViewById(R.id.item_receita_valor);
        if(campoValor != null) campoValor.setText(receita.getValorFormatoDinheiro());
        TextView campoConta = (TextView) item.findViewById(R.id.item_receita_conta);
        if(campoConta != null) campoConta.setText(receita.getConta().getNome());
        TextView campoData = (TextView) item.findViewById(R.id.item_receita_data);
        if(campoData != null) campoData.setText(receita.getDataFormatada());


        return item;

    }
}
