package org.example.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class MenuControllerTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void restore() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    @DisplayName("Завершение работы приложения при выборе пункта выхода")
    void shouldExitWhenZeroPressed() {
        provideInput("0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString()).contains("Выход");
    }

    @Test
    @DisplayName("Обработка ввода несуществующего пункта меню")
    void shouldShowErrorOnInvalidChoice() {
        provideInput("99\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString()).contains("Нет такого пункта");
    }

    @Test
    @DisplayName("Запрет поиска при отсутствии загруженных данных")
    void shouldWarnWhenSearchingEmptyList() {
        provideInput("4\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString()).contains("Список пуст! Сначала загрузите данные.");
    }

    @Test
    @DisplayName("Успешная генерация случайного набора данных через меню")
    void shouldLoadRandomDataSuccessfully() {
        provideInput("1\n2\n5\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString())
                .contains("Данные успешно загружены!")
                .contains("Элементов: 5");
    }

    @Test
    @DisplayName("Проверка наличия данных перед запуском алгоритмов сортировки")
    void shouldWarnWhenSortingEmptyList() {
        provideInput("2\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString()).contains("Нечего сортировать! Добавьте данные.");
    }

    @Test
    @DisplayName("Запрет на использование строковых полей в особых алгоритмах")
    void shouldPreventSpecialSortOnStringFields() {
        provideInput("1\n2\n5\n2\n6\n1\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString())
                .contains("Ошибка: Особые алгоритмы работают только с числовыми полями");
    }

    @Test
    @DisplayName("Устойчивость к вводу строки вместо числа в главном меню")
    void shouldHandleStringInputInReadInt() {
        provideInput("1\n2\nabc\n5\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString())
                .contains("Введите целое число:")
                .contains("Данные успешно загружены! Элементов: 5");
    }

    @Test
    @DisplayName("Проверка процесса поиска по мощности и запуск сравнения потоков")
    void shouldExecuteSearchComparison() {
        provideInput("1\n2\n5\n4\n200\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString())
                .contains("Запуск сравнения производительности...")
                .contains("--- Однопоточный поиск ---")
                .contains("--- Многопоточный поиск")
                .contains("Найдено вхождений:");
    }

    @Test
    @DisplayName("Успешное выполнение сортировки и отказ от сохранения в файл")
    void shouldSortSuccessfullyWithoutSaving() {
        provideInput("1\n2\n2\n2\n4\n2\nn\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString())
                .contains("Сортировка выполнена успешно!")
                .contains("Записать результат сортировки в отдельный файл?");
    }

    @Test
    @DisplayName("Обработка ситуации, когда выбран неверный алгоритм или поле")
    void shouldHandleInvalidAlgorithmChoice() {
        provideInput("1\n2\n3\n2\n99\n0\n");

        MenuController controller = new MenuController();
        controller.run();

        assertThat(testOut.toString()).contains("Неверный алгоритм.");
    }
}
