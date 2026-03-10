package org.example.specialSorting;

import org.example.model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialSortingTest {

    @Test
    @DisplayName("Сортировка объектов только с чётным значением мощности")
    void shouldSortOnlyEvenPowerCars() {
        Car c1 = new Car.Builder().model("A").power(101).build();
        Car c2 = new Car.Builder().model("B").power(500).build();
        Car c3 = new Car.Builder().model("C").power(300).build();
        Car c4 = new Car.Builder().model("D").power(151).build();

        List<Car> cars = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
        SpecialBubbleSort sorter = new SpecialBubbleSort();

        sorter.sort(cars, Car.compareByPower());

        assertThat(cars.get(0).getPower()).isEqualTo(101);
        assertThat(cars.get(1).getPower()).isEqualTo(300);
        assertThat(cars.get(2).getPower()).isEqualTo(500);
        assertThat(cars.get(3).getPower()).isEqualTo(151);
    }

    @Test
    @DisplayName("Сортировка объектов только с чётным годом выпуска")
    void shouldSortOnlyEvenYears() {
        Car c1 = new Car.Builder().year(2021).build();
        Car c2 = new Car.Builder().year(2024).build();
        Car c3 = new Car.Builder().year(2020).build();

        List<Car> cars = new ArrayList<>(Arrays.asList(c1, c2, c3));
        new SpecialQuickSort().sort(cars, Car.compareByYear());

        assertThat(cars.get(0).getYear()).isEqualTo(2021);
        assertThat(cars.get(1).getYear()).isEqualTo(2020);
        assertThat(cars.get(2).getYear()).isEqualTo(2024);
    }

    @Test
    @DisplayName("Игнорирование сортировки для нечисловых полей")
    void shouldNotFailOnStringSorting() {
        Car c1 = new Car.Builder().model("Z").power(100).build();
        Car c2 = new Car.Builder().model("A").power(100).build();

        List<Car> cars = new ArrayList<>(Arrays.asList(c1, c2));
        SpecialMergeSort sorter = new SpecialMergeSort();

        sorter.sort(cars, Car.compareByModel());

        assertThat(cars).containsExactly(c1, c2);
    }
}
