package com.learning.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public final class ScreenshotUtils {

    private static final Path SCREENSHOT_DIR = Path.of("target", "screenshots");
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private ScreenshotUtils() {
    }

    public static byte[] captureBytes(WebDriver driver) {
        if (!(driver instanceof TakesScreenshot takesScreenshot)) {
            return null;
        }
        return takesScreenshot.getScreenshotAs(OutputType.BYTES);
    }

    public static void attachToAllure(WebDriver driver, String attachmentName) {
        byte[] screenshot = captureBytes(driver);
        if (screenshot == null) {
            return;
        }
        Allure.addAttachment(
                attachmentName,
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png");
    }

    public static Path capture(WebDriver driver, String testName) {
        byte[] screenshot = captureBytes(driver);
        if (screenshot == null) {
            return null;
        }

        try {
            Files.createDirectories(SCREENSHOT_DIR);
            String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            Path destination = SCREENSHOT_DIR.resolve(safeName + "_" + timestamp + ".png");

            Files.write(destination, screenshot);
            attachToAllure(driver, "Failure screenshot");
            return destination;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot for test: " + testName, e);
        }
    }
}
