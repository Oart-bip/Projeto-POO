
package model;

import exception.ValidacaoException;

public class ItemPedido {
    
    private final Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) throws ValidacaoException {
        validarProduto(produto);
        validarQuantidade(quantidade);
        this.produto = produto;
        this.quantidade = quantidade;
    }

    private void validarProduto(Produto produto) throws ValidacaoException {
        if (produto == null) {
            throw new ValidacaoException("Produto não pode ser nulo");
        }
    }

    private void validarQuantidade(int quantidade) throws ValidacaoException {
        if (quantidade <= 0) {
            throw new ValidacaoException("Quantidade deve ser positiva");
        }
    }

    public void adicionarQuantidade(int quantidade) throws ValidacaoException {
        validarQuantidade(quantidade);
        this.quantidade += quantidade;
    }

    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s x%d = R$ %.2f", 
            produto.getNome(), quantidade, calcularSubtotal());
    }
}