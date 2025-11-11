
package enums;

public enum StatusPedido {
    ABERTO("Aberto"),
    FILA("Na Fila"),
    PROCESSANDO("Processando"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}