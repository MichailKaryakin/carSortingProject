package org.example.util;

import org.example.model.Car;

public abstract class AbstractCarValidator implements CarValidator {

    private CarValidator next;

    @Override
    public void setNext(CarValidator next) {
        this.next = next;
    }

    @Override
    public void validate(Car car) {
        check(car);
        if (next != null) {
            next.validate(car);
        }
    }

    protected abstract void check(Car car);
}