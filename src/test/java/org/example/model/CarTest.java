package org.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {

    @Test
    @DisplayName("Создание объекта через Builder с проверкой всех полей")
    void builderShouldCreateCorrectCar() {
        Car car = new Car.Builder()
                .model("Tesla Model 3")
                .power(283)
                .year(2021)
                .build();

        assertThat(car.getModel()).isEqualTo("Tesla Model 3");
        assertThat(car.getPower()).isEqualTo(283);
        assertThat(car.getYear()).isEqualTo(2021);
    }

    @Test
    @DisplayName("Корректность работы методов сравнения equals и hashCode")
    void equalsAndHashCodeShouldWork() {
        Car car1 = new Car.Builder().model("BMW").power(200).year(2020).build();
        Car car2 = new Car.Builder().model("BMW").power(200).year(2020).build();
        Car car3 = new Car.Builder().model("Audi").power(150).year(2018).build();

        assertThat(car1).isEqualTo(car2);
        assertThat(car1.hashCode()).isEqualTo(car2.hashCode());
        assertThat(car1).isNotEqualTo(car3);
    }

    @Nested
    @DisplayName("Сравнение объектов (Comparator)")
    class ComparatorTests {

        @Test
        @DisplayName("Сортировка по числовому значению мощности")
        void compareByPowerShouldWork() {
            Car weakCar = new Car.Builder().power(100).build();
            Car strongCar = new Car.Builder().power(300).build();

            Comparator<Car> comparator = Car.compareByPower();

            assertThat(comparator.compare(weakCar, strongCar)).isNegative();
            assertThat(comparator.compare(strongCar, weakCar)).isPositive();
        }

        @Test
        @DisplayName("Сортировка по году выпуска")
        void compareByYearShouldWork() {
            Car oldCar = new Car.Builder().year(1990).build();
            Car newCar = new Car.Builder().year(2024).build();

            assertThat(Car.compareByYear().compare(oldCar, newCar)).isNegative();
        }

        @Test
        @DisplayName("Сравнение строковых названий моделей, включая null-значения")
        void compareByModelShouldHandleNulls() {
            Car nullModelCar = new Car.Builder().model(null).build();
            Car aModelCar = new Car.Builder().model("A").build();
            Car bModelCar = new Car.Builder().model("B").build();

            Comparator<Car> comparator = Car.compareByModel();

            assertThat(comparator.compare(nullModelCar, aModelCar)).isNegative();
            assertThat(comparator.compare(aModelCar, nullModelCar)).isPositive();
            assertThat(comparator.compare(aModelCar, bModelCar)).isNegative();
        }

        @Test
        @DisplayName("Последовательная сортировка по всем полям объекта")
        void compareByAllFieldsShouldWorkSequentially() {
            Car car1 = new Car.Builder().model("Lada").year(2010).power(80).build();
            Car car2 = new Car.Builder().model("Lada").year(2020).power(100).build();
            Car car3 = new Car.Builder().model("ZAZ").year(1970).power(30).build();

            Comparator<Car> comparator = Car.compareByAllFields();

            assertThat(comparator.compare(car1, car2)).isNegative();
            assertThat(comparator.compare(car1, car3)).isNegative();
        }
    }

    @Test
    @DisplayName("Формирование строкового представления объекта")
    void toStringShouldContainFieldsInfo() {
        Car car = new Car.Builder().model("Mazda").power(150).year(2015).build();
        String result = car.toString();

        assertThat(result)
                .contains("Mazda")
                .contains("150")
                .contains("2015");
    }
}
