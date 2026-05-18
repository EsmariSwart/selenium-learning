package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.CartPage;
import com.learning.pages.InventoryPage;
import com.learning.tests.support.TestFlows;

@DisplayName("Shopping cart")
class CartTest extends BaseTest {

    @Tag("smoke")
    @Test
    void addItemToCart() {
        InventoryPage inventoryPage = TestFlows.loginAsStandardUser(driver);

        inventoryPage.addBackpackToCart();

        assertEquals("1", inventoryPage.getCartBadgeCount());

        CartPage cartPage = inventoryPage.openCart();
        assertTrue(cartPage.getItemName().contains("Sauce Labs Backpack"));
    }

    @Tag("regression")
    @Test
    void cartBadgeShowsTwoWhenAddingMultipleItems() {
        InventoryPage inventoryPage = TestFlows.loginAsStandardUser(driver);

        inventoryPage.addBackpackToCart();
        inventoryPage.addBikeLightToCart();

        assertEquals("2", inventoryPage.getCartBadgeCount());

        CartPage cartPage = inventoryPage.openCart();
        assertEquals(2, cartPage.getItemCount());
    }

    @Tag("regression")
    @Test
    void removeItemFromCart() {
        InventoryPage inventoryPage = TestFlows.loginAsStandardUser(driver);

        inventoryPage.addBackpackToCart();
        CartPage cartPage = inventoryPage.openCart();
        cartPage.removeBackpack();

        assertEquals(0, cartPage.getItemCount());
        assertTrue(cartPage.isEmpty());
    }
}
