package com.maxiannicu.networkprogramming.configuration;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by nicu on 5/9/17.
 */
public class JsonConfiguration implements Configuration {
    private final JSONObject configuration;

    public JsonConfiguration(){
        try {
            URL resource = getClass().getClassLoader().getResource("config.json");
            configuration = new JSONObject(getFileContent(resource.toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Cannot read configuration file",e);
        }
    }

    @Override
    public String get(String key) {
        return configuration.getString(key);
    }

    private String getFileContent(URI uri){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(uri));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()){
                stringBuilder.append(scanner.next());
            }

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            if (scanner != null){
                scanner.close();
                throw new RuntimeException("Configuration file not found",e);
            }
        }

        return null;
    }
}
