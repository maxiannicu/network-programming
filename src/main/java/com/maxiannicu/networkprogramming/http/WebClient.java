package com.maxiannicu.networkprogramming.http;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Nicu Maxian on 3/30/2017.
 */
public interface WebClient {
    String get(String url) throws IOException;
}
