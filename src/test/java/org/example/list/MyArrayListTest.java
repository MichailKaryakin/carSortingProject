package org.example.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MyArrayListTest {

    private MyArrayList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>(2);
    }

    @Nested
    @DisplayName("Базовые операции списка")
    class BasicOperations {
        @Test
        @DisplayName("Добавление элементов и автоматическое расширение массива")
        void shouldAddElementsAndGrow() {
            list.add("A");
            list.add("B");
            list.add("C");

            assertThat(list).hasSize(3).containsExactly("A", "B", "C");
        }

        @Test
        @DisplayName("Удаление элемента по индексу со смещением остальных")
        void shouldRemoveByIndex() {
            list.addAll(List.of("A", "B", "C"));
            String removed = list.remove(1);

            assertThat(removed).isEqualTo("B");
            assertThat(list).containsExactly("A", "C");
        }

        @Test
        @DisplayName("Выброс исключения при выходе за границы списка")
        void shouldThrowExceptionOnInvalidIndex() {
            list.add("A");
            assertThatThrownBy(() -> list.get(5))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    @Nested
    @DisplayName("Работа с очередью и манипуляции")
    class CustomMethods {
        @Test
        @DisplayName("Работа методов вставки и извлечения с обеих сторон")
        void pushPopMethodsShouldWork() {
            list.pushBack("Middle");
            list.pushFront("Front");
            list.pushBack("Back");

            assertThat(list).containsExactly("Front", "Middle", "Back");
            assertThat(list.popFront()).isEqualTo("Front");
            assertThat(list.popBack()).isEqualTo("Back");
            assertThat(list).containsExactly("Middle");
        }

        @Test
        @DisplayName("Разворот порядка элементов в списке")
        void reverseShouldWork() {
            list.addAll(List.of("1", "2", "3"));
            list.reverse();
            assertThat(list).containsExactly("3", "2", "1");
        }

        @Test
        @DisplayName("Перемешивание элементов без потери данных")
        void shuffleShouldKeepAllElements() {
            List<String> data = List.of("A", "B", "C", "D", "E");
            list.addAll(data);
            list.shuffle();

            assertThat(list).hasSize(5).containsAll(data);
        }
    }

    @Nested
    @DisplayName("Интеграция со Stream API")
    class StreamCollectorTests {
        @Test
        @DisplayName("Сборка элементов стрима в кастомный MyArrayList")
        void collectorShouldWork() {
            MyArrayList<Integer> result = Stream.of(1, 2, 3, 4, 5)
                    .collect(MyArrayList.toMyArrayList());

            assertThat(result)
                    .isInstanceOf(MyArrayList.class)
                    .containsExactly(1, 2, 3, 4, 5);
        }
    }

    @Test
    @DisplayName("Ошибка при изменении списка во время итерации")
    void shouldThrowCME() {
        list.addAll(List.of("A", "B", "C"));
        Iterator<String> it = list.iterator();

        list.add("D");

        assertThatThrownBy(it::next)
                .isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    @DisplayName("Оптимизация размера внутреннего массива под количество элементов")
    void trimToSizeShouldWork() {
        list = new MyArrayList<>(100);
        list.add("Only one");
        list.trimToSize();

        assertThat(list).hasSize(1).containsExactly("Only one");
    }
}
