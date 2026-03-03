package org.example.input;

import org.example.list.MyArrayList;
import org.example.model.Car;
import org.example.validation.Validator;

import java.util.List;
import java.util.Scanner;

public class ConsoleDataReader implements DataReader {
    private final Scanner scanner = new Scanner(System.in);
    private final Validator validator = new Validator();

    @Override
    public List<Car> read() {
        System.out.print("Введите количество машин: ");
        int count = readInt();

        return java.util.stream.IntStream.range(1, count + 1)
                .mapToObj(i -> {
                    System.out.println("--- Машина #" + i + " ---");
                    System.out.print("Модель: ");
                    String model = scanner.nextLine();
                    System.out.print("Мощность (л.с.): ");
                    int power = readInt();
                    System.out.print("Год выпуска: ");
                    int year = readInt();

                    return new Car.Builder()
                            .model(model)
                            .power(power)
                            .year(year)
                            .build();
                })
                .peek(validator::validate)
                .collect(MyArrayList.toMyArrayList());
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
