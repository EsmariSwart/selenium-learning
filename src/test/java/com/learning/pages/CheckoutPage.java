package com.learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By checkoutButton = By.id("checkout");
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By confirmationHeader = By.className("complete-header");
    private final By checkoutInfoTitle = By.cssSelector("[data-test='title']");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void clickCheckout() {
        click(checkoutButton);
        waitForCheckoutStepOne();
    }

    public void waitForCheckoutStepOne() {
        wait.waitForUrlContains("checkout-step-one");
        wait.waitForVisibility(checkoutInfoTitle);
    }

    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
        wait.waitForUrlContains("checkout-step-two");
        wait.waitForClickability(finishButton);
    }

    public void clickContinueWithoutRequiredFields() {
        click(continueButton);
        wait.waitForVisibility(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return wait.waitForVisibility(errorMessage).isDisplayed();
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    public void clickFinish() {
        click(finishButton);
        wait.waitForUrlContains("checkout-complete");
        wait.waitForVisibility(confirmationHeader);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }
}
