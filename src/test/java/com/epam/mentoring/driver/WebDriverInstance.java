package com.epam.mentoring.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverInstance {

        private static WebDriver driver;
        private static ChromeOptions options = new ChromeOptions();
        private static DesiredCapabilities dc = DesiredCapabilities.chrome();

        private static final Logger LOGGER = LogManager.getLogger(WebDriverInstance.class);

        private WebDriverInstance(){}

        public static WebDriver getDriverInstance(){
        if (driver == null){
            WebDriverManager.chromedriver().setup();
            dc.setCapability(ChromeOptions.CAPABILITY, options);
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4450/wd/hub"), dc);
                LOGGER.info("Driver initialized");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
        LOGGER.info("Driver quite successfull");
    }
}
