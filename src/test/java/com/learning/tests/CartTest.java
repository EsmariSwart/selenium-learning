package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.CartPage;
import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.utils.ConfigReader;

public class CartTest extends BaseTest {

    @Tag("smoke")
    @Test
    public void addItemToCart() {
        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        inventoryPage.addBackpackToCart();

        assertEquals("1", inventoryPage.getCartBadgeCount());

        CartPage cartPage = inventoryPage.openCart();
        assertTrue(cartPage.getItemName().contains("Sauce Labs Backpack"));
    }
}
