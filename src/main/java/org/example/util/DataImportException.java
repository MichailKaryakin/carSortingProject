package org.example.util;

// кастомное исключение, в которое будут упаковываться все ошибки при чтении данных
public class DataImportException extends Exception {
    public DataImportException(String message) {
        super(message);
    }
}
