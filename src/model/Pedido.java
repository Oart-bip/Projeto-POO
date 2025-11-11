
package model;

import enums.StatusPedido;
import exception.PedidoException;
import exception.ValidacaoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    
    private final Long id;
    private final Cliente cliente;
    private final List<ItemPedido> itens;
    private StatusPedido status;
    private final LocalDateTime dataCriacao;

    public Pedido(Long id, Cliente cliente) throws PedidoException {
        validarCliente(cliente);
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
        this.dataCriacao = LocalDateTime.now();
    }

    private void validarCliente(Cliente cliente) throws PedidoException {
        if (cliente == null) {
            throw new PedidoException("Cliente não pode ser nulo");
        }
    }

    public void adicionarItem(Produto produto, int quantidade) 
            throws ValidacaoException, PedidoException {
        validarPedidoAberto();
        
        ItemPedido itemExistente = buscarItemPorProduto(produto);
        if (itemExistente != null) {
            itemExistente.adicionarQuantidade(quantidade);
        } else {
            itens.add(new ItemPedido(produto, quantidade));
        }
    }

    private void validarPedidoAberto() throws PedidoException {
        if (status != StatusPedido.ABERTO) {
            throw new PedidoException("Não é possível modificar pedido com status: " + status);
        }
    }

    private ItemPedido buscarItemPorProduto(Produto produto) {
        return itens.stream()
            .filter(item -> item.getProduto().equals(produto))
            .findFirst()
            .orElse(null);
    }

    public void finalizarParaFila() throws PedidoException {
        if (itens.isEmpty()) {
            throw new PedidoException("Pedido deve ter pelo menos um item");
        }
        this.status = StatusPedido.FILA;
    }

    public void alterarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    public double calcularValorTotal() {
        return itens.stream()
            .mapToDouble(ItemPedido::calcularSubtotal)
            .sum();
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public StatusPedido getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d - %s\n", id, status));
        sb.append(String.format("Cliente: %s\n", cliente.getNome()));
        sb.append(String.format("Data: %s\n", dataCriacao));
        sb.append("Itens:\n");
        itens.forEach(item -> sb.append("  ").append(item).append("\n"));
        sb.append(String.format("Total: R$ %.2f", calcularValorTotal()));
        return sb.toString();
    }
}