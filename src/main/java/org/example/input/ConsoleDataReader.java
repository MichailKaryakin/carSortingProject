package org.example.input;

import org.example.model.Car;
import org.example.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleDataReader implements DataReader {
    private final Scanner scanner = new Scanner(System.in);
    private final Validator validator = new Validator();

    @Override
    public List<Car> read() {
        List<Car> cars = new ArrayList<>();
        System.out.print("Введите количество машин: ");

        int count = readInt();

        for (int i = 0; i < count; i++) {
            System.out.println("--- Машина #" + (i + 1) + " ---");

            System.out.print("Модель: ");
            String model = scanner.nextLine();

            System.out.print("Мощность (л.с.): ");
            int power = readInt();

            System.out.print("Год выпуска: ");
            int year = readInt();

            Car car = new Car.Builder()
                    .model(model)
                    .power(power)
                    .year(year)
                    .build();

            validator.validate(car);
            cars.add(car);
        }
        return cars;
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Это не число. Попробуйте еще раз: ");
            }
        }
    }
}
