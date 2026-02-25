package org.example.util;

// кастомное исключение, в которое будут упаковываться все ошибки при чтении данных
public class DataImportException extends RuntimeException {
    public DataImportException(String message) {
        super(message);
    }
}
