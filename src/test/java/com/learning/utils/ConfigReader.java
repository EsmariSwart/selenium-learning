package com.learning.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        loadPropertiesFile("config.properties");
        String env = System.getProperty("env", "dev");
        loadPropertiesFile("config-" + env + ".properties");
    }

    private static void loadPropertiesFile(String fileName) {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                if ("config.properties".equals(fileName)) {
                    throw new RuntimeException(fileName + " not found");
                }
                throw new RuntimeException(fileName + " not found for env=" + System.getProperty("env", "dev"));
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
}
