package org.example.util;

import org.example.model.Car;

import java.time.Year;

public class YearValidator extends AbstractCarValidator {

    @Override
    protected void check(Car car) {
        int year = car.getYear();
        int currentYear = Year.now().getValue();

        if (year < 1900) {
            throw new DataImportException(
                    "Год выпуска слишком старый (минимум 1900, получено: " + year + ")"
            );
        }

        if (year > currentYear + 1) {
            throw new DataImportException(
                    "Год выпуска из будущего (получено: " + year + ")"
            );
        }
    }
}