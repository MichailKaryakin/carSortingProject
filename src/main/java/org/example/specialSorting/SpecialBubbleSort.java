package org.example.specialSorting;

import org.example.model.Car;
import org.example.sorting.BubbleSort;
import java.util.Comparator;
import java.util.List;

import static org.example.specialSorting.SortUtils.createEvenList;
import static org.example.specialSorting.SortUtils.insertSortedList;

public class SpecialBubbleSort extends BubbleSort {
    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {
        List<Car> evenList = createEvenList(cars, comparator);
        super.sort(evenList, comparator);
        insertSortedList(cars, evenList, comparator);
    }
}
