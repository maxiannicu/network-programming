package com.maxiannicu.networkprogramming.repository;

import com.maxiannicu.networkprogramming.entity.Car;

import java.util.List;
import java.util.Optional;

/**
 * Created by nicu on 3/18/17.
 */
public interface CarRepository {
    Optional<Car> get(int id);
    List<Car> getAll();
}
