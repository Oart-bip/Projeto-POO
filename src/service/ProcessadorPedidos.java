
package service;

import enums.StatusPedido;
import model.Pedido;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProcessadorPedidos implements Runnable {
    
    private final BlockingQueue<Pedido> fila;
    private final PedidoService pedidoService;
    private volatile boolean executando;
    private final int tempoProcessamento;

    public ProcessadorPedidos(PedidoService pedidoService, int tempoProcessamentoSegundos) {
        this.fila = new LinkedBlockingQueue<>();
        this.pedidoService = pedidoService;
        this.executando = false;
        this.tempoProcessamento = tempoProcessamentoSegundos * 1000;
    }

    public void adicionarNaFila(Pedido pedido) {
        try {
            fila.put(pedido);
            System.out.println("[FILA] Pedido #" + pedido.getId() + " adicionado à fila");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Erro ao adicionar pedido na fila: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        executando = true;
        System.out.println("[PROCESSADOR] Iniciado e aguardando pedidos...");
        
        while (executando) {
            try {
                Pedido pedido = fila.take();
                processar(pedido);
            } catch (InterruptedException e) {
                if (executando) {
                    System.err.println("[PROCESSADOR] Interrompido: " + e.getMessage());
                }
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("[PROCESSADOR] Encerrado");
    }

    private void processar(Pedido pedido) {
        try {
            System.out.println("[PROCESSADOR] Iniciando processamento do Pedido #" + pedido.getId());
            
            pedidoService.alterarStatus(pedido.getId(), StatusPedido.PROCESSANDO);
            System.out.println("[PROCESSADOR] Pedido #" + pedido.getId() + " -> PROCESSANDO");
            
            Thread.sleep(tempoProcessamento);
            
            pedidoService.alterarStatus(pedido.getId(), StatusPedido.FINALIZADO);
            System.out.println("[PROCESSADOR] Pedido #" + pedido.getId() + " -> FINALIZADO ✓");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[PROCESSADOR] Processamento interrompido: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[PROCESSADOR] Erro ao processar pedido: " + e.getMessage());
        }
    }

    public void parar() {
        executando = false;
        System.out.println("[PROCESSADOR] Solicitando parada...");
    }

    public int tamanhoDaFila() {
        return fila.size();
    }

    public boolean estaExecutando() {
        return executando;
    }
}