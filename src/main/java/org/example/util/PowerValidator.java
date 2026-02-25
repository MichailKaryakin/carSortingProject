package org.example.util;

import org.example.model.Car;

public class PowerValidator extends AbstractCarValidator {

    @Override
    protected void check(Car car) {
        int power = car.getPower();

        if (power <= 0) {
            throw new DataImportException(
                    "Мощность должна быть больше 0 (получено: " + power + ")"
            );
        }

        if (power > 2000) {
            throw new DataImportException(
                    "Мощность слишком большая (максимум 2000 л.с., получено: " + power + ")"
            );
        }
    }
}