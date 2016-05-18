package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;

/**
 * Created by guilherme.costa on 25/01/2016.
 */
public class SaldoCartaoDeCreditoAdapter extends BaseAdapter {

    private List<CartaoDeCredito> cartoes;
    private Activity activity;

    public SaldoCartaoDeCreditoAdapter(List<CartaoDeCredito> cartoes, Activity activity) {
        this.cartoes = cartoes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return cartoes.size();
    }

    @Override
    public Object getItem(int position) {
        return cartoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((CartaoDeCredito)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = activity.getLayoutInflater().inflate(R.layout.saldo_cartao_item, null);

        CartaoDeCredito cartao = (CartaoDeCredito) getItem(position);

        //Recuperar e popula os campos do xml.
        TextView campoConta = (TextView) item.findViewById(R.id.item_cartao_conta);
        if(campoConta != null) campoConta.setText(cartao.getConta().getNome());
        TextView campoUltimosQuatroDigitos = (TextView) item.findViewById(R.id.item_cartao_ultimos_quatro_digitos);
        if(campoUltimosQuatroDigitos != null) campoUltimosQuatroDigitos.setText(cartao.getUltimosQuatroDigitos());
        TextView campoBandeira = (TextView) item.findViewById(R.id.item_cartao_bandeira);
        if(campoBandeira != null) campoBandeira.setText(cartao.getBandeira().toString());
        TextView campoSaldoFatura = (TextView) item.findViewById(R.id.item_cartao_valor_fatura_atual);
        if(campoSaldoFatura != null && cartao.getFaturaAtual() != null) campoSaldoFatura.setText(cartao.getFaturaAtual().getValorFormatoDinheiro());

        return item;

    }
}
