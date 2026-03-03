package org.example.input;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.DataReadException;
import org.example.list.MyArrayList;
import org.example.model.Car;
import org.example.validation.Validator;

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
            List<Car> initialList = mapper.readValue(file, new TypeReference<List<Car>>() {});

            return initialList.stream()
                    .peek(validator::validate)
                    .collect(MyArrayList.toMyArrayList());
        } catch (Exception e) {
            throw new DataReadException("Ошибка при обработке файла: " + filePath, e);
        }
    }
}
