package com.learning.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {

    private static final Path SCREENSHOT_DIR = Path.of("target", "screenshots");
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private ScreenshotUtils() {
    }

    public static Path capture(WebDriver driver, String testName) {
        if (!(driver instanceof TakesScreenshot takesScreenshot)) {
            return null;
        }

        try {
            Files.createDirectories(SCREENSHOT_DIR);
            String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            Path destination = SCREENSHOT_DIR.resolve(safeName + "_" + timestamp + ".png");

            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            Files.write(destination, screenshot);
            return destination;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot for test: " + testName, e);
        }
    }
}
