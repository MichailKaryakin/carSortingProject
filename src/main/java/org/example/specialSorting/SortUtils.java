package org.example.specialSorting;

import org.example.model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortUtils {
    static List<Car> createEvenList(List<Car> list, Comparator<Car> comparator) {
        List<Car> evenList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isEven(list, i, comparator)) {
                evenList.add(list.get(i));
            }
        }
        return evenList;
    }

    static boolean isEven(List<Car> list, int index, Comparator<Car> comparator) {
        if (list == null || index < 0 || index >= list.size()) {
            return false;
        }

        Car car = list.get(index);

        if (car == null) {
            return false;
        }

        if (compareComparatorByHorsePower(comparator) && !compareComparatorByYear(comparator)) {
            return getValueOfHorsePower(list, index) % 2 == 0;
        } else if (compareComparatorByYear(comparator) && !compareComparatorByHorsePower(comparator)) {
            return getValueOfYear(list, index) % 2 == 0;
        } else {
            return  false;
        }
    }

    static int getValueOfHorsePower(List<Car> cars,int index) {
        return cars.get(index).getPower();
    }

    static int getValueOfYear(List<Car> cars,int index) {
        return cars.get(index).getYear();
    }

    static void insertSortedList(List<Car> sourceList, List<Car> sortedList, Comparator<Car> comparator) {
        for (int i = 0, j = 0; i < sourceList.size(); i++) {
            if (isEven(sourceList, i, comparator)) {
                sourceList.set(i, sortedList.get(j));
                j++;
            }
        }
    }

    static boolean compareComparatorByHorsePower(Comparator<Car> comparator) {
        Comparator<Car> comparedComparator = Car.compareByPower();
        Car car1 = new Car(2, "1", 0);
        Car car2 = new Car(1, "1", 0);
        boolean isFirstBigger = comparator.compare(car1, car2) == comparedComparator.compare(car1, car2);
        car1 = new Car(2, "1", 0);
        car2 = new Car(1, "1", 0);
        boolean isSecondBigger = comparator.compare(car1, car2) == comparedComparator.compare(car1, car2);
        car1 = new Car(1, "1", 0);
        car2 = new Car(1, "1", 0);
        boolean isEqual = comparator.compare(car1, car2) == comparedComparator.compare(car1, car2);

        return isFirstBigger && isSecondBigger && isEqual;
    }

    static boolean compareComparatorByYear(Comparator<Car> comparator) {
        Comparator<Car> comparedComparator = Car.compareByYear();
        Car car1 = new Car(1, "1", 2);
        Car car2 = new Car(1, "1", 1);
        boolean isFirstBigger = comparator.compare(car1, car2) == comparedComparator.compare(car1, car2);
        car1 = new Car(1, "1", 1);
        car2 = new Car(1, "1", 2);
        boolean isSecondBigger = comparator.compare(car1, car2) == comparedComparator.compare(car1, car2);
        car1 = new Car(1, "1", 1);
        car2 = new Car(1, "1", 1);
        boolean isEqual = comparator.compare(car1, car2) == comparedComparator.compare(car1, car2);

        return isFirstBigger && isSecondBigger && isEqual;
    }
}
