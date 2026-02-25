package org.example.util;

import org.example.model.Car;

public class Validator {

    private final CarValidator chain;

    public Validator() {
        CarValidator powerValidator = new PowerValidator();
        CarValidator modelValidator = new ModelValidator();
        CarValidator yearValidator = new YearValidator();

        powerValidator.setNext(modelValidator);
        modelValidator.setNext(yearValidator);

        this.chain = powerValidator;
    }

    public void validate(Car car) {
        if (car == null) {
            throw new DataImportException("Машина не может быть null");
        }

        chain.validate(car);
    }
}