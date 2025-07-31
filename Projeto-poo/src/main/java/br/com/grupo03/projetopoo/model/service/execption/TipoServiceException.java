package br.com.grupo03.projetopoo.model.service.execption;

/**
 * Exceção personalizada para erros específicos da camada de serviço de Tipo.
 * Herda de RuntimeException para não forçar o tratamento com try-catch (unchecked exception),
 * o que é uma prática comum em muitas arquiteturas modernas (ex: Spring).
 */
public class TipoServiceException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     * @param message a mensagem detalhando o erro.
     */
    public TipoServiceException(String message) {
        super(message);
    }

    /**
     * Construtor que aceita uma mensagem de erro e a causa original.
     * Útil para encapsular outras exceções.
     * @param message a mensagem detalhando o erro.
     * @param cause a exceção original que causou este erro.
     */
    public TipoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}