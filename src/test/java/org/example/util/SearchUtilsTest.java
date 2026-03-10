package org.example.util;

import org.example.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchUtilsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("Результаты последовательного и многопоточного поиска должны совпадать")
    void shouldReturnSameResultsForBothMethods() {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int power = (i % 10 == 0) ? 500 : 100;
            cars.add(new Car.Builder().model("Model " + i).power(power).year(2020).build());
        }

        SearchUtils.countCarsByPowerSequential(cars, 500);
        SearchUtils.countCarsByPowerParallel(cars, 500);

        String output = outContent.toString();

        assertThat(output).contains("Однопоточный поиск")
                .contains("Многопоточный поиск")
                .contains("Найдено вхождений: 10");

        int countOccurrences = output.split("Найдено вхождений: 10").length - 1;
        assertThat(countOccurrences).isEqualTo(2);
    }

    @Test
    @DisplayName("Корректная работа при пустом списке данных")
    void shouldHandleEmptyList() {
        List<Car> emptyList = new ArrayList<>();

        SearchUtils.countCarsByPowerParallel(emptyList, 500);

        assertThat(outContent.toString()).contains("Найдено вхождений: 0");
    }

    @Test
    @DisplayName("Корректная работа, когда данных меньше, чем ядер процессора")
    void shouldWorkWithSmallDataAmount() {
        List<Car> smallList = List.of(
                new Car.Builder().model("A").power(300).build(),
                new Car.Builder().model("B").power(300).build()
        );

        SearchUtils.countCarsByPowerParallel(smallList, 300);

        assertThat(outContent.toString()).contains("Найдено вхождений: 2");
    }
}
