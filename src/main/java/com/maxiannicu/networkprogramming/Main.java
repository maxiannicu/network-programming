package com.maxiannicu.networkprogramming;

import com.maxiannicu.networkprogramming.concurrency.DependentRunnable;

public class Main {
    public static void main(String[] args){
        DependentRunnable one = new DependentRunnable("1", 1);
        DependentRunnable two = new DependentRunnable("2", 2);
        DependentRunnable three = new DependentRunnable("3", 2);
        DependentRunnable four = new DependentRunnable("4", 1);
        DependentRunnable five = new DependentRunnable("5", 1);
        DependentRunnable six = new DependentRunnable("6", 1);

        five.addDependency(one);
        five.addDependency(two);
        five.addDependency(three);
        six.addDependency(two);
        six.addDependency(three);
        six.addDependency(four);

        new Thread(one).start();
        new Thread(two).start();
        new Thread(three).start();
        new Thread(four).start();
        new Thread(five).start();
        new Thread(six).start();
    }
}
