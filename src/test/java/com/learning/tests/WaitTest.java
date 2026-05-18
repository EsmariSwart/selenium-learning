package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.LoginPage;
import com.learning.utils.ConfigReader;

public class WaitTest extends BaseTest {

    @Test
    public void loginWithExplicitWait() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
