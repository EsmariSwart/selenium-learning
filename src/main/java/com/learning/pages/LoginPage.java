package com.learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void attemptLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public InventoryPage login(String username, String password) {
        attemptLogin(username, password);
        return new InventoryPage(driver).waitUntilLoaded();
    }

    public boolean isErrorMessageDisplayed() {
        return wait.waitForVisibility(errorMessage).isDisplayed();
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}
