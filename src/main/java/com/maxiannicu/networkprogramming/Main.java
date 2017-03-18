package com.maxiannicu.networkprogramming;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args){
        System.out.printf("Arguments count %d\n",args.length);
        Stream.of(args).forEach(System.out::println);
    }
}
