
package enums;

public enum CategoriaProduto {
    ALIMENTOS("Alimentos"),
    ELETRONICOS("Eletrônicos"),
    LIVROS("Livros"),
    VESTUARIO("Vestuário"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaProduto(String descricao) {
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