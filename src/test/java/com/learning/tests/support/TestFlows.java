package com.learning.tests.support;

import org.openqa.selenium.WebDriver;

import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.utils.ConfigReader;

public final class TestFlows {

    private TestFlows() {
    }

    public static InventoryPage loginAsStandardUser(WebDriver driver) {
        return new LoginPage(driver).login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));
    }
}
