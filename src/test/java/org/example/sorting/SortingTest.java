package org.example.sorting;

import org.example.model.Car;
import org.example.service.SortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SortingTest {

    private List<Car> unsortedCars;
    private final Comparator<Car> yearComparator = Car.compareByYear();

    @BeforeEach
    void setUp() {
        unsortedCars = new ArrayList<>(Arrays.asList(
                new Car.Builder().model("ZAZ").year(1970).build(),
                new Car.Builder().model("Tesla").year(2022).build(),
                new Car.Builder().model("BMW").year(2005).build()
        ));
    }

    static Stream<SortingStrategy> strategyProvider() {
        return Stream.of(
                new BubbleSort(),
                new InsertionSort(),
                new SelectionSort(),
                new QuickSort(),
                new MergeSort()
        );
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    @DisplayName("Проверка корректности упорядочивания списка по году выпуска")
    void allSortingAlgorithmsShouldWork(SortingStrategy strategy) {
        strategy.sort(unsortedCars, yearComparator);

        assertThat(unsortedCars)
                .extracting(Car::getYear)
                .containsExactly(1970, 2005, 2022);
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    @DisplayName("Устойчивость алгоритмов к пустым данным и null")
    void shouldHandleEdgeCases(SortingStrategy strategy) {
        List<Car> emptyList = new ArrayList<>();
        strategy.sort(emptyList, yearComparator);
        assertThat(emptyList).isEmpty();

        strategy.sort(null, yearComparator);
    }

    @Test
    @DisplayName("Защита сервиса от запуска без выбранного алгоритма")
    void serviceShouldThrowExceptionWhenNoStrategy() {
        SortingService service = new SortingService();
        assertThatThrownBy(() -> service.executeSort(unsortedCars, yearComparator))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Нет выбранной стратегии!");
    }

    @Test
    @DisplayName("Проверка делегирования задачи от сервиса к конкретной стратегии")
    void serviceShouldDelegateSortingToStrategy() {
        SortingStrategy mockStrategy = mock(SortingStrategy.class);
        SortingService service = new SortingService();
        service.setStrategy(mockStrategy);

        service.executeSort(unsortedCars, yearComparator);

        verify(mockStrategy, times(1)).sort(unsortedCars, yearComparator);
    }
}
