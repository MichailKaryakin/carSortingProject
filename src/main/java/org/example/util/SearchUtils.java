package org.example.util;

import org.example.model.Car;
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
        measureAndPrint("Многопоточный поиск (2 потока)", () -> {
            AtomicInteger totalCount = new AtomicInteger(0);
            int mid = data.size() / 2;

            Thread t1 = createSearchThread(data, 0, mid, targetPower, totalCount);
            Thread t2 = createSearchThread(data, mid, data.size(), targetPower, totalCount);

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return 0;
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

    /**
     * Обобщенный метод замера времени (устраняет дублирование вывода).
     */
    private static void measureAndPrint(String label, Supplier<Integer> task) {
        if (task == null) return;

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
