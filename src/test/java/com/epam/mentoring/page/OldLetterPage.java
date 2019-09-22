package com.epam.mentoring.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class OldLetterPage extends AbstractPage {

    public OldLetterPage
            (WebDriver driver) {
        super(driver);
    }

    @FindBy (name = "Subject")
    WebElement mailSubject;

    @FindBy (xpath = "//div[contains(@class, 'editable-container')]")
    WebElement mailEditor;

    @FindBy (xpath = "//span[text()='Отправить']")
    WebElement sendButton;

    @FindBy(xpath = "//span[@title='Закрыть']")
    WebElement closeButtonX;

    @FindBy(xpath = "//button[@title='Закрыть']")
    WebElement closeComposeMessageButtonX;

    public String getMailSubject(){
        return mailSubject.getAttribute("value");
    }

    public boolean isTextBodyExistsInMessage(String mailBody) {
        waitForElementVisible(mailEditor);
        String xpath = String.format(".//div[contains(text(), '%s')]", mailBody);
        List texts = mailEditor.findElements(By.xpath(xpath));

        return texts.size() > 0;
    }

    public  void sendMail(){
        waitUntilElementClickable(sendButton);
        sendButton.click();
    }

    public void closeAlertSendSuccessfull(){
        waitForElementVisible(closeButtonX);
        closeButtonX.click();
    }

    public void closeComposeMessage(){
        closeComposeMessageButtonX.click();
        new WebDriverWait(driver, AbstractPage.WAIT_FOR_ELEMENT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='compose-windows']")));
    }
}
