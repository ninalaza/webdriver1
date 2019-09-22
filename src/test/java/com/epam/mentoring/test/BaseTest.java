package com.epam.mentoring.test;

import com.epam.mentoring.driver.WebDriverInstance;
import com.epam.mentoring.testdata.TestData;
import com.epam.mentoring.testdata.bean.User;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    WebDriver driver;
    User testUser;

    @BeforeClass
    protected void initTest() {
        driver = WebDriverInstance.getDriverInstance();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        testUser = new User.UserBuilder(TestData.LOGIN_NAME, TestData.PASSWORD, TestData.EMAIL, TestData.RECEIVER_EMAIL).build();
    }

    @AfterClass(description = "close browser")
    protected void tearDown() {
        WebDriverInstance.closeDriver();
    }
}
