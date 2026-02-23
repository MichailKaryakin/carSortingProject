package org.example.strategy;

import org.example.model.Car;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuickSort implements SortingStrategy {

    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || cars.size() < 2) {
            return;
        }
        quickSort(cars, 0, cars.size() - 1, comparator);
    }

    private void quickSort(List<Car> cars, int low, int high, Comparator<Car> comparator) {
        if (low < high) {
            int pi = partition(cars, low, high, comparator);

            quickSort(cars, low, pi - 1, comparator);
            quickSort(cars, pi + 1, high, comparator);
        }
    }

    private int partition(List<Car> cars, int low, int high, Comparator<Car> comparator) {
        int randomIndex = low + (int) (Math.random() * (high - low + 1));
        Collections.swap(cars, randomIndex, high);

        Car pivot = cars.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (comparator.compare(cars.get(j), pivot) <= 0) {
                i++;
                Collections.swap(cars, i, j);
            }
        }

        Collections.swap(cars, i + 1, high);
        return i + 1;
    }
}