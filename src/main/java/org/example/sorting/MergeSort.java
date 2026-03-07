package org.example.sorting;

import org.example.model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort implements  SortingStrategy {
    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || cars.size() < 2 || comparator == null) {
            return;
        }

        Comparator<Car> nullSafeComparator = Comparator.nullsLast(comparator);

        List<Car> buffer = new ArrayList<>(cars);

        sortList(cars, buffer, 0, cars.size(), nullSafeComparator);

    }

    private static void sortList(List<Car> cars,List<Car> buffer, int left, int right, Comparator<Car> comparator) {
        if (right - left > 1) {
            int mid = left + (right - left) / 2;

            sortList(cars, buffer, left, mid, comparator);
            sortList(cars, buffer, mid, right, comparator);


            mergeCarList(cars, buffer, left, mid, right, comparator);
        }
    }

    private static void mergeCarList(List<Car> cars, List<Car> buffer, int left, int mid, int right, Comparator<Car> comparator) {

        for (int i = left; i < right; i++) {
            buffer.set(i, cars.get(i));
        }

        int i = left;
        int j = mid;
        int k = left;

        while (i < mid && j < right) {
            if (comparator.compare(buffer.get(i), buffer.get(j)) <= 0) {
                cars.set(k, buffer.get(i));
                i++;
            } else {
                cars.set(k, buffer.get(j));
                j++;
            }
            k++;
        }

        while (i < mid) {
            cars.set(k++, buffer.get(i++));
        }
    }
}
