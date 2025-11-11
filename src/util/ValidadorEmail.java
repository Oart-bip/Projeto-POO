
package util;

import exception.ValidacaoException;

public class ValidadorEmail {
    
    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static void validar(String email) throws ValidacaoException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidacaoException("E-mail não pode ser vazio");
        }
        
        if (!email.matches(REGEX_EMAIL)) {
            throw new ValidacaoException("E-mail inválido: " + email);
        }
    }

    public static boolean ehValido(String email) {
        try {
            validar(email);
            return true;
        } catch (ValidacaoException e) {
            return false;
        }
    }
}