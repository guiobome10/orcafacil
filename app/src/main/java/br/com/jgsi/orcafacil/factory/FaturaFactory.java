package br.com.jgsi.orcafacil.factory;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.model.DespesaCartao;
import br.com.jgsi.orcafacil.model.Fatura;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 14/03/2016.
 */
public class FaturaFactory {


    public static List<Fatura> geraFaturas(DespesaCartao despesaCartao) {
        List<Fatura> faturas = new ArrayList<Fatura>();
        despesaCartao.getFatura().setValor(despesaCartao.getValorParcela());
        faturas.add(despesaCartao.getFatura());
        for(int i = 1; i < despesaCartao.getQuantidadeParcelas(); i ++){
            Fatura fatura = new Fatura();
            fatura.setCartaoDeCredito(despesaCartao.getFatura().getCartaoDeCredito());
            fatura.setDataInicialCiclo(DateUtil.addMonthToDate(despesaCartao.getFatura().getDataInicialCiclo(), i));
            fatura.setDataFinalCiclo(DateUtil.addMonthToDate(despesaCartao.getFatura().getDataFinalCiclo(), i));
            fatura.setDataVencimento(DateUtil.addMonthToDate(despesaCartao.getFatura().getDataVencimento(), i));
            fatura.setValor(despesaCartao.getValorParcela());
            faturas.add(fatura);
        }
        return faturas;
    }
}
