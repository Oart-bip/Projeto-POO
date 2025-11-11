
package model;

import exception.ValidacaoException;
import util.ValidadorEmail;

public class Cliente {
    
    private final Long id;
    private String nome;
    private String email;

    public Cliente(Long id, String nome, String email) throws ValidacaoException {
        this.id = id;
        validarEAtribuir(nome, email);
    }

    private void validarEAtribuir(String nome, String email) throws ValidacaoException {
        validarNome(nome);
        ValidadorEmail.validar(email);
        this.nome = nome;
        this.email = email;
    }

    private void validarNome(String nome) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome do cliente não pode ser vazio");
        }
        if (nome.trim().length() < 3) {
            throw new ValidacaoException("Nome do cliente deve ter pelo menos 3 caracteres");
        }
    }

    public void atualizarDados(String nome, String email) throws ValidacaoException {
        validarEAtribuir(nome, email);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("Cliente #%d: %s (%s)", id, nome, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}