package com.learning.reporting;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.WebDriver;

import com.learning.utils.ScreenshotUtils;

import io.qameta.allure.Allure;

/**
 * Bridges framework capabilities to Allure reporting.
 *
 * Lives in src/test because Allure is a test-scoped dependency. Keeping this
 * out of src/main lets the framework stay free of a hard dependency on any
 * specific reporting tool.
 */
public final class AllureReporter {

    private AllureReporter() {
    }

    public static void attachScreenshot(WebDriver driver, String attachmentName) {
        byte[] screenshot = ScreenshotUtils.captureBytes(driver);
        if (screenshot == null) {
            return;
        }
        Allure.addAttachment(
                attachmentName,
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png");
    }
}
