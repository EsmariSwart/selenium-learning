package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.CheckoutPage;
import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.utils.ConfigReader;

public class CheckoutTest extends BaseTest {

    @Test
    public void completeCheckoutSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        inventoryPage.addBackpackToCart();
        inventoryPage.openCart();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInformation("Es", "Swart", "8001");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        assertEquals("Thank you for your order!", checkoutPage.getConfirmationMessage());
    }
}
