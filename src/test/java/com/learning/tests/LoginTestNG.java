package com.learning.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.learning.utils.ScreenshotUtils;

import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.utils.ConfigReader;
import com.learning.utils.DriverFactory;

public class LoginTestNG {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("base.url"));
    }

    @Test
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        assertTrue(new InventoryPage(driver).isLoaded());
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE && driver != null) {
                ScreenshotUtils.capture(driver, result.getName());
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
