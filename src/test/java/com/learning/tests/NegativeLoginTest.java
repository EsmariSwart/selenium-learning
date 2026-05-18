package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.LoginPage;

public class NegativeLoginTest extends BaseTest {

    @Tag("regression")
    @Test
    public void invalidLoginShowsErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.attemptLogin("wrong_user", "wrong_password");

        assertTrue(loginPage.isErrorMessageDisplayed());
    }
}
