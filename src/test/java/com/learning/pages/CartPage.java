package com.learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By itemName = By.className("inventory_item_name");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitUntilLoaded() {
        wait.waitForVisibility(pageTitle);
        return this;
    }

    public String getItemName() {
        return getText(itemName);
    }
}
