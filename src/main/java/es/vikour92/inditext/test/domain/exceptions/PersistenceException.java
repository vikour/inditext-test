package es.vikour92.inditext.test.domain.exceptions;

public abstract class PersistenceException extends RuntimeException {

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(String message) {
            super(message);
    }
}

