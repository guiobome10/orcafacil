package br.com.jgsi.orcafacil.factory;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.model.Orcamento;
import br.com.jgsi.orcafacil.model.StatusOrcamento;
import br.com.jgsi.orcafacil.util.DateUtil;

/**
 * Created by guilherme.costa on 04/04/2016.
 */
public class OrcamentoFactory {

    public static List<Orcamento> criarListaDeOrcamentosParaRepeticoesDo(Orcamento orcamento, Integer quantidadeRepeticoes){
        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        for(int i = 0; i < quantidadeRepeticoes; i++ ){
            Orcamento aux = (Orcamento) orcamento.clone();
            aux.setDataInicio(DateUtil.addDayToDate(orcamento.getDataInicio(), (i+1) * orcamento.getPeriodicidade().quantidade()));
            aux.setDataFim(DateUtil.addDayToDate(orcamento.getDataFim(), (i+1) * orcamento.getPeriodicidade().quantidade()));
            aux.setStatus(StatusOrcamento.PLANEJADO);
            orcamentos.add(aux);
        }

        return orcamentos;
    }
}
