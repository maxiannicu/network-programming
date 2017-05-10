package com.maxiannicu.networkprogramming.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * Created by nicu on 3/18/17.
 */
public class RepositoryModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(CarRepository.class).to(MockCarRepository.class).in(Scopes.SINGLETON);
    }
}
