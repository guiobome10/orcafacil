package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.DespesaCartao;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class DespesaCartaoAdapter extends BaseAdapter {

    private List<DespesaCartao> despesasCartao;
    private Activity activity;

    public DespesaCartaoAdapter(List<DespesaCartao> despesasCartao, Activity activity) {
        this.despesasCartao = despesasCartao;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return despesasCartao.size();
    }

    @Override
    public Object getItem(int position) {
        return despesasCartao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((DespesaCartao)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = activity.getLayoutInflater().inflate(R.layout.despesa_cartao_item, null);

        DespesaCartao despesaCartao = (DespesaCartao) getItem(position);

        //Recuperar e popula os campos do xml.
        ImageView campoImagemCategoria = (ImageView) item.findViewById(R.id.item_despesa_cartao_imagem_categoria);
        if(campoImagemCategoria != null) campoImagemCategoria.setImageBitmap(despesaCartao.getCategoria().getImagem().getBitmap());
        TextView campoCategoria = (TextView) item.findViewById(R.id.item_despesa_cartao_categoria);
        if(campoCategoria != null) campoCategoria.setText(despesaCartao.getCategoria().getNome());
        TextView campoValor = (TextView) item.findViewById(R.id.item_despesa_cartao_valor);
        if(campoValor != null) campoValor.setText(despesaCartao.getValorFormatoDinheiro());
        TextView campoFatura = (TextView) item.findViewById(R.id.item_despesa_cartao_fatura);
        if(campoFatura != null) campoFatura.setText(despesaCartao.getFatura().toString());
        TextView campoData = (TextView) item.findViewById(R.id.item_despesa_cartao_data);
        if(campoData != null) campoData.setText(despesaCartao.getDataFormatada());

        return item;

    }
}
