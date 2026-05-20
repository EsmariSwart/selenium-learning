package com.learning.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By itemName = By.className("inventory_item_name");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");
    private final By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private final By cartItems = By.className("cart_item");

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

    public int getItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeBackpack() {
        click(removeBackpackButton);
        wait.waitForInvisibility(itemName);
    }

    public CheckoutPage proceedToCheckout() {
        wait.waitForVisibility(itemName);
        click(checkoutButton);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.waitForCheckoutStepOne();
        return checkoutPage;
    }
}
