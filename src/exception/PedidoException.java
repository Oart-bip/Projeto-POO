
package exception;

public class PedidoException extends Exception {
    
    public PedidoException(String mensagem) {
        super(mensagem);
    }

    public PedidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}