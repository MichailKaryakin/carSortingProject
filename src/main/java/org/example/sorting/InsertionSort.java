package org.example.sorting;

import org.example.model.Car;

import java.util.Comparator;
import java.util.List;

public class InsertionSort implements SortingStrategy {
    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || cars.size() < 2) {
            return;
        }

        int n = cars.size();
        for (int i = 1; i < n; i++) {
            Car key = cars.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(cars.get(j), key) > 0) {
                cars.set(j + 1, cars.get(j));
                j = j - 1;
            }
            cars.set(j + 1, key);
        }
    }
}
