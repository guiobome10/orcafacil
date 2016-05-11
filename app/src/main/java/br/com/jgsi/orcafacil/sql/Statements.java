package br.com.jgsi.orcafacil.sql;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public interface Statements extends Tabelas {

    String SELECT_ALL = "SELECT * FROM ";
    String SELECT_COUNT = "SELECT COUNT(*) ";
    String SELECT_ALL_DESPESAS =
            "SELECT  DESPESA.id, " +
                    "CATEGORIA_DESPESA.id as id_categoria, " +
                    "CATEGORIA_DESPESA.nome as nome_categoria, " +
                    "CATEGORIA_DESPESA.imagem as imagem_categoria, " +
                    "CONTA.id AS id_conta, " +
                    "CONTA.nome AS nome_conta, " +
                    "CONTA.saldo AS saldo_conta, " +
                    "CONTA.moeda AS moeda_conta, " +
                    "DESPESA.valor, " +
                    "DESPESA.data, " +
                    "DESPESA.detalhes, " +
                    "ORCAMENTO.id as id_orcamento, " +
                    "ORCAMENTO.valor as valor_orcamento, " +
                    "ORCAMENTO.saldo as saldo_orcamento, " +
                    "ORCAMENTO.data_inicio as data_inicio_orcamento, " +
                    "ORCAMENTO.data_fim as data_fim_orcamento, " +
                    "ORCAMENTO.status as status_orcamento, " +
                    "ORCAMENTO.periodicidade as periodicidade_orcamento " +
                    "FROM DESPESA " +
                    "JOIN CATEGORIA_DESPESA " +
                    "ON (DESPESA.id_categoria = CATEGORIA_DESPESA.id) " +
                    "JOIN CONTA " +
                    "ON (DESPESA.id_conta = CONTA.id) " +
                    "LEFT OUTER JOIN ORCAMENTO " +
                    "ON (DESPESA.id_orcamento = ORCAMENTO.id);";
    String ORDER_BY_DATA_DESPESA = "ORDER BY DESPESA.data;";
    String SELECT_ALL_DESPESAS_ORDENADO_POR_DATA = SELECT_ALL_DESPESAS.replace(";"," ") + ORDER_BY_DATA_DESPESA;
    String SELECT_ALL_DESPESAS_POR_DATA = SELECT_ALL_DESPESAS.replace(";"," ") + "WHERE DESPESA.data >= ? AND DESPESA.data <= ? " + ORDER_BY_DATA_DESPESA;
    String SELECT_DESPESAS_POR_CONTA = SELECT_ALL_DESPESAS.replace(";", " ") + "WHERE CONTA.id = ? " + ORDER_BY_DATA_DESPESA;
    String SELECT_DESPESAS_POR_CATEGORIA_DATA = SELECT_ALL_DESPESAS.replace(";", " ") + "WHERE CATEGORIA_DESPESA.id = ? AND DESPESA.data >= ? AND DESPESA.data <=? " + ORDER_BY_DATA_DESPESA;
    String AGG_SALDO_DESPESAS = "TOTAL_DESPESAS";
    String SELECT_TOTAL_DESPESAS_MES_ATUAL =
             "SELECT SUM("+ TB_DESPESA_C_VALOR +") AS " + AGG_SALDO_DESPESAS + " FROM DESPESA " +
                    "WHERE " + TB_DESPESA_C_DATA + ">=? AND " + TB_DESPESA_C_DATA + "<=? " +
            ORDER_BY_DATA_DESPESA;


    String SELECT_ALL_RECEITAS =
        "SELECT  RECEITA.id, " +
                "CATEGORIA_RECEITA.id as id_categoria, " +
                "CATEGORIA_RECEITA.nome as nome_categoria, " +
                "CATEGORIA_RECEITA.imagem as imagem_categoria, " +
                "CONTA.id AS id_conta, " +
                "CONTA.nome AS nome_conta, " +
                "CONTA.saldo AS saldo_conta, " +
                "CONTA.moeda AS moeda_conta, " +
                "RECEITA.valor, " +
                "RECEITA.data, " +
                "RECEITA.detalhes " +
        "FROM RECEITA " +
        "JOIN CATEGORIA_RECEITA " +
        "ON (RECEITA.id_categoria = CATEGORIA_RECEITA.id) " +
        "JOIN CONTA " +
        "ON (RECEITA.id_conta = CONTA.id);";
    String ORDER_BY_DATA_RECEITA = "ORDER BY RECEITA.data;";
    String SELECT_ALL_RECEITAS_ORDENADO_POR_DATA = SELECT_ALL_RECEITAS.replace(";", " ") + ORDER_BY_DATA_RECEITA;
    String SELECT_RECEITAS_POR_CONTA = SELECT_ALL_RECEITAS.replace(";", " ") + "WHERE CONTA.id = ? " + ORDER_BY_DATA_RECEITA;
    String AGG_SALDO_RECEITAS = "TOTAL_RECEITAS";
    String SELECT_TOTAL_RECEITAS_MES_ATUAL =
            "SELECT SUM("+ TB_RECEITA_C_VALOR +") AS " + AGG_SALDO_RECEITAS + " FROM RECEITA " +
                    "WHERE " + TB_RECEITA_C_DATA + ">=? AND " + TB_RECEITA_C_DATA + "<=? " +
                    ORDER_BY_DATA_RECEITA;


    String SELECT_ALL_ORCAMENTOS =
        "SELECT ORCAMENTO.id, " +
            "CATEGORIA_DESPESA.id as id_categoria, " +
            "CATEGORIA_DESPESA.nome as nome_categoria, " +
            "CATEGORIA_DESPESA.imagem as imagem_categoria, " +
            "ORCAMENTO.valor, " +
            "ORCAMENTO.saldo, " +
            "ORCAMENTO.data_inicio, " +
            "ORCAMENTO.data_fim, " +
            "ORCAMENTO.status, " +
            "ORCAMENTO.periodicidade " +
        "FROM ORCAMENTO " +
        "JOIN CATEGORIA_DESPESA " +
        "ON (ORCAMENTO.id_categoria = CATEGORIA_DESPESA.id);";
    String ORDER_BY_DATA_ORCAMENTO = "ORDER BY ORCAMENTO.data_fim;" ;
    String SELECT_ALL_ORCAMENTOS_ORDERNADO_POR_DATA = SELECT_ALL_ORCAMENTOS.replace(";", " ") + ORDER_BY_DATA_ORCAMENTO;
    String SELECT_ORCAMENTO_BY_CATEG_DATA = SELECT_ALL_ORCAMENTOS.replace(";", " ") + "WHERE CATEGORIA_DESPESA.id=?1 AND (ORCAMENTO.data_inicio <=?2 AND ORCAMENTO.data_fim >=?3 );";
    String SELECT_ORCAMENTO_BY_CATEG_DATA_ORDENADO_DATA = SELECT_ORCAMENTO_BY_CATEG_DATA.replace(";", " ") + ORDER_BY_DATA_ORCAMENTO;
    String SELECT_ORCAMENTO_BY_CATEG_DATA_COM_INTERSECCOES = SELECT_ORCAMENTO_BY_CATEG_DATA.replace(";", " ").replace("AND (", "AND ((")
            + "OR (ORCAMENTO.data_inicio <=?2 AND ORCAMENTO.data_inicio >=?3 ) "
            + "OR (ORCAMENTO.data_fim >=?2 AND ORCAMENTO.data_fim <=?3 ) "
            + "OR (ORCAMENTO.data_fim >=?2 AND ORCAMENTO.data_inicio <=?3 )) " + ORDER_BY_DATA_ORCAMENTO;
    String SELECT_ALL_ORCAMENTOS_POR_DATA = SELECT_ALL_ORCAMENTOS.replaceAll(";", " ") + "WHERE ORCAMENTO.data_inicio <=?1 AND ORCAMENTO.data_fim >=?2 " + ORDER_BY_DATA_ORCAMENTO;

    String SELECT_ALL_CARTOES_DE_CREDITO =
            "SELECT  CARTAO_DE_CREDITO.id, " +
                    "CONTA.id AS id_conta, " +
                    "CONTA.nome AS nome_conta, " +
                    "CONTA.saldo AS saldo_conta, " +
                    "CONTA.moeda AS moeda_conta, " +
                    "CARTAO_DE_CREDITO.ultimos_quatro_digitos, " +
                    "CARTAO_DE_CREDITO.bandeira " +
                    "FROM CARTAO_DE_CREDITO " +
                    "JOIN CONTA " +
                    "ON (CARTAO_DE_CREDITO.id_conta = CONTA.id);";

    String SELECT_CARTAO_DE_CREDITO_POR_CONTA = SELECT_ALL_CARTOES_DE_CREDITO.replace(";"," ") + "WHERE CONTA.id = ?;";

    String FATURA_ORDER_BY_DATA_FINAL_CICLO = " ORDER BY " + TB_FATURA_NAME + "." + TB_FATURA_C_DATA_FINAL_CICLO + ";";
    String SELECT_ALL_FATURAS =
            "SELECT " +     TB_FATURA_NAME + "." + TB_FATURA_C_ID                           + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_DATA_INICIAL_CICLO           + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_DATA_FINAL_CICLO             + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_DATA_VENCIMENTO              + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_VALOR                        + ", " +
                 TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ID
                                + " AS " + TB_FATURA_J_ID_CARTAO                            + ", " +
                 TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ULTIMOS_QUATRO_DIGITOS
                                + " AS " + TB_FATURA_J_ULTIMOS_QUATRO_DIGITOS_CARTAO        + ", " +
                 TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_BANDEIRA
                                + " AS " + TB_FATURA_J_BANDEIRA_CARTAO                      + ", " +
                             TB_CONTA_NAME + "." + TB_CONTA_C_ID
                                + " AS " + TB_FATURA_J_ID_CONTA                             + ", " +
                             TB_CONTA_NAME + "." + TB_CONTA_C_NOME
                                + " AS " + TB_FATURA_J_NOME_CONTA                   + ", " +
                             TB_CONTA_NAME + "." + TB_CONTA_C_SALDO
                                + " AS " + TB_FATURA_J_SALDO_CONTA                  + ", " +
                             TB_CONTA_NAME + "." + TB_CONTA_C_MOEDA
                                + " AS " + TB_FATURA_J_MOEDA_CONTA                  + " " +
            "FROM " + TB_FATURA_NAME + " " +
            "JOIN " + TB_CARTAO_DE_CREDITO_NAME +
            "  ON (" + TB_FATURA_NAME + "." + TB_FATURA_C_ID_CARTAO_DE_CREDITO + "="
                     + TB_CARTAO_DE_CREDITO_NAME + "." + TB_FATURA_C_ID + ") " +
            "JOIN " + TB_CONTA_NAME +
            "  ON (" + TB_CARTAO_DE_CREDITO_NAME + "." + TB_FATURA_J_ID_CONTA + "="
                     + TB_CONTA_NAME + "." + TB_FATURA_C_ID + ");";
    String SELECT_FATURA_POR_CARTAO_DE_CREDITO = SELECT_ALL_FATURAS.replace(";", " ") +
            "WHERE " + TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ID + "=? " +
            FATURA_ORDER_BY_DATA_FINAL_CICLO;
    String SELECT_FATURA_POR_CARTAO_DATA_INICIO_FIM_VENCIMENTO = SELECT_ALL_FATURAS.replace(";", " ") +
            "WHERE " + TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ID + "=? " +
            "  AND " + TB_FATURA_NAME + "." + TB_FATURA_C_DATA_INICIAL_CICLO + "=? " +
            "  AND " + TB_FATURA_NAME + "." + TB_FATURA_C_DATA_FINAL_CICLO + "=? " +
            "  AND " + TB_FATURA_NAME + "." + TB_FATURA_C_DATA_VENCIMENTO + "=? " +
            FATURA_ORDER_BY_DATA_FINAL_CICLO;


    String DESP_CARTAO_ORDER_BY_DATA = " ORDER BY " + TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_DATA + ";";
    String SELECT_ALL_DESPESAS_CARTAO =
            "SELECT " +     TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_ID              + ", " +
                            TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_VALOR           + ", " +
                            TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_DATA            + ", " +
                            TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_DETALHES        + ", " +
                            TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_QT_PARCELAS     + ", " +
                            TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_NR_PARCELA      + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_ID
                                            + " AS " + TB_FATURA_J_ID_FATURA                + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_DATA_INICIAL_CICLO
                                            + " AS " + TB_FATURA_J_DT_INI_CICLO_FATURA      + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_DATA_FINAL_CICLO
                                            + " AS " + TB_FATURA_J_DT_FIN_CICLO_FATURA      + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_DATA_VENCIMENTO
                                            + " AS " + TB_FATURA_J_DT_VENC_FATURA           + ", " +
                            TB_FATURA_NAME + "." + TB_FATURA_C_VALOR
                                            + " AS " + TB_FATURA_J_VALOR_FATURA             + ", " +
                    TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ID
                                            + " AS " + TB_CARTAO_J_ID                       + ", " +
                    TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_BANDEIRA
                                            + " AS " + TB_CARTAO_J_BANDEIRA                 + ", " +
                    TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ULTIMOS_QUATRO_DIGITOS
                                            + " AS " + TB_CARTAO_J_ULTIMOS_QUATRO_DIGITOS   + ", " +
                    TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ID_CONTA
                                            + " AS " + TB_CARTAO_J_ID_CONTA                 + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_ID
                                            + " AS " + TB_ORCAMENTO_J_ID                    + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_VALOR
                                            + " AS " + TB_ORCAMENTO_J_VALOR                 + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_SALDO
                                            + " AS " + TB_ORCAMENTO_J_SALDO                 + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_DATA_INICIO
                                            + " AS " + TB_ORCAMENTO_J_DATA_INICIO           + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_DATA_FIM
                                            + " AS " + TB_ORCAMENTO_J_DATA_FIM              + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_STATUS
                                            + " AS " + TB_ORCAMENTO_J_STATUS                + ", " +
                    TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_PERIODICIDADE
                                            + " AS " + TB_ORCAMENTO_J_PERIODICIDADE         + ", " +
                    TB_CATEGORIA_DESPESA_NAME + "." + TB_CAT_DESP_C_ID
                                            + " AS " + TB_CAT_DESP_J_ID                     + ", " +
                    TB_CATEGORIA_DESPESA_NAME + "." + TB_CAT_DESP_C_NOME
                                            + " AS " + TB_CAT_DESP_J_NOME                   + ", " +
                    TB_CATEGORIA_DESPESA_NAME + "." + TB_CAT_DESP_C_IMAGEM
                                            + " AS " + TB_CAT_DESP_J_IMAGEM                 + " " +
            "FROM " + TB_DESPESA_CARTAO_NAME + " " +
            "JOIN " + TB_FATURA_NAME +
            "  ON (" + TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_ID_FATURA + "="
                     + TB_FATURA_NAME + "." + TB_FATURA_C_ID + ") " +
            "JOIN " + TB_CARTAO_DE_CREDITO_NAME +
            "  ON (" + TB_CARTAO_DE_CREDITO_NAME + "." + TB_CARTAO_C_ID + "="
                     + TB_FATURA_NAME + "." + TB_FATURA_C_ID_CARTAO_DE_CREDITO + ") " +
            "JOIN " + TB_CATEGORIA_DESPESA_NAME +
            "  ON (" + TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_ID_CATEGORIA + "="
                     + TB_CATEGORIA_DESPESA_NAME + "." + TB_CAT_DESP_C_ID + ") " +
            "LEFT OUTER JOIN " + TB_ORCAMENTO_NAME +
            "  ON (" + TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_ID_ORCAMENTO + "="
                     + TB_ORCAMENTO_NAME + "." + TB_ORCAMENTO_C_ID + ");";
    String SELECT_ALL_DESPESAS_CARTAO_ORDENADA_POR_DATA = SELECT_ALL_DESPESAS_CARTAO.replace(";", " ") + DESP_CARTAO_ORDER_BY_DATA;
    String SELECT_ALL_DESPESAS_CARTAO_POR_FATURA = SELECT_ALL_DESPESAS_CARTAO.replace(";", " ") +
            "WHERE " + TB_DESPESA_CARTAO_NAME + "." + TB_DESP_CARTAO_C_ID_FATURA + "=? " + DESP_CARTAO_ORDER_BY_DATA;

}
