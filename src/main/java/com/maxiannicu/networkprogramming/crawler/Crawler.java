package com.maxiannicu.networkprogramming.crawler;

import java.util.Set;

/**
 * Created by Nicu Maxian on 3/30/2017.
 */
public interface Crawler {
    Set<String> crawle(String url,int depth);
}
