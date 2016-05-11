package br.com.jgsi.orcafacil.model;

/**
 * Created by guilherme.costa on 29/01/2016.
 */
public enum StatusOrcamento {

    EM_ABERTO   (1, "Em aberto"),
    PLANEJADO   (2, "Planejado"),
    EXCEDIDO    (3, "Excedido"),
    FINALIZADO  (4, "Finalizado");

    private int id;
    private String descricao;

    StatusOrcamento(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int id(){ return id;};
    public String nome(){ return descricao;};

    public static StatusOrcamento get(int status) {
        for (StatusOrcamento statusOrcamento : StatusOrcamento.values()) {
            if(statusOrcamento.id == status){
                return  statusOrcamento;
            }
        }
        return EM_ABERTO;
    }

}
