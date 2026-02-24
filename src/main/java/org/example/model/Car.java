package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.Objects;

@SuppressWarnings("ClassCanBeRecord")
public class Car {
    private final int power;
    private final String model;
    private final int year;

    @JsonCreator
    public Car(@JsonProperty("power") int power,
               @JsonProperty("model") String model,
               @JsonProperty("year") int year) {
        this.power = power;
        this.model = model;
        this.year = year;
    }

    // приватный конструктор, принимающий билдер
    private Car(Builder builder) {
        this.power = builder.power;
        this.model = builder.model;
        this.year = builder.year;
    }

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    // три компаратора для сортировки по трём разным полям compareByPower(),
    // compareByModel(), compareByYear() + компаратор compareByAllFields()
    public static Comparator<Car> compareByPower() {
        return (c1, c2) -> Integer.compare(c1.getPower(), c2.getPower());
    }

    public static Comparator<Car> compareByModel() {
        return (c1, c2) -> {
            if (c1.getModel() == null) return (c2.getModel() == null) ? 0 : -1;
            if (c2.getModel() == null) return 1;
            return c1.getModel().compareTo(c2.getModel());
        };
    }

    public static Comparator<Car> compareByYear() {
        return (c1, c2) -> Integer.compare(c1.getYear(), c2.getYear());
    }

    public static Comparator<Car> compareByAllFields() {
        return (c1, c2) -> {
            int modelRes = compareByModel().compare(c1, c2);
            if (modelRes != 0) return modelRes;

            int yearRes = compareByYear().compare(c1, c2);
            if (yearRes != 0) return yearRes;

            return compareByPower().compare(c1, c2);
        };
    }

    // вложенный класс билдер
    public static class Builder {

        private int power;
        private String model;
        private int year;

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return power == car.power &&
                year == car.year &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    @Override
    public String toString() {
        return "Car: " +
                "power = " + power +
                ", model = '" + model +
                ", year = " + year +
                '}';
    }
}
