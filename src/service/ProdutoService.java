
package service;

import enums.CategoriaProduto;
import exception.ValidacaoException;
import model.Produto;
import repository.ProdutoRepository;
import util.GeradorId;

import java.util.List;

public class ProdutoService {
    
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto cadastrar(String nome, double preco, CategoriaProduto categoria) 
            throws ValidacaoException {
        Long id = GeradorId.gerarIdProduto();
        Produto produto = new Produto(id, nome, preco, categoria);
        repository.salvar(produto);
        
        return produto;
    }

    public Produto buscarPorId(Long id) throws ValidacaoException {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new ValidacaoException("Produto não encontrado: " + id));
    }

    public List<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public List<Produto> listarPorCategoria(CategoriaProduto categoria) {
        return repository.buscarPorCategoria(categoria);
    }

    public void atualizar(Long id, String nome, double preco, CategoriaProduto categoria) 
            throws ValidacaoException {
        Produto produto = buscarPorId(id);
        produto.atualizarDados(nome, preco, categoria);
        repository.salvar(produto);
    }

    public int contarTotal() {
        return repository.contarTotal();
    }
}