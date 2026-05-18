package com.learning.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.learning.base.BaseTest;

public class FirstTest extends BaseTest {

    @Test
    public void openSauceDemoAndCheckTitle() {
        assertEquals("Swag Labs", driver.getTitle());
    }
}
