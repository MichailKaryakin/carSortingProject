package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    // здесь приватный конструктор, принимающий билдер

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    // три компаратора для сортировки по трём разным полям compareByPower(), compareByModel(), compareByYear()

    // вложенный класс билдер

    // equals, hashCode, toString
}
