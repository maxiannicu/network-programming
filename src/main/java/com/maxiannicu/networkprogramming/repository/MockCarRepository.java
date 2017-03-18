package com.maxiannicu.networkprogramming.repository;

import com.maxiannicu.networkprogramming.entity.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by nicu on 3/18/17.
 */
public class MockCarRepository implements CarRepository {
    private List<Car> cars = new ArrayList<>();

    public MockCarRepository() {
        cars.add(new Car(0,"BMW","X5"));
        cars.add(new Car(1,"Toyota","Corolla"));
        cars.add(new Car(2,"Toyota","Yaris"));
        cars.add(new Car(3,"Audi","Q5"));
    }

    @Override
    public Optional<Car> get(int id) {
        return cars.stream().filter(car -> car.getId() == id).findFirst();
    }

    @Override
    public List<Car> getAll() {
        return cars;
    }
}
