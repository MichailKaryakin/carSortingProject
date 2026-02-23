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
    // compareByModel(), compareByYear()
    public static Comparator<Car> compareByPower() {
        return Comparator.comparingInt(Car::getPower);
    }

    public static Comparator<Car> compareByModel() {
        return Comparator.comparing(Car::getModel);
    }

    public static Comparator<Car> compareByYear() {
        return Comparator.comparingInt(Car::getYear);
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
