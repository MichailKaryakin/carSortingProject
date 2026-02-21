package org.example.service;

import org.example.model.Car;
import org.example.strategy.SortingStrategy;

import java.util.Comparator;
import java.util.List;

public class SortingService {
    private SortingStrategy strategy;

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeSort(List<Car> data, Comparator<Car> comparator) {
        if (strategy == null) throw new IllegalStateException("Нет выбранной стратегии!");
        strategy.sort(data, comparator);
    }
}
