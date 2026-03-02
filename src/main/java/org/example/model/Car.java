package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.Objects;

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

    public static Comparator<Car> compareByPower() {
        return Comparator.comparingInt(Car::getPower);
    }

    public static Comparator<Car> compareByModel() {
        return (c1, c2) -> {
            if (c1.getModel() == null) return (c2.getModel() == null) ? 0 : -1;
            if (c2.getModel() == null) return 1;
            return c1.getModel().compareTo(c2.getModel());
        };
    }

    public static Comparator<Car> compareByYear() {
        return Comparator.comparingInt(Car::getYear);
    }

    public static Comparator<Car> compareByAllFields() {
        return Comparator.comparing(Car::getModel, Comparator.nullsFirst(String::compareTo))
                .thenComparingInt(Car::getYear)
                .thenComparingInt(Car::getPower);
    }

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
                ", year = " + year;
    }
}
