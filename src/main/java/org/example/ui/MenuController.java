package org.example.ui;

import org.example.model.Car;
import org.example.service.SortingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private final Scanner scanner = new Scanner(System.in);
    private final SortingService sortingService = new SortingService();
    private List<Car> carList = new ArrayList<>();

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            printMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> fillDataMenu();
                case "2" -> sortDataMenu();
                case "3" -> displayData();
                case "0" -> isRunning = false;
                default -> System.out.println("Нет такого пункта");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n--- Главное меню ---");
        System.out.println("1. Заполнить данные");
        System.out.println("2. Сортировать список");
        System.out.println("3. Вывести список на экран");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void fillDataMenu() {
        System.out.println("\n--- Источник данных ---");
        System.out.println("1. Прочитать из файла");
        System.out.println("2. Сгенерировать случайно");
        System.out.println("3. Ввести вручную");

        // здесь в зависимости от ввода пользователя вызывается заполнение
    }

    private void sortDataMenu() {
        if (carList.isEmpty()) {
            System.out.println("Нечего сортировать! Добавьте данные.");
            return;
        }
        System.out.println("\n--- Настройка сортировки ---");

        // выбор компаратора и алгоритма
    }

    private void displayData() {
        if (carList.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            carList.forEach(System.out::println);
        }
    }
}
