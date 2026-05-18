package com.learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By itemName = By.className("inventory_item_name");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitUntilLoaded() {
        wait.waitForUrlContains("cart");
        wait.waitForVisibility(pageTitle);
        return this;
    }

    public String getItemName() {
        return getText(itemName);
    }

    public CheckoutPage proceedToCheckout() {
        wait.waitForVisibility(itemName);
        click(checkoutButton);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.waitForCheckoutStepOne();
        return checkoutPage;
    }
}
