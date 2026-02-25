package org.example.util;

import org.example.model.Car;

public class ModelValidator extends AbstractCarValidator {

    @Override
    protected void check(Car car) {
        String model = car.getModel();

        if (model == null) {
            throw new DataImportException("Модель не может быть null");
        }

        String trimmed = model.trim();

        if (trimmed.isEmpty()) {
            throw new DataImportException("Модель не может быть пустой строкой");
        }

        if (trimmed.length() < 3) {
            throw new DataImportException(
                    "Модель слишком короткая (минимум 3 символа, получено: '" + model + "')"
            );
        }
    }
}