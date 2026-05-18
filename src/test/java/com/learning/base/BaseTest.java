package com.learning.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import com.learning.extensions.TestResultExtension;
import com.learning.utils.ConfigReader;
import com.learning.utils.DriverFactory;

@ExtendWith(TestResultExtension.class)
public class BaseTest {

    public WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("base.url"));
    }
}
