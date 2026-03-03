package org.example.ui;

import org.example.input.*;
import org.example.list.MyArrayList;
import org.example.model.Car;
import org.example.service.SortingService;
import org.example.sorting.*;
import org.example.util.SearchUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private final Scanner scanner = new Scanner(System.in);
    private final SortingService sortingService = new SortingService();
    private List<Car> carList = new MyArrayList<>();

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            printMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> readData();
                case "2" -> sortData();
                case "3" -> displayData();
                case "4" -> searchByPowerSingleThreadAndParallel();
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
        System.out.println("4. Поиск по мощности (сравнение одного и нескольких потоков)");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void searchByPowerSingleThreadAndParallel() {
        if (carList.isEmpty()) {
            System.out.println("Список пуст! Сначала загрузите данные.");
            return;
        }

        System.out.print("Введите значение мощности для поиска: ");
        int targetPower = readInt();

        System.out.println("\nЗапуск сравнения производительности...");

        SearchUtils.countCarsByPowerSequential(carList, targetPower);
        SearchUtils.countCarsByPowerParallel(carList, targetPower);
    }

    private void readData() {
        System.out.println("\n--- Источник данных ---");
        System.out.println("1. Прочитать из файла (JSON)");
        System.out.println("2. Сгенерировать случайно");
        System.out.println("3. Ввести вручную");
        System.out.print("Ваш выбор: ");

        String choice = scanner.nextLine();
        DataReader dataReader = null;

        switch (choice) {
            case "1" -> dataReader = new JsonFileDataReader("src/main/resources/cars.json");
            case "2" -> {
                System.out.print("Введите желаемое количество элементов: ");
                int size = readInt();
                dataReader = new RandomDataReader(size);
            }
            case "3" -> dataReader = new ConsoleDataReader();
            default -> System.out.println("Неверный выбор источника.");
        }

        if (dataReader != null) {
            try {
                this.carList = dataReader.read();
                System.out.println("Данные успешно загружены! Элементов: " + carList.size());
            } catch (Exception e) {
                System.out.println("Ошибка при загрузке: " + e.getMessage());
            }
        }
    }

    private void sortData() {
        if (carList.isEmpty()) {
            System.out.println("Нечего сортировать! Добавьте данные.");
            return;
        }

        System.out.println("\n--- Выбор алгоритма ---");
        System.out.println("1 - Пузырьком, 2 - Выбором, 3 - Вставками, 4 - Быстрая");
        String algoChoice = scanner.nextLine();

        SortingStrategy strategy = switch (algoChoice) {
            case "1" -> new BubbleSort();
            case "2" -> new SelectionSort();
            case "3" -> new InsertionSort();
            case "4" -> new QuickSort();
            default -> null;
        };

        if (strategy == null) {
            System.out.println("Неверный алгоритм.");
            return;
        }

        System.out.println("\n--- Поле для сортировки ---");
        System.out.println("1 - Модель, 2 - Мощность, 3 - Год, 4 - По всем полям");
        String fieldChoice = scanner.nextLine();

        Comparator<Car> comparator = switch (fieldChoice) {
            case "1" -> Car.compareByModel();
            case "2" -> Car.compareByPower();
            case "3" -> Car.compareByYear();
            case "4" -> Car.compareByAllFields();
            default -> null;
        };

        if (comparator == null) {
            System.out.println("Неверное поле.");
            return;
        }

        sortingService.setStrategy(strategy);
        sortingService.executeSort(carList, comparator);
        System.out.println("Сортировка выполнена успешно!");
    }

    private void displayData() {
        if (carList.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            System.out.println("\n--- Список автомобилей ---");
            carList.forEach(System.out::println);
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число: ");
            }
        }
    }
}
