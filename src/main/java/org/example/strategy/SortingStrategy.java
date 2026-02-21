package org.example.strategy;

import org.example.model.Car;

import java.util.Comparator;
import java.util.List;

// написать для этого интерфейса несколько реализаций сортировки
public interface SortingStrategy {
    void sort(List<Car> cars, Comparator<Car> comparator);
}
