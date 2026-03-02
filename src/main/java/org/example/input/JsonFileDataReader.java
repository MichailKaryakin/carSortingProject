package org.example.input;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.DataReadException;
import org.example.model.Car;
import org.example.util.Validator;

import java.io.File;
import java.util.List;

public class JsonFileDataReader implements DataReader {
    private final String filePath;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Validator validator = new Validator();

    public JsonFileDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Car> read() {
        try {
            File file = new File(filePath);
            List<Car> cars = mapper.readValue(file, new TypeReference<>() {
            });

            for (Car car : cars) {
                validator.validate(car);
            }
            return cars;
        } catch (Exception e) {
            throw new DataReadException("Ошибка при обработке файла: " + filePath, e);
        }
    }
}
