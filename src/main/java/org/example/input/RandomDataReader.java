package org.example.input;

import org.example.list.MyArrayList;
import org.example.model.Car;
import org.example.validation.Validator;

import java.util.List;
import java.util.Random;

public class RandomDataReader implements DataReader {
    private final int count;
    private final Random random = new Random();
    private final Validator validator = new Validator();

    public RandomDataReader(int count) {
        this.count = count;
    }

    @Override
    public List<Car> read() {
        String[] models = {"Audi A4", "BMW M3", "Tesla Model S", "Lada Vesta"};

        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> new Car.Builder()
                        .model(models[random.nextInt(models.length)])
                        .power(80 + random.nextInt(400))
                        .year(1995 + random.nextInt(30))
                        .build())
                .peek(validator::validate)
                .collect(MyArrayList.toMyArrayList());
    }
}
