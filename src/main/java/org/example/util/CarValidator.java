package org.example.util;

import org.example.model.Car;

public interface CarValidator {
    void validate(Car car);
    void setNext(CarValidator next);
}