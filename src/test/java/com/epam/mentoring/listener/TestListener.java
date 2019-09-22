package com.epam.mentoring.listener;

import com.epam.mentoring.driver.WebDriverInstance;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    Logger LOGGER = LogManager.getLogger(TestListener.class);
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("==========Test" + iTestResult.getTestName() + " started==========");

    }

    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info("==========Test" + iTestResult.getTestName() + " success==========");
    }

    public void onTestFailure(ITestResult iTestResult) {
        LOGGER.info("==========Test" + iTestResult.getTestName() + " failure==========");
        saveScreenshot();
    }

    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.info("==========Test" + iTestResult.getTestName() + " skipped==========");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    public void onStart(ITestContext iTestContext) {
    }

    public void onFinish(ITestContext iTestContext) {
    }

    private void saveScreenshot(){
        File screenCapture = ((TakesScreenshot) WebDriverInstance.getDriverInstance())
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenCapture, new File(
                    ".//target/screenshots/"
                            + getCurrentTimeAsString() +
                            ".png"));
        } catch (IOException e) {
            LOGGER.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }

    private String getCurrentTimeAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu-MM-dd_HH-mm-ss" );
        return ZonedDateTime.now().format(formatter);
    }
}
