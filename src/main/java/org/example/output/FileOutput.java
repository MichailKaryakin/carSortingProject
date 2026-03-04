package org.example.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class FileOutput {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void appendCarsToFile(Collection<Car> cars, String filePath) {

        try (FileWriter writer = new FileWriter(filePath, true)) {

            for (Car car : cars) {
                String json = mapper.writeValueAsString(car);
                writer.write(json);
                writer.write(System.lineSeparator());
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }
}