package org.example.input;

import org.example.exception.DataReadException;
import org.example.model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataReaderTest {

    @Nested
    @DisplayName("Генерация случайных данных")
    class RandomReaderTest {
        @Test
        @DisplayName("Проверка создания заданного количества объектов")
        void shouldGenerateCorrectCount() {
            int expectedCount = 5;
            RandomDataReader reader = new RandomDataReader(expectedCount);

            List<Car> result = reader.read();

            assertThat(result).hasSize(expectedCount);
            assertThat(result.getFirst().getModel()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("Чтение данных из JSON")
    class JsonReaderTest {
        @TempDir
        Path tempDir;

        @Test
        @DisplayName("Успешное чтение списка машин из файла")
        void shouldReadFromJsonFile() throws IOException {
            File tempFile = tempDir.resolve("cars.json").toFile();
            String json = "[{\"model\":\"Audi\",\"power\":200,\"year\":2020}]";
            Files.writeString(tempFile.toPath(), json);

            JsonFileDataReader reader = new JsonFileDataReader(tempFile.getAbsolutePath());
            List<Car> result = reader.read();

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getModel()).isEqualTo("Audi");
        }

        @Test
        @DisplayName("Ошибка при попытке прочитать несуществующий файл")
        void shouldThrowExceptionWhenFileNotFound() {
            JsonFileDataReader reader = new JsonFileDataReader("non_existent.json");

            assertThatThrownBy(reader::read)
                    .isInstanceOf(DataReadException.class)
                    .hasMessageContaining("Ошибка при обработке файла");
        }
    }

    @Nested
    @DisplayName("Ручной ввод через консоль")
    class ConsoleReaderTest {
        @Test
        @DisplayName("Корректная обработка последовательного ввода полей")
        void shouldReadFromConsole() {
            String simulatedInput = "1\nTesla\n500\n2022\n";
            Scanner testScanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

            ConsoleDataReader reader = new ConsoleDataReader(testScanner);
            List<Car> result = reader.read();

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getModel()).isEqualTo("Tesla");
            assertThat(result.getFirst().getPower()).isEqualTo(500);
        }
    }
}
