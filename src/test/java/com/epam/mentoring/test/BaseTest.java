package com.epam.mentoring.test;

import com.epam.mentoring.driver.WebDriverInstance;
import com.epam.mentoring.testdata.TestData;
import com.epam.mentoring.testdata.bean.User;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    WebDriver driver;
    User testUser;
    Logger LOGGER = LogManager.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        PropertyConfigurator.configure(Paths.get(System.getProperty("user.dir") , "src", "test", "resources", "log4j.properties").toAbsolutePath().toString());
    }

    @BeforeClass
    protected void initTest() {
        LOGGER.info("Initializing ChromeDriver");
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
