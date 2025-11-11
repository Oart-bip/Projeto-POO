
package util;

import java.util.concurrent.atomic.AtomicLong;

public class GeradorId {
    
    private static final AtomicLong contadorCliente = new AtomicLong(1);
    private static final AtomicLong contadorProduto = new AtomicLong(1);
    private static final AtomicLong contadorPedido = new AtomicLong(1);

    public static Long gerarIdCliente() {
        return contadorCliente.getAndIncrement();
    }

    public static Long gerarIdProduto() {
        return contadorProduto.getAndIncrement();
    }

    public static Long gerarIdPedido() {
        return contadorPedido.getAndIncrement();
    }
}