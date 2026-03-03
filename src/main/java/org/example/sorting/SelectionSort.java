package org.example.sorting;

import org.example.model.Car;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectionSort implements SortingStrategy {
    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || cars.size() < 2) {
            return;
        }

        int n = cars.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(cars.get(j), cars.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Collections.swap(cars, i, minIdx);
            }
        }
    }
}
