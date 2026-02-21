package org.example.input;

import org.example.model.Car;

import java.util.List;

// написать для этого интерфейса три класса с разной реализацией заполнения списка (файл, рандом, вручную)
// не забыть упаковать и пробросить исключение
public interface DataReader {
    List<Car> read() throws Exception;
}
