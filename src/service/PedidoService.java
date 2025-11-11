
package service;

import enums.StatusPedido;
import exception.PedidoException;
import exception.ValidacaoException;
import model.Cliente;
import model.Pedido;
import model.Produto;
import repository.PedidoRepository;
import util.GeradorId;

import java.util.List;

public class PedidoService {
    
    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository repository, 
                        ClienteService clienteService, 
                        ProdutoService produtoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public Pedido criarPedido(Long clienteId) throws PedidoException, ValidacaoException {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        
        Long id = GeradorId.gerarIdPedido();
        Pedido pedido = new Pedido(id, cliente);
        repository.salvar(pedido);
        
        return pedido;
    }

    public void adicionarItem(Long pedidoId, Long produtoId, int quantidade) 
            throws PedidoException, ValidacaoException {
        Pedido pedido = buscarPorId(pedidoId);
        Produto produto = produtoService.buscarPorId(produtoId);
        
        pedido.adicionarItem(produto, quantidade);
        repository.salvar(pedido);
    }

    public void finalizarPedido(Long pedidoId) throws PedidoException, ValidacaoException {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.finalizarParaFila();
        repository.salvar(pedido);
    }

    public Pedido buscarPorId(Long id) throws PedidoException {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new PedidoException("Pedido não encontrado: " + id));
    }

    public List<Pedido> listarTodos() {
        return repository.listarTodos();
    }

    public List<Pedido> listarPorStatus(StatusPedido status) {
        return repository.buscarPorStatus(status);
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return repository.buscarPorCliente(clienteId);
    }

    public void alterarStatus(Long pedidoId, StatusPedido novoStatus) 
            throws PedidoException {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.alterarStatus(novoStatus);
        repository.salvar(pedido);
    }

    public int contarPorStatus(StatusPedido status) {
        return repository.contarPorStatus(status);
    }

    public int contarTotal() {
        return repository.contarTotal();
    }
}