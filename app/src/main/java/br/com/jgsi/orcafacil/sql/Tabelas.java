package br.com.jgsi.orcafacil.sql;

/**
 * Created by guilherme.costa on 20/01/2016.
 */
public interface Tabelas {

    String TB_CATEGORIA_DESPESA_NAME = "CATEGORIA_DESPESA";
    String TB_CAT_DESP_C_ID = "id";
    String TB_CAT_DESP_C_NOME = "nome";
    String TB_CAT_DESP_C_IMAGEM = "imagem";
    String TB_CAT_DESP_J_ID = "id_categ_despesa";
    String TB_CAT_DESP_J_NOME = "nome_categ_despesa";
    String TB_CAT_DESP_J_IMAGEM = "imagem_categ_despesa";
    String TB_CATEGORIA_DESPESA_CREATE =
            "CREATE TABLE " + TB_CATEGORIA_DESPESA_NAME +
            "(" + TB_CAT_DESP_C_ID +    " INTEGER PRIMARY KEY, " +
                  TB_CAT_DESP_C_NOME +  " TEXT UNIQUE NOT NULL, " +
                  TB_CAT_DESP_C_IMAGEM+ " INTEGER );";
    String TB_CATEGORIA_DESPESA_DROP = "DROP TABLE IF EXISTS " + TB_CATEGORIA_DESPESA_NAME;

    String TB_CATEGORIA_RECEITA_NAME = "CATEGORIA_RECEITA";
    String TB_CATEGORIA_RECEITA_CREATE =
            "CREATE TABLE " + TB_CATEGORIA_RECEITA_NAME +
                    "(id INTEGER PRIMARY KEY," +
                    " nome TEXT UNIQUE NOT NULL," +
                    " imagem INTEGER );";
    String TB_CATEGORIA_RECEITA_DROP = "DROP TABLE IF EXISTS " + TB_CATEGORIA_RECEITA_NAME;

    String TB_CONTA_NAME = "CONTA";
    String TB_CONTA_C_ID = "id";
    String TB_CONTA_C_NOME = "nome";
    String TB_CONTA_C_SALDO = "saldo";
    String TB_CONTA_C_MOEDA = "moeda";
    String TB_CONTA_CREATE =
    "CREATE TABLE " + TB_CONTA_NAME +
            "( " + TB_CONTA_C_ID +  " INTEGER PRIMARY KEY, " +
            TB_CONTA_C_NOME  +      " TEXT UNIQUE NOT NULL, " +
            TB_CONTA_C_SALDO +      " REAL NOT NULL, " +
            TB_CONTA_C_MOEDA +      " TEXT NOT NULL );";
    String TB_CONTA_DROP = "DROP TABLE IF EXISTS " + TB_CONTA_NAME;

    String TB_DESPESA_NAME = "DESPESA";
    String TB_DESPESA_C_ID = "id";
    String TB_DESPESA_C_ID_CATEGORIA = "id_categoria";
    String TB_DESPESA_C_ID_CONTA = "id_conta";
    String TB_DESPESA_C_ID_ORCAMENTO = "id_orcamento";
    String TB_DESPESA_C_VALOR = "valor";
    String TB_DESPESA_C_DATA = "data";
    String TB_DESPESA_C_DETALHES = "detalhes";
    String TB_DESPESA_C_ID_PERIODICIDADE = "id_periodicidade";
    String TB_DESPESA_C_NR_REPETICOES = "numero_repeticoes";
    String TB_DESPESA_CREATE =
    "CREATE TABLE " + TB_DESPESA_NAME +
            "( " + TB_DESPESA_C_ID          + " INTEGER PRIMARY KEY," +
            TB_DESPESA_C_ID_CATEGORIA       + "  INTEGER NOT NULL," +
            TB_DESPESA_C_ID_CONTA           + "  INTEGER NOT NULL," +
            TB_DESPESA_C_ID_ORCAMENTO       + "  INTEGER," +
            TB_DESPESA_C_VALOR              + "  REAL NOT NULL," +
            TB_DESPESA_C_DATA               + "  TEXT NOT NULL," +
            TB_DESPESA_C_DETALHES           + "  TEXT," +
            TB_DESPESA_C_ID_PERIODICIDADE   + "  INTEGER," +
            TB_DESPESA_C_NR_REPETICOES      + "  INTEGER );";
    String TB_DESPESA_DROP = "DROP TABLE IF EXISTS " + TB_DESPESA_NAME;

    String TB_RECEITA_NAME = "RECEITA";
    String TB_RECEITA_C_ID = "id";
    String TB_RECEITA_C_ID_CATEGORIA = "id_categoria";
    String TB_RECEITA_C_ID_CONTA = "id_conta";
    String TB_RECEITA_C_VALOR = "valor";
    String TB_RECEITA_C_DATA = "data";
    String TB_RECEITA_C_DETALHES = "detalhes";
    String TB_RECEITA_CREATE =
    "CREATE TABLE " + TB_RECEITA_NAME +
            "(" + TB_RECEITA_C_ID           + " INTEGER PRIMARY KEY," +
             TB_RECEITA_C_ID_CATEGORIA      + "  INTEGER NOT NULL," +
             TB_RECEITA_C_ID_CONTA          + "  INTEGER NOT NULL," +
             TB_RECEITA_C_VALOR             + "  REAL NOT NULL," +
             TB_RECEITA_C_DATA              + "  TEXT NOT NULL," +
             TB_RECEITA_C_DETALHES          + "  TEXT );";
    String TB_RECEITA_DROP = "DROP TABLE IF EXISTS " + TB_RECEITA_NAME;

    String TB_ORCAMENTO_NAME = "ORCAMENTO";
    String TB_ORCAMENTO_C_ID = "id";
    String TB_ORCAMENTO_C_ID_CATEGORIA = "id_categoria";
    String TB_ORCAMENTO_C_VALOR = "valor";
    String TB_ORCAMENTO_C_SALDO = "saldo";
    String TB_ORCAMENTO_C_DATA_INICIO = "data_inicio";
    String TB_ORCAMENTO_C_DATA_FIM = "data_fim";
    String TB_ORCAMENTO_C_STATUS = "status";
    String TB_ORCAMENTO_C_PERIODICIDADE = "periodicidade";
    String TB_ORCAMENTO_J_ID = "id_orcamento";
    String TB_ORCAMENTO_J_ID_CATEGORIA = "id_categoria_orcamento";
    String TB_ORCAMENTO_J_VALOR = "valor_orcamento";
    String TB_ORCAMENTO_J_SALDO = "saldo_orcamento";
    String TB_ORCAMENTO_J_DATA_INICIO = "data_inicio_orcamento";
    String TB_ORCAMENTO_J_DATA_FIM = "data_fim_orcamento";
    String TB_ORCAMENTO_J_STATUS = "status_orcamento";
    String TB_ORCAMENTO_J_PERIODICIDADE = "periodicidade_orcamento";
    String TB_ORCAMENTO_CREATE =
    "CREATE TABLE " + TB_ORCAMENTO_NAME +
            "(" +
           TB_ORCAMENTO_C_ID            + " INTEGER PRIMARY KEY," +
           TB_ORCAMENTO_C_ID_CATEGORIA  + " INTEGER NOT NULL," +
           TB_ORCAMENTO_C_VALOR         + " REAL NOT NULL," +
           TB_ORCAMENTO_C_SALDO         + " REAL NOT NULL," +
           TB_ORCAMENTO_C_DATA_INICIO   + " TEXT ," +
           TB_ORCAMENTO_C_DATA_FIM      + " TEXT ," +
           TB_ORCAMENTO_C_STATUS        + " INTEGER NOT NULL," +
           TB_ORCAMENTO_C_PERIODICIDADE + " INTEGER" +
            ");";
    String TB_ORCAMENTO_DROP = "DROP TABLE IF EXISTS " + TB_ORCAMENTO_NAME;

    String TB_CARTAO_DE_CREDITO_NAME = "CARTAO_DE_CREDITO";
    String TB_CARTAO_C_ID = "id";
    String TB_CARTAO_C_ID_CONTA = "id_conta";
    String TB_CARTAO_C_ULTIMOS_QUATRO_DIGITOS = "ultimos_quatro_digitos";
    String TB_CARTAO_C_BANDEIRA = "bandeira";
    String TB_CARTAO_J_ID = "id_cartao";
    String TB_CARTAO_J_ID_CONTA = "id_conta_cartao";
    String TB_CARTAO_J_ULTIMOS_QUATRO_DIGITOS = "ultimos_quatro_digitos_cartao";
    String TB_CARTAO_J_BANDEIRA = "bandeira_cartao";
    String TB_CARTAO_DE_CREDITO_CREATE =
            "CREATE TABLE " + TB_CARTAO_DE_CREDITO_NAME +
                    "("+ TB_CARTAO_C_ID                     + " INTEGER PRIMARY KEY, " +
                         TB_CARTAO_C_ID_CONTA               + " INTEGER NOT NULL, " +
                         TB_CARTAO_C_ULTIMOS_QUATRO_DIGITOS +" TEXT NOT NULL, " +
                         TB_CARTAO_C_BANDEIRA               +" TEXT NOT NULL );";
    String TB_CARTAO_DE_CREDITO_DROP = "DROP TABLE IF EXISTS " + TB_CARTAO_DE_CREDITO_NAME;

    String TB_FATURA_NAME = "FATURA";
    String TB_FATURA_C_ID = "id";
    String TB_FATURA_C_DATA_INICIAL_CICLO = "data_inicial_ciclo";
    String TB_FATURA_C_DATA_FINAL_CICLO = "data_final_ciclo";
    String TB_FATURA_C_DATA_VENCIMENTO = "data_vencimento";
    String TB_FATURA_C_VALOR = "valor";
    String TB_FATURA_C_ID_CARTAO_DE_CREDITO = "id_cartao_de_credito";
    String TB_FATURA_J_ID_FATURA = "id_fatura";
    String TB_FATURA_J_DT_INI_CICLO_FATURA = "data_inicio_ciclo_fatura";
    String TB_FATURA_J_DT_FIN_CICLO_FATURA = "data_final_ciclo_fatura";
    String TB_FATURA_J_DT_VENC_FATURA = "data_vencimento_fatura";
    String TB_FATURA_J_VALOR_FATURA = "valor_fatura";

    String TB_FATURA_J_ID_CARTAO = "id_cartao";
    String TB_FATURA_J_BANDEIRA_CARTAO = "bandeira_cartao";
    String TB_FATURA_J_ULTIMOS_QUATRO_DIGITOS_CARTAO = "ultimos_quatro_digitos_cartao";
    String TB_FATURA_J_ID_CONTA = "id_conta";
    String TB_FATURA_J_NOME_CONTA = "nome_conta";
    String TB_FATURA_J_SALDO_CONTA = "saldo_conta";
    String TB_FATURA_J_MOEDA_CONTA = "moeda_conta";
    String TB_FATURA_CREATE =
            "CREATE TABLE " + TB_FATURA_NAME + "(" +
                TB_FATURA_C_ID +                    " INTEGER PRIMARY KEY, " +
                TB_FATURA_C_DATA_INICIAL_CICLO +    " TEXT NOT NULL, " +
                TB_FATURA_C_DATA_FINAL_CICLO +      " TEXT NOT NULL, " +
                TB_FATURA_C_DATA_VENCIMENTO +       " TEXT NOT NULL, " +
                TB_FATURA_C_VALOR  +                " REAL NOT NULL, " +
                TB_FATURA_C_ID_CARTAO_DE_CREDITO  + " INTEGER NOT NULL" +
            ");";
    String TB_FATURA_DROP = "DROP TABLE IF EXISTS " + TB_FATURA_NAME;

    String TB_DESPESA_CARTAO_NAME = "DESPESA_CARTAO";
    String TB_DESP_CARTAO_C_ID = "id";
    String TB_DESP_CARTAO_C_ID_CATEGORIA = "id_categoria";
    String TB_DESP_CARTAO_C_ID_FATURA = "id_fatura";
    String TB_DESP_CARTAO_C_ID_ORCAMENTO = "id_orcamento";
    String TB_DESP_CARTAO_C_VALOR = "valor";
    String TB_DESP_CARTAO_C_DATA = "data";
    String TB_DESP_CARTAO_C_DETALHES = "detalhes";
    String TB_DESP_CARTAO_C_QT_PARCELAS = "quantidade_parcelas";
    String TB_DESP_CARTAO_C_NR_PARCELA = "numero_parcela";

    String TB_DESPESA_CARTAO_CREATE =
            "CREATE TABLE " + TB_DESPESA_CARTAO_NAME +
                    "(" +
                    TB_DESP_CARTAO_C_ID +           " INTEGER PRIMARY KEY, " +
                    TB_DESP_CARTAO_C_ID_CATEGORIA + " INTEGER NOT NULL, " +
                    TB_DESP_CARTAO_C_ID_FATURA +    " INTEGER NOT NULL, " +
                    TB_DESP_CARTAO_C_ID_ORCAMENTO + " INTEGER, " +
                    TB_DESP_CARTAO_C_VALOR +        " REAL NOT NULL, " +
                    TB_DESP_CARTAO_C_DATA +         " TEXT NOT NULL, " +
                    TB_DESP_CARTAO_C_DETALHES +     " TEXT, " +
                    TB_DESP_CARTAO_C_QT_PARCELAS +  " INTEGER, " +
                    TB_DESP_CARTAO_C_NR_PARCELA +   " INTEGER" +
                    " );";
    String TB_DESPESA_CARTAO_DROP = "DROP TABLE IF EXISTS " + TB_DESPESA_CARTAO_NAME;

}
