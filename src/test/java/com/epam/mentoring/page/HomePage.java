package com.epam.mentoring.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    @FindBy (xpath = "//input[@id='mailbox:login']")
    WebElement loginField;

    @FindBy (xpath = "//input[@id='mailbox:password']")
    WebElement passwordField;

    @FindBy (xpath = "//label[@id='mailbox:submit']")
    WebElement goButton;

    @FindBy (xpath = "//input[@value='Ввести пароль']")
    WebElement enterPasswordButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get("https://mail.ru");
        return this;
    }

    public HomePage fillLoginField(String query) {
        waitForElementVisible(loginField);
        loginField.sendKeys(query);
        return this;
    }

    public HomePage fillPasswordField(String query) {
        waitUntilElementClickable(passwordField);
        passwordField.sendKeys(query);
        return this;
    }

    public HomePage clickEnterPassword() {
        enterPasswordButton.click();
        return this;
    }

    public UserAccountPage startUserSession() {
        goButton.click();
        return new UserAccountPage(driver);
    }
}


