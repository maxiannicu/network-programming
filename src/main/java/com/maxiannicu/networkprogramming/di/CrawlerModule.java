package com.maxiannicu.networkprogramming.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.maxiannicu.networkprogramming.crawler.Crawler;
import com.maxiannicu.networkprogramming.crawler.CrawlerImpl;
import com.maxiannicu.networkprogramming.http.WebClient;
import com.maxiannicu.networkprogramming.http.WebClientImpl;

/**
 * Created by Nicu Maxian on 3/30/2017.
 */
public class CrawlerModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(Crawler.class).to(CrawlerImpl.class);
        binder.bind(WebClient.class).to(WebClientImpl.class);
    }
}
