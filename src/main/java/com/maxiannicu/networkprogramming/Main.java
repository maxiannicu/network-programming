package com.maxiannicu.networkprogramming;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.maxiannicu.networkprogramming.crawler.Crawler;
import com.maxiannicu.networkprogramming.di.CrawlerModule;

import java.util.Scanner;
import java.util.Set;

public class Main {
    private final Crawler crawler;

    @Inject
    public Main(Crawler crawler) {
        this.crawler = crawler;
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter url to scan : ");
        String url = scanner.next();
        System.out.print("Please enter depth to scan : ");
        int depth = scanner.nextInt();
        Set<String> paths = crawler.crawle(url, depth);
        System.out.println("Here are all paths:");
        for (String path : paths){
            System.out.println(path);
        }
        System.out.printf("Total found %d links",paths.size());
    }

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new CrawlerModule());
        Main app = injector.getInstance(Main.class);
        app.run();
    }
}
