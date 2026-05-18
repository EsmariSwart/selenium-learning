package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;
import com.learning.pages.InventoryPage;
import com.learning.pages.LoginPage;
import com.learning.tests.support.TestFlows;

/**
 * Intentionally failing tests for verifying screenshots and Allure attachments.
 * Excluded from normal runs; execute with: mvn test -Pdiagnostic -Pheadless
 */
@Tag("diagnostic")
@DisplayName("Failure demos (screenshot / Allure)")
class FailureDemoTest extends BaseTest {

    @Tag("diagnostic")
    @Test
    void failsWhileStillOnLoginPage() {
        new LoginPage(driver);

        assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Diagnostic: expected to fail on login page for screenshot capture");
    }

    @Tag("diagnostic")
    @Test
    void failsWithWrongCartBadgeAfterAddingItem() {
        InventoryPage inventoryPage = TestFlows.loginAsStandardUser(driver);
        inventoryPage.addBackpackToCart();

        assertEquals("99", inventoryPage.getCartBadgeCount(),
                "Diagnostic: wrong badge count to trigger failure screenshot");
    }

    @Tag("diagnostic")
    @Test
    void failsWithUnexpectedException() {
        TestFlows.loginAsStandardUser(driver);
        throw new AssertionError("Diagnostic: intentional failure for reporting pipeline");
    }
}
