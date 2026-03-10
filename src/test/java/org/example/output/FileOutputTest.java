package org.example.output;

import org.example.model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileOutputTest {

    @Test
    @DisplayName("Создание файла с таймстемпом и проверка содержимого")
    void shouldCreateFileWithTimestamp(@TempDir Path tempDir) throws IOException {
        List<Car> cars = List.of(
                new Car.Builder().model("Lada").power(80).year(2010).build(),
                new Car.Builder().model("Tesla").power(600).year(2023).build()
        );

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = "sort_result_" + timestamp + ".jsonl";
        Path filePath = tempDir.resolve(fileName);

        FileOutput.appendCarsToFile(cars, filePath.toString());

        assertThat(Files.exists(filePath)).isTrue();

        List<String> lines = Files.readAllLines(filePath);
        assertThat(lines).hasSize(2);
        assertThat(lines.get(0)).contains("Lada").contains("80");
        assertThat(lines.get(1)).contains("Tesla").contains("600");
    }
}
