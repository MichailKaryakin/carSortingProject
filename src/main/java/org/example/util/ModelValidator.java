package org.example.util;

import org.example.exception.DataValidateException;
import org.example.model.Car;

public class ModelValidator extends AbstractCarValidator {

    @Override
    protected void check(Car car) {
        String model = car.getModel();

        if (model == null) {
            throw new DataValidateException("Модель не может быть null");
        }

        String trimmed = model.trim();

        if (model.isBlank()) {
            throw new DataValidateException("Модель не может быть пустой строкой");
        }

        if (trimmed.length() < 3) {
            throw new DataValidateException(
                    "Модель слишком короткая (минимум 3 символа, получено: '" + model + "')"
            );
        }
    }
}
