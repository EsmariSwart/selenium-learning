package com.learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By backpackAddToCartButton = By.id("add-to-cart-sauce-labs-backpack");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage waitUntilLoaded() {
        wait.waitForUrlContains("inventory");
        wait.waitForVisibility(pageTitle);
        return this;
    }

    public boolean isLoaded() {
        try {
            wait.waitForVisibility(pageTitle);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void addBackpackToCart() {
        click(backpackAddToCartButton);
        wait.waitForVisibility(cartBadge);
    }

    public String getCartBadgeCount() {
        return getText(cartBadge);
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver).waitUntilLoaded();
    }
}
