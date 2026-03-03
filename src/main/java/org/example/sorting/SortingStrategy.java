package org.example.sorting;

import org.example.model.Car;

import java.util.Comparator;
import java.util.List;

public interface SortingStrategy {
    void sort(List<Car> cars, Comparator<Car> comparator);
}
