package com.learning.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.learning.base.BaseTest;
import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.tests.support.TestFlows;
import com.learning.utils.ConfigReader;

@DisplayName("Login")
class LoginTest extends BaseTest {

    @Tag("smoke")
    @Test
    void successfulLogin() {
        TestFlows.loginAsStandardUser(driver);

        assertTrue(new InventoryPage(driver).isLoaded());
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Tag("regression")
    @Test
    void loginPageIsDisplayedBeforeAuthentication() {
        LoginPage loginPage = new LoginPage(driver);

        assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
        loginPage.enterUsername("standard_user");
        assertTrue(driver.findElement(org.openqa.selenium.By.id("user-name")).isDisplayed());
    }

    @Tag("regression")
    @Test
    void emptyCredentialsShowErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.attemptLogin("", "");

        assertThat(loginPage.isErrorMessageDisplayed()).isTrue();
        assertThat(loginPage.getErrorMessageText()).containsIgnoringCase("required");
    }

    @Tag("regression")
    @ParameterizedTest
    @CsvSource({
            "wrong_user, wrong_pass",
            "locked_out_user, secret_sauce"
    })
    void invalidLogins(String user, String pass) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.attemptLogin(user, pass);

        assertThat(loginPage.isErrorMessageDisplayed()).isTrue();

        if ("locked_out_user".equals(user)) {
            assertThat(loginPage.getErrorMessageText()).containsIgnoringCase("locked out");
        }
    }

    @Tag("regression")
    @Test
    void explicitWaitsReachInventoryAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        assertTrue(driver.getCurrentUrl().contains("inventory"));
        assertTrue(new InventoryPage(driver).isLoaded());
    }
}
