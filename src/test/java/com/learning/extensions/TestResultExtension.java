package com.learning.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.learning.base.BaseTest;
import com.learning.reporting.AllureReporter;
import com.learning.utils.ScreenshotUtils;

public class TestResultExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {
        Object testInstance = context.getRequiredTestInstance();
        if (!(testInstance instanceof BaseTest baseTest)) {
            return;
        }

        try {
            if (context.getExecutionException().isPresent() && baseTest.driver != null) {
                ScreenshotUtils.capture(baseTest.driver, context.getDisplayName());
                AllureReporter.attachScreenshot(baseTest.driver, "Failure screenshot");
            }
        } finally {
            if (baseTest.driver != null) {
                baseTest.driver.quit();
                baseTest.driver = null;
            }
        }
    }
}
