package com.myown.ownProject.core.config;

import java.io.FileInputStream;
import java.util.Properties;

public class configReader {

    private static Properties prop;

    static {
        try {
            prop = new Properties();
            FileInputStream fis =
                    new FileInputStream("src/main/resources/config.properties");
            prop.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file");
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

}
