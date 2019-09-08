package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewLetterPage extends AbstractPage {

    @FindBy(xpath = "//input[@tabindex='100']")
    WebElement fillAdressField;

    @FindBy(xpath = "//input[@tabindex='400']")
    WebElement fillSubjectField;

    @FindBy(xpath = "//div[@role = 'textbox']")
    WebElement fillBodyInNewLetterField;

    @FindBy(xpath = "//span[text()='Сохранить']")
    WebElement saveLetterButton;

    @FindBy(xpath = "//button[@title='Закрыть']")
    WebElement quitButton;

    public NewLetterPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage fillAdressField(String query) {
        waitForElementVisible(fillAdressField);
        fillAdressField.sendKeys(query);
        return this;
    }

    public NewLetterPage fillSubjectField(String query) {
        fillSubjectField.sendKeys(query);
        return this;
    }

    public NewLetterPage fillSubjectFieldByAction(String query) {
        Actions builder = new Actions(driver);
        builder.moveToElement(fillSubjectField).click().keyDown(fillSubjectField, Keys.SHIFT)
                .sendKeys(fillSubjectField, query).keyUp(fillSubjectField, Keys.SHIFT).doubleClick(fillSubjectField)
                .build().perform();
        return this;
    }

    public NewLetterPage fillBodyInNewLetterField(String query) {
        fillBodyInNewLetterField.sendKeys(query);
        return this;
    }

    public NewLetterPage saveLetterInDrafts() {
        saveLetterButton.click();
        return this;
    }

    public void closeComposeMessage() {
        quitButton.click();
        new WebDriverWait(driver, AbstractPage.WAIT_FOR_ELEMENT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='compose-windows']")));
    }
}
