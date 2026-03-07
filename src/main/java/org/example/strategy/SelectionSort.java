package org.example.strategy;

import org.example.model.Car;
import java.util.Comparator;
import java.util.List;

public class SelectionSort implements SortingStrategy{
    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || cars.size() < 2 || comparator == null) {
            return;
        }

        Comparator<Car> nullSafeComparator = Comparator.nullsLast(comparator);

        for (int i = 0; i < cars.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < cars.size(); j++) {
                if (nullSafeComparator.compare(cars.get(j), cars.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Car minCar = cars.get(minIndex);
                cars.remove(minIndex);
                cars.add(i, minCar);
            }
        }
    }
}
