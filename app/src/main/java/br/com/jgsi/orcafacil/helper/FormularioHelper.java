package br.com.jgsi.orcafacil.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.dao.OrcamentoDAO;
import br.com.jgsi.orcafacil.model.Bandeira;
import br.com.jgsi.orcafacil.model.CartaoDeCredito;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Conta;
import br.com.jgsi.orcafacil.model.Despesa;
import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.model.Moeda;
import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.Periodicidade;
import br.com.jgsi.orcafacil.model.Receita;
import br.com.jgsi.orcafacil.util.DateFormatter;
import br.com.jgsi.orcafacil.util.DecimalFormatter;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public class FormularioHelper {

    private final TextView campoInfo;
    private CheckBox campoParcelada;
    private EditText campoUltimosQuatroDigitos;
    private EditText campoData;
    private EditText campoDataInicioCiclo;
    private EditText campoDataFinalCiclo;
    private EditText campoDataVencimento;
    private EditText campoNome;
    private EditText campoSaldo;
    private EditText campoValor;
    private EditText campoDetalhe;
    private EditText campoQuantidadeParcelas;
    private Spinner campoFatura;
    private Spinner campoCartao;
    private Spinner campoConta;
    private Spinner campoMoeda;
    private Spinner campoBandeira;
    private Spinner campoPeriodicidade;
    private ImageView campoImagem;
    private Activity activity;
    private int layoutID;
    private Calendar data;

    public FormularioHelper(Activity activity, int layoutID) {
        this.activity = activity;
        this.layoutID = layoutID;

        switch (layoutID){
            case R.layout.conta_form :
                campoNome = (EditText) activity.findViewById(R.id.conta_form_nome);
                campoSaldo = (EditText) activity.findViewById(R.id.conta_form_saldo);
                campoMoeda = (Spinner) activity.findViewById(R.id.conta_form_moeda);
                break;
            case R.layout.categoria_form :
                campoNome = (EditText) activity.findViewById(R.id.categoria_form_nome);
                campoImagem = (ImageView) activity.findViewById(R.id.categoria_form_imagem);
                break;
            case R.layout.receita_form :
                campoData = (EditText) activity.findViewById(R.id.receita_form_data);
                campoValor = (EditText) activity.findViewById(R.id.receita_form_valor);
                campoConta = (Spinner) activity.findViewById(R.id.receita_form_conta);
                break;
            case R.layout.orcamento_form :
                campoValor = (EditText) activity.findViewById(R.id.orcamento_form_valor);
                campoPeriodicidade = (Spinner) activity.findViewById(R.id.orcamento_form_periodicidade);
                campoData = (EditText) activity.findViewById(R.id.orcamento_form_data_fim);
                break;
            case R.layout.despesa_form :
                campoConta = (Spinner) activity.findViewById(R.id.despesa_form_conta);
                campoData = (EditText) activity.findViewById(R.id.despesa_form_data);
                campoDetalhe = (EditText) activity.findViewById(R.id.despesa_form_detalhe);
                campoValor = (EditText) activity.findViewById(R.id.despesa_form_valor);
                break;
            case R.layout.cartao_de_credito_form :
                campoConta = (Spinner) activity.findViewById(R.id.cartao_form_conta);
                campoUltimosQuatroDigitos = (EditText) activity.findViewById(R.id.cartao_form_quatro_ultimos_digitos);
                campoBandeira = (Spinner) activity.findViewById(R.id.cartao_form_bandeira);
                break;
            case R.layout.fatura_form :
                campoCartao = (Spinner) activity.findViewById(R.id.fatura_form_cartao);
                campoDataInicioCiclo = (EditText) activity.findViewById(R.id.fatura_form_data_inicio_ciclo);
                campoDataFinalCiclo = (EditText) activity.findViewById(R.id.fatura_form_data_final_ciclo);
                campoDataVencimento = (EditText) activity.findViewById(R.id.fatura_form_data_vencimento);
                campoValor = (EditText) activity.findViewById(R.id.fatura_form_valor);
                break;
            case R.layout.despesa_cartao_form :
                campoFatura = (Spinner) activity.findViewById(R.id.despesa_cartao_form_fatura);
                campoData = (EditText) activity.findViewById(R.id.despesa_cartao_form_data);
                campoValor = (EditText) activity.findViewById(R.id.despesa_cartao_form_valor);
                campoDetalhe = (EditText) activity.findViewById(R.id.despesa_cartao_form_detalhes);
                campoParcelada = (CheckBox) activity.findViewById(R.id.despesa_cartao_form_parcelada);
                campoQuantidadeParcelas = (EditText) activity.findViewById(R.id.despesa_cartao_form_quantidade_parcelas);
                break;
        }
        campoInfo = (TextView) activity.findViewById(R.id.campo_erro);
    }

    public Conta pegaDadosForm(Conta conta){
        conta.setNome(getNome());
        conta.setSaldo(Double.valueOf(getSaldo()));
        conta.setMoeda(Moeda.get(getMoeda()));
        return conta;
    }

    public CategoriaDespesa pegaDadosForm(CategoriaDespesa categoria) {
        categoria.setNome(getNome());
        return categoria;
    }

    public CategoriaReceita pegaDadosForm(CategoriaReceita categoria){
        categoria.setNome(getNome());
        return categoria;
    }

    public Despesa pegaDadosForm(Despesa despesa) {
        despesa.setValor(getValor());
        despesa.setData(getData());
        despesa.setConta(getConta());
        despesa.setDetalhes(getDetalhes());
        return despesa;
    }

    public Orcamento pegaDadosForm(Orcamento orcamento) {
        orcamento.setValor(getValor());
        orcamento.setSaldo(getValor());
        orcamento.setPeriodicidade(getPeriodicidade());
        if(orcamento.getPeriodicidade().equals(Periodicidade.PERZONALIZADA)){
            orcamento.setDataFim(getData());
        } else {
            orcamento.calculaDataFim();
        }
        return orcamento;
    }

    public Receita pegaDadosForm(Receita receita) {
        receita.setData(getData());
        receita.setConta(getConta());
        receita.setValor(getValor());

        return receita;
    }

    public CartaoDeCredito pegaDadosForm(CartaoDeCredito cartaoDeCredito) {
        cartaoDeCredito.setBandeira(getBandeira());
        cartaoDeCredito.setConta(getConta());
        cartaoDeCredito.setUltimosQuatroDigitos(getUltimosQuatroDigitos());

        return cartaoDeCredito;
    }

    public Fatura pegaDadosForm(Fatura fatura) {
        fatura.setCartaoDeCredito(getCartao());
        fatura.setValor(getValor());
        fatura.setDataInicialCiclo(getDataInicioCiclo());
        fatura.setDataFinalCiclo(getDataFinalCiclo());
        fatura.setDataVencimento(getDataVencimento());
        return fatura;
    }

    public DespesaCartao pegaDadosForm(DespesaCartao despesaCartao) {
        despesaCartao.setFatura((Fatura) campoFatura.getSelectedItem());
        despesaCartao.setData(getData());
        despesaCartao.setValor(getValor());
        despesaCartao.setDetalhes(getDetalhes());
        if(campoParcelada.isChecked()){
            despesaCartao.setQuantidadeParcelas(getQuantidadeParcelas());
        } else {
            despesaCartao.setQuantidadeParcelas(1);
        }
        return despesaCartao;
    }


    private String getUltimosQuatroDigitos() {
        return campoUltimosQuatroDigitos.getText().toString();
    }

    private Bandeira getBandeira() {
        return (Bandeira) campoBandeira.getSelectedItem();
    }

    public boolean validaDadosForm(){
        switch (layoutID){
            case R.layout.conta_form :
                 return validaFormConta(activity);
            case R.layout.categoria_form :
                return validaFormCategoriaReceita(activity);
            case R.layout.receita_form :
                return validaFormReceita(activity);
            case R.layout.despesa_form :
                return validaFormDespesa(activity);
            case R.layout.cartao_de_credito_form :
                return validaFormCartao(activity);
            case R.layout.fatura_form :
                return validaFormFatura(activity);
            case R.layout.despesa_cartao_form :
                return validaFormDespesaCartao(activity);
        }

        return true;
    }

    private boolean validaFormDespesaCartao(Activity activity) {
        if(isCampoDataVazio()){
            setErroCampo(campoData, R.string.campo_data_obrigatorio);
            return false;
        }
        if(isCampoValorVazio() || isCampoValorZerado()){
            setErroCampo(campoValor, R.string.campo_valor_obrigatorio);
            return false;
        }
        if(campoParcelada.isChecked() && campoQuantidadeParcelas.getText().toString().isEmpty()){
            setErroCampo(campoQuantidadeParcelas, R.string.campo_quantidade_parcelas_obrigatorio);
            return false;
        }
        return true;
    }
    private boolean validaFormFatura(Activity activity) {
        if(isCampoDataInicioCicloVazio()){
            setErroCampo(campoDataInicioCiclo, R.string.data_inicio_ciclo_obrigatorio);
            return false;
        }
        if(isCampoDataFinalCicloVazio()){
            setErroCampo(campoDataFinalCiclo, R.string.data_final_ciclo_obrigatorio);
            return false;
        }
        if(isCampoDataVencimentoVazio()){
            setErroCampo(campoDataVencimento, R.string.data_vencimento_obrigatorio);
            return false;
        }
        if(isCampoValorVazio() || isCampoValorZerado()){
            setErroCampo(campoValor, R.string.campo_valor_obrigatorio);
            return false;
        }
        return true;
    }

    private boolean validaFormCartao(Activity activity) {
        if(isCampoQuatroUltimosDigitosVazio() || isCampoQuatroUltimosDigitosIncompleto()){
            setErroCampo(campoUltimosQuatroDigitos, R.string.campo_ultimos_quatro_digitos_obrigatorio);
            return false;
        }
        return true;
    }

    private boolean validaFormDespesa(Activity activity) {
        if(isCampoValorVazio()){
            setErroCampo(campoValor, R.string.campo_valor_obrigatorio);
            return false;
        }
        if(isCampoDataVazio()){
            setErroCampo(campoData, R.string.campo_data_obrigatorio);
            return false;
        }
        if(isCampoValorZerado()){
            setErroCampo(campoValor, R.string.campo_valor_zerado);
            return false;
        }
        return true;
    }

    public boolean validaFormOrcamento(Activity activity, Orcamento orcamento) {
        if(isCampoValorVazio()){
            setErroCampo(campoValor, R.string.campo_valor_obrigatorio);
            return false;
        }
        if(isCampoPeriodicidadeVazio()){
            return false;
        }
        if(orcamento.getPeriodicidade().equals(Periodicidade.PERZONALIZADA)){
            if(isCampoDataVazio()){
                setErroCampo(campoData, R.string.data_fim_orcamento_obrigatorio);
                return false;
            }
        }

        OrcamentoDAO dao = new OrcamentoDAO(activity);
        List<Orcamento> orcamentos = dao.getByPeriodoCategoriaComInterseccoes(orcamento.getDataInicio(), orcamento.getDataFim(), orcamento.getCategoria());
        if(!orcamentos.isEmpty()){
            setErro("Já existe um orcamento dessa categoria para o período informado.");
            return false;
        }
        return true;
    }

    private boolean validaFormReceita(Activity activity) {
        if(isCampoContaVazio()){
            return false;
        }
        if(isCampoDataVazio()){
            setErroCampo(campoData, R.string.campo_data_obrigatorio);
            return false;
        }
        if(isCampoValorVazio()){
            setErroCampo(campoValor, R.string.campo_valor_obrigatorio);
            return false;
        }

        if(isCampoValorZerado()){
            setErroCampo(campoValor, R.string.campo_valor_zerado);
            return false;
        }
        return true;
    }

    private boolean validaFormCategoriaReceita(Context context) {
        if(isCampoNomeInvalido()){
            setErroCampo(campoNome, R.string.nome_categoria_obrigatorio);
            return false;
        }
        return true;
    }

    private boolean validaFormConta(Context context) {
        if(isCampoNomeInvalido()){
            setErroCampo(campoNome, R.string.nome_conta_obrigatorio);
            return false;
        }
        if(getSaldo() == null || (getSaldo() != null && getSaldo().isEmpty())){
            setErroCampo(campoSaldo, R.string.saldo_conta_obrigatorio);
            return false;
        }
        if(getMoeda() == null || (getNome() != null && getMoeda().isEmpty())){
            return false;
        }
        return true;
    }

    private void setErroCampo(EditText campo, int stringResourceID) {
        campo.setError(activity.getResources().getString(stringResourceID));
    }


    private boolean isCampoNomeInvalido() {
        return getNome() == null || (getNome() != null && getNome().isEmpty());
    }

    public String getNome() {
        return campoNome.getText().toString();
    }

    public String getSaldo() {
        return campoSaldo.getText().toString();
    }

    public String getMoeda() {
        return (String) campoMoeda.getSelectedItem();
    }

    public Calendar getDataInicioCiclo() {
        return DateFormatter.formata(campoDataInicioCiclo.getText().toString());
    }

    public Calendar getDataFinalCiclo() {
        return DateFormatter.formata(campoDataFinalCiclo.getText().toString());
    }

    public Calendar getDataVencimento() {
        return DateFormatter.formata(campoDataVencimento.getText().toString());
    }

    public Calendar getData() {
        return DateFormatter.formata(campoData.getText().toString());
    }

    public Conta getConta() {
        return (Conta) campoConta.getSelectedItem();
    }

    public CartaoDeCredito getCartao() {
        return (CartaoDeCredito)campoCartao.getSelectedItem();
    }

    public Double getValor() {
        return DecimalFormatter.formata(campoValor.getText().toString());
    }

    public Periodicidade getPeriodicidade() {
        return Periodicidade.get((String) campoPeriodicidade.getSelectedItem());
    }

    public String getDetalhes() {
        return campoDetalhe.getText().toString();
    }

    public boolean isCampoContaVazio() {
        return campoConta == null;
    }

    public boolean isCampoDataVazio() {
        return campoData == null || (campoData != null && campoData.getText().toString().isEmpty());
    }

    public boolean isCampoValorVazio() {
        return campoValor == null || (campoValor != null && campoValor.getText().toString().isEmpty());
    }

    public boolean isCampoPeriodicidadeVazio() {
        return getPeriodicidade() == null;
    }

    public boolean isCampoValorZerado() {
        return getValor().equals(0);
    }

    public boolean isCampoQuatroUltimosDigitosVazio() {
        return getUltimosQuatroDigitos().isEmpty();
    }

    public boolean isCampoQuatroUltimosDigitosIncompleto() {
        return getUltimosQuatroDigitos().length() < 4;
    }

    public boolean isCampoDataInicioCicloVazio() {
        return campoDataInicioCiclo.getText().toString().isEmpty();
    }

    public boolean isCampoDataFinalCicloVazio() {
        return campoDataFinalCiclo.getText().toString().isEmpty();
    }

    public boolean isCampoDataVencimentoVazio() {
        return campoDataVencimento.getText().toString().isEmpty();
    }

    private Integer getQuantidadeParcelas() {
        return Integer.valueOf(campoQuantidadeParcelas.getText().toString());
    }

    private void setErro(String mensagemErro){
        campoInfo.setText(mensagemErro);
        campoInfo.setVisibility(View.VISIBLE);
        campoInfo.setTextColor(activity.getResources().getColor(R.color.colorError));
    }
}
