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

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void clickCheckout() {
        click(checkoutButton);
        wait.waitForVisibility(checkoutInfoTitle);
    }

    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void clickFinish() {
        click(finishButton);
        wait.waitForVisibility(confirmationHeader);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }
}
