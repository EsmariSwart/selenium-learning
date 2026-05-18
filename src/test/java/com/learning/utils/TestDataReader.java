package com.learning.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public final class TestDataReader {

    private static final Gson GSON = new Gson();

    private TestDataReader() {
    }

    public static <T> T read(String resourcePath, Class<T> type) {
        try (InputStream input = TestDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new RuntimeException("Test data not found: " + resourcePath);
            }
            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                return GSON.fromJson(reader, type);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data: " + resourcePath, e);
        }
    }
}
