// Arquivo: src/model/Produto.java
package model;

import enums.CategoriaProduto;
import exception.ValidacaoException;

public class Produto {
    
    private final Long id;
    private String nome;
    private double preco;
    private CategoriaProduto categoria;

    public Produto(Long id, String nome, double preco, CategoriaProduto categoria) 
            throws ValidacaoException {
        this.id = id;
        validarEAtribuir(nome, preco, categoria);
    }

    private void validarEAtribuir(String nome, double preco, CategoriaProduto categoria) 
            throws ValidacaoException {
        validarNome(nome);
        validarPreco(preco);
        validarCategoria(categoria);
        
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    private void validarNome(String nome) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome do produto não pode ser vazio");
        }
    }

    private void validarPreco(double preco) throws ValidacaoException {
        if (preco <= 0) {
            throw new ValidacaoException("Preço deve ser positivo");
        }
    }

    private void validarCategoria(CategoriaProduto categoria) throws ValidacaoException {
        if (categoria == null) {
            throw new ValidacaoException("Categoria não pode ser nula");
        }
    }

    public void atualizarDados(String nome, double preco, CategoriaProduto categoria) 
            throws ValidacaoException {
        validarEAtribuir(nome, preco, categoria);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("Produto #%d: %s - R$ %.2f [%s]", 
            id, nome, preco, categoria);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}