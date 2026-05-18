package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        assertTrue(new InventoryPage(driver).isLoaded());
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
