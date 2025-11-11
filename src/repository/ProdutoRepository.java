
package repository;

import enums.CategoriaProduto;
import model.Produto;
import java.util.*;
import java.util.stream.Collectors;

public class ProdutoRepository {
    
    private final Map<Long, Produto> produtos;

    public ProdutoRepository() {
        this.produtos = new HashMap<>();
    }

    public void salvar(Produto produto) {
        produtos.put(produto.getId(), produto);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtos.get(id));
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    public List<Produto> buscarPorCategoria(CategoriaProduto categoria) {
        return produtos.values().stream()
            .filter(p -> p.getCategoria() == categoria)
            .collect(Collectors.toList());
    }

    public boolean existe(Long id) {
        return produtos.containsKey(id);
    }

    public void remover(Long id) {
        produtos.remove(id);
    }

    public int contarTotal() {
        return produtos.size();
    }
}