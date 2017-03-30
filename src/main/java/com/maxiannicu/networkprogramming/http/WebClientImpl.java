package com.maxiannicu.networkprogramming.http;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by Nicu Maxian on 3/30/2017.
 */
public class WebClientImpl implements WebClient {
    @Override
    public String get(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)u.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);

        String content = getBody(connection);
        connection.disconnect();

        return content;
    }

    private String getBody(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String content = getContent(bufferedReader);
        bufferedReader.close();
        return content;
    }

    private String getContent(BufferedReader rd) throws IOException {
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }
}
