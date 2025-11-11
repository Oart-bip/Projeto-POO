
package repository;

import enums.StatusPedido;
import model.Pedido;
import java.util.*;
import java.util.stream.Collectors;

public class PedidoRepository {
    
    private final Map<Long, Pedido> pedidos;

    public PedidoRepository() {
        this.pedidos = new HashMap<>();
    }

    public synchronized void salvar(Pedido pedido) {
        pedidos.put(pedido.getId(), pedido);
    }

    public synchronized Optional<Pedido> buscarPorId(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public synchronized List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    public synchronized List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidos.values().stream()
            .filter(p -> p.getStatus() == status)
            .collect(Collectors.toList());
    }

    public synchronized List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidos.values().stream()
            .filter(p -> p.getCliente().getId().equals(clienteId))
            .collect(Collectors.toList());
    }

    public synchronized boolean existe(Long id) {
        return pedidos.containsKey(id);
    }

    public synchronized int contarTotal() {
        return pedidos.size();
    }

    public synchronized int contarPorStatus(StatusPedido status) {
        return (int) pedidos.values().stream()
            .filter(p -> p.getStatus() == status)
            .count();
    }
}