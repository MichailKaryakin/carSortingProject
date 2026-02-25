package org.example.strategy;

import org.example.model.Car;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BubbleSort implements SortingStrategy {
    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || cars.size() < 2) {
            return;
        }

        int n = cars.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(cars.get(j), cars.get(j + 1)) > 0) {
                    Collections.swap(cars, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
