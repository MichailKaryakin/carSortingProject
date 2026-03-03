package org.example.validation;

import org.example.exception.DataValidateException;
import org.example.model.Car;

public class Validator {
    private static final CarValidator CHAIN;

    static {
        CarValidator power = new PowerValidator();
        CarValidator model = new ModelValidator();
        CarValidator year = new YearValidator();

        power.setNext(model);
        model.setNext(year);
        CHAIN = power;
    }

    public void validate(Car car) {
        if (car == null) throw new DataValidateException("Машина не может быть null");
        CHAIN.validate(car);
    }
}
