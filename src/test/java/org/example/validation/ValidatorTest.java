package org.example.validation;

import org.example.exception.DataValidateException;
import org.example.model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {

    private final Validator validator = new Validator();

    @Test
    @DisplayName("Успешная валидация корректно заполненного автомобиля")
    void shouldPassValidCar() {
        Car car = new Car.Builder()
                .model("Toyota")
                .power(150)
                .year(2020)
                .build();

        assertThatCode(() -> validator.validate(car)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Ошибка при передаче пустого объекта (null)")
    void shouldThrowWhenCarIsNull() {
        assertThatThrownBy(() -> validator.validate(null))
                .isInstanceOf(DataValidateException.class)
                .hasMessage("Машина не может быть null");
    }

    @Nested
    @DisplayName("Проверка названия модели")
    class ModelValidationTests {
        @Test
        @DisplayName("Запрет на использование null или пустых строк в названии")
        void shouldThrowWhenModelIsInvalid() {
            Car nullModel = new Car.Builder().model(null).power(100).year(2000).build();
            Car emptyModel = new Car.Builder().model("  ").power(100).year(2000).build();

            assertThatThrownBy(() -> validator.validate(nullModel))
                    .hasMessageContaining("Модель не может быть null");

            assertThatThrownBy(() -> validator.validate(emptyModel))
                    .hasMessageContaining("Модель не может быть пустой строкой");
        }

        @Test
        @DisplayName("Запрет на слишком короткие названия моделей")
        void shouldThrowWhenModelIsTooShort() {
            Car shortModel = new Car.Builder().model("Ab").power(100).year(2000).build();

            assertThatThrownBy(() -> validator.validate(shortModel))
                    .hasMessageContaining("Модель слишком короткая");
        }
    }

    @Nested
    @DisplayName("Проверка мощности двигателя")
    class PowerValidationTests {
        @ParameterizedTest
        @ValueSource(ints = {0, -5, 2500})
        @DisplayName("Запрет на отрицательную, нулевую или избыточную мощность")
        void shouldThrowWhenPowerIsOutOfRange(int invalidPower) {
            Car car = new Car.Builder().model("Mazda").power(invalidPower).year(2000).build();

            assertThatThrownBy(() -> validator.validate(car))
                    .isInstanceOf(DataValidateException.class);
        }
    }

    @Nested
    @DisplayName("Проверка года выпуска")
    class YearValidationTests {
        @Test
        @DisplayName("Запрет на регистрацию слишком старых автомобилей")
        void shouldThrowWhenYearIsTooOld() {
            Car oldCar = new Car.Builder().model("Ford").power(100).year(1899).build();

            assertThatThrownBy(() -> validator.validate(oldCar))
                    .hasMessageContaining("Год выпуска слишком старый");
        }

        @Test
        @DisplayName("Запрет на указание года выпуска из будущего")
        void shouldThrowWhenYearIsFromFuture() {
            int futureYear = Year.now().getValue() + 5;
            Car futureCar = new Car.Builder().model("Tesla").power(100).year(futureYear).build();

            assertThatThrownBy(() -> validator.validate(futureCar))
                    .hasMessageContaining("Год выпуска из будущего");
        }
    }
}
