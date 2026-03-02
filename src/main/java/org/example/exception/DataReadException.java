package org.example.exception;

public class DataReadException extends RuntimeException {
    public DataReadException(String message) {
        super(message);
    }

    public DataReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
