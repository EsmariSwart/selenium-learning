package com.learning.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.CheckoutPage;
import com.learning.pages.InventoryPage;
import com.learning.tests.support.TestFlows;
import com.learning.utils.CheckoutData;
import com.learning.utils.TestDataReader;

@DisplayName("Checkout")
class CheckoutTest extends BaseTest {

    @Tag("smoke")
    @Test
    void completeCheckoutSuccessfully() {
        CheckoutData checkoutData = TestDataReader.read("testdata/checkout.json", CheckoutData.class);
        InventoryPage inventoryPage = TestFlows.loginAsStandardUser(driver);

        inventoryPage.addBackpackToCart();
        CheckoutPage checkoutPage = inventoryPage.openCart().proceedToCheckout();

        checkoutPage.enterCheckoutInformation(
                checkoutData.getFirstName(),
                checkoutData.getLastName(),
                checkoutData.getPostalCode());
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        assertEquals("Thank you for your order!", checkoutPage.getConfirmationMessage());
        assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
    }

    @Tag("regression")
    @Test
    void checkoutStepOneRequiresCustomerInformation() {
        InventoryPage inventoryPage = TestFlows.loginAsStandardUser(driver);

        inventoryPage.addBackpackToCart();
        CheckoutPage checkoutPage = inventoryPage.openCart().proceedToCheckout();

        checkoutPage.clickContinueWithoutRequiredFields();

        assertThat(checkoutPage.isErrorMessageDisplayed()).isTrue();
        assertThat(checkoutPage.getErrorMessageText()).containsIgnoringCase("first Name");
        assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
    }
}
