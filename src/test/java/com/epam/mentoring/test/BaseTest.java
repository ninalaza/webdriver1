package com.epam.mentoring.test;

import com.epam.mentoring.driver.WebDriverInstance;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    WebDriver driver;

    @BeforeClass
    protected void initDriver() {
        driver = WebDriverInstance.getDriverInstance();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(description = "close browser")
    protected void tearDown() {
        WebDriverInstance.closeDriver();
    }
}
