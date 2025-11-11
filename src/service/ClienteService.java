
package service;

import exception.ValidacaoException;
import model.Cliente;
import repository.ClienteRepository;
import util.GeradorId;

import java.util.List;

public class ClienteService {
    
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente cadastrar(String nome, String email) throws ValidacaoException {
        validarEmailUnico(email);
        
        Long id = GeradorId.gerarIdCliente();
        Cliente cliente = new Cliente(id, nome, email);
        repository.salvar(cliente);
        
        return cliente;
    }

    private void validarEmailUnico(String email) throws ValidacaoException {
        if (repository.buscarPorEmail(email).isPresent()) {
            throw new ValidacaoException("Já existe um cliente com este e-mail");
        }
    }

    public Cliente buscarPorId(Long id) throws ValidacaoException {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new ValidacaoException("Cliente não encontrado: " + id));
    }

    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }

    public void atualizar(Long id, String nome, String email) throws ValidacaoException {
        Cliente cliente = buscarPorId(id);
        
        // Verifica se o email já está em uso por outro cliente
        repository.buscarPorEmail(email).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                try {
                    throw new ValidacaoException("E-mail já está em uso por outro cliente");
                } catch (ValidacaoException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
        cliente.atualizarDados(nome, email);
        repository.salvar(cliente);
    }

    public int contarTotal() {
        return repository.contarTotal();
    }
}