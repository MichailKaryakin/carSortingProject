package org.example.util;

import org.example.model.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public final class SearchUtils {

    private SearchUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void countCarsByPowerSequential(List<Car> data, int targetPower) {
        measureAndPrint("Однопоточный поиск", () -> {
            int count = 0;
            for (Car car : data) {
                if (car.getPower() == targetPower) {
                    count++;
                }
            }
            return count;
        });
    }

    public static void countCarsByPowerParallel(List<Car> data, int targetPower) {
        // Определяем количество доступных ядер процессора
        int numThreads = Runtime.getRuntime().availableProcessors();

        measureAndPrint("Многопоточный поиск (" + numThreads + " пот.)", () -> {
            if (data == null || data.isEmpty()) return 0;

            AtomicInteger totalCount = new AtomicInteger(0);
            List<Thread> threads = new ArrayList<>();

            // Рассчитываем размер чанка для каждого потока
            int size = data.size();
            int chunkSize = (int) Math.ceil((double) size / numThreads);

            for (int i = 0; i < numThreads; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, size);

                // Если данных меньше, чем потоков, выходим из цикла
                if (start >= size) break;

                Thread thread = createSearchThread(data, start, end, targetPower, totalCount);
                threads.add(thread);
                thread.start();
            }

            // Ожидаем завершения всех запущенных потоков
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return totalCount.get();
        });
    }

    private static Thread createSearchThread(List<Car> data, int start, int end, int target, AtomicInteger counter) {
        return new Thread(() -> {
            int localCount = 0;
            for (int i = start; i < end; i++) {
                if (data.get(i).getPower() == target) {
                    localCount++;
                }
            }
            counter.addAndGet(localCount);
        });
    }

    private static void measureAndPrint(String label, Supplier<Integer> task) {
        long startTime = System.nanoTime();
        int result = task.get();
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("--- %s ---\n", label);
        System.out.println("Найдено вхождений: " + result);
        System.out.printf("Время выполнения: %.3f мс\n", durationMs);
        System.out.println("---------------------------------------\n");
    }
}