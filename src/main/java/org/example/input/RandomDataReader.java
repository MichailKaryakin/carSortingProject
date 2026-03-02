package org.example.input;

import org.example.model.Car;
import org.example.util.Validator;

import java.util.ArrayList;
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
        List<Car> cars = new ArrayList<>();
        String[] models = {"Audi A4", "BMW M3", "Tesla Model S", "Lada Vesta"};

        for (int i = 0; i < count; i++) {
            Car car = new Car.Builder()
                    .model(models[random.nextInt(models.length)])
                    .power(80 + random.nextInt(400))
                    .year(1995 + random.nextInt(30))
                    .build();

            validator.validate(car);
            cars.add(car);
        }
        return cars;
    }
}
