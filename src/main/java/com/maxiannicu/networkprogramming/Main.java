package com.maxiannicu.networkprogramming;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.maxiannicu.networkprogramming.di.RepositoryModule;
import com.maxiannicu.networkprogramming.repository.CarRepository;

import java.util.stream.Stream;

public class Main {
    private CarRepository carRepository;

    @Inject
    public Main(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void showCars(){
        this.carRepository.getAll().forEach(System.out::println);
    }

    public static void main(String[] args){
        System.out.printf("Arguments count %d\n",args.length);
        Stream.of(args).forEach(System.out::println);

        Injector injector = Guice.createInjector(new RepositoryModule());
        Main instance = injector.getInstance(Main.class);
        instance.showCars();
    }
}
