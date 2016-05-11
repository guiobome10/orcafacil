package br.com.jgsi.orcafacil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.activities.CartaoDeCreditoActivity;
import br.com.jgsi.orcafacil.activities.ContaActivity;
import br.com.jgsi.orcafacil.activities.DespesaActivity;
import br.com.jgsi.orcafacil.activities.OrcamentoActivity;
import br.com.jgsi.orcafacil.activities.ReceitaActivity;
import br.com.jgsi.orcafacil.adapter.ContaAdapter;
import br.com.jgsi.orcafacil.adapter.SaldoCartaoDeCreditoAdapter;
import br.com.jgsi.orcafacil.dao.CartaoDeCreditoDAO;
import br.com.jgsi.orcafacil.dao.ContaDAO;
import br.com.jgsi.orcafacil.dao.SaldoDAO;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

public class PrincipalActivity extends FragmentActivity {

    private ListView listaContas;
    private ListView listaCartoes;
    private TextView totalReceita;
    private TextView totalDespesa;
    private TextView saldoDespesaXReceita;
    private ContaDAO contaDAO;
    private CartaoDeCreditoDAO cartaoDAO;
    private SaldoDAO saldoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        recuperaCamposView();
    }

    private void recuperaCamposView() {
        listaContas = (ListView) findViewById(R.id.contas);
        listaCartoes = (ListView) findViewById(R.id.cartoes_de_credito);
        totalReceita = (TextView) findViewById(R.id.total_receita);
        totalDespesa = (TextView) findViewById(R.id.total_despesa);
        saldoDespesaXReceita = (TextView) findViewById(R.id.saldo_despesa_x_receita);
    }

    @Override
    protected void onResume() {
        carregaListaContas();
        carregaListaCartoes();
        carregaValores();

        super.onResume();
    }

    private void carregaListaContas() {
        contaDAO = new ContaDAO(this);
        listaContas.setAdapter(new ContaAdapter(contaDAO.getLista(), this));
    }

    private void carregaListaCartoes() {
        cartaoDAO = new CartaoDeCreditoDAO(this);
        listaCartoes.setAdapter(new SaldoCartaoDeCreditoAdapter(cartaoDAO.listarCartoesComSaldoFatura(), this));
    }

    private void carregaValores() {
        saldoDAO = new SaldoDAO(this);
        Double totalReceitas = saldoDAO.getTotalReceitasMesAtual();
        Double totalDespesas = saldoDAO.getTotalDespesasMesAtual();
        Double saldoDespesasXReceitas = totalReceitas - totalDespesas;
        totalReceita.setText(DecimalFormatter.formataDinheiro(totalReceitas));
        totalDespesa.setText(DecimalFormatter.formataDinheiro(totalDespesas));
        saldoDespesaXReceita.setText(DecimalFormatter.formataDinheiro(saldoDespesasXReceitas));
        if(saldoDespesasXReceitas < 0.) {
            saldoDespesaXReceita.setTextColor(Color.RED);
        } else {
            saldoDespesaXReceita.setTextColor(Color.GREEN);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.conta :
                Intent irParaContaActivity = new Intent(this, ContaActivity.class);
                startActivity(irParaContaActivity);
                break;
            case R.id.receita :
                Intent irParaReceitaActivity = new Intent(this, ReceitaActivity.class);
                startActivity(irParaReceitaActivity);
                break;
            case R.id.orcamento :
                Intent irParaOrcamentoActivity = new Intent(this, OrcamentoActivity.class);
                startActivity(irParaOrcamentoActivity);
                break;
            case R.id.despesa :
                Intent irParaDespesaActivity = new Intent(this, DespesaActivity.class);
                startActivity(irParaDespesaActivity);
                break;
            case R.id.cartao_de_credito :
                Intent irParaCartaoDeCreditoActivity = new Intent(this, CartaoDeCreditoActivity.class);
                startActivity(irParaCartaoDeCreditoActivity);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
