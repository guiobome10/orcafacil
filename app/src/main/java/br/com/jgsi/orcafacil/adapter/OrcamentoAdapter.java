package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Orcamento;

/**
 * Created by guilherme.costa on 29/01/2016.
 */
public class OrcamentoAdapter extends BaseAdapter{

    private List<Orcamento> orcamentos;
    private Activity activity;

    public OrcamentoAdapter(List<Orcamento> orcamentos, Activity activity) {
        this.orcamentos = orcamentos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return orcamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return orcamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Orcamento)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = activity.getLayoutInflater().inflate(R.layout.orcamento_item, null);

        Orcamento orcamento = (Orcamento) getItem(position);

        //Recuperar e popula os campos do xml.
        ImageView campoImagemCategoria = (ImageView) item.findViewById(R.id.item_orcamento_imagem_categoria);
        if(campoImagemCategoria != null && orcamento.getCategoria() != null) campoImagemCategoria.setImageBitmap(orcamento.getCategoria().getImagem().getBitmap());
        TextView campoCategoria = (TextView) item.findViewById(R.id.item_orcamento_categoria);
        if(campoCategoria != null && orcamento.getCategoria() != null) campoCategoria.setText(orcamento.getCategoria().getNome());
        TextView campoValor = (TextView) item.findViewById(R.id.item_orcamento_valor);
        if(campoValor != null) campoValor.setText(activity.getResources().getString(R.string.valor) + ": " + orcamento.getValorFormatoDinheiro());
        TextView campoSaldo = (TextView) item.findViewById(R.id.item_orcamento_saldo);
        if(campoSaldo != null) campoSaldo.setText(activity.getResources().getString(R.string.saldo) + ": " + orcamento.getSaldoFormatoDinheiro());
        if(orcamento.getSaldo() < 0){
            campoSaldo.setTextColor(Color.RED);
        }else {
            campoSaldo.setTextColor(Color.GREEN);
        }
        TextView campoData = (TextView) item.findViewById(R.id.item_orcamento_data);
        if(campoData != null) campoData.setText(orcamento.getPeriodo());
        TextView campoPeriodicidade = (TextView) item.findViewById(R.id.item_orcamento_periodicidade);
        if(campoPeriodicidade != null) campoPeriodicidade.setText(orcamento.getPeriodicidade().nome());


        return item;

    }
}
