
package repository;

import model.Cliente;
import java.util.*;

public class ClienteRepository {
    
    private final Map<Long, Cliente> clientes;

    public ClienteRepository() {
        this.clientes = new HashMap<>();
    }

    public void salvar(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    public boolean existe(Long id) {
        return clientes.containsKey(id);
    }

    public void remover(Long id) {
        clientes.remove(id);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clientes.values().stream()
            .filter(c -> c.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    public int contarTotal() {
        return clientes.size();
    }
}