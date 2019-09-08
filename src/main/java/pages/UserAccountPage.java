package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UserAccountPage extends AbstractPage {

    @FindBy(xpath = "//span[@data-title-shortcut = 'N']/child::span")
    WebElement newLetterButton;

    @FindBy(xpath = "//div[@class='nav__folder-name__txt'][text()='Черновики']")
    WebElement draftButton;

    @FindBy(xpath = "//div[@class='nav__folder-name__txt'][text()='Корзина']")
    WebElement trashButton;

    @FindBy(xpath = "//a[@href='/sent/']")
    WebElement sentButton;

    @FindBy(id = "PH_user-email")
    WebElement authorisationButton;

    @FindBy(xpath = "//a[@class='llc js-tooltip-direction_letter-bottom js-letter-list-item llc_normal llc_first']")
    WebElement firstLetterInFolder;

    @FindBy(xpath = "//h2[@class='thread__subject']")
    WebElement subjectInMailForSentFolder;

    @FindBy(id = "PH_logoutLink")
    WebElement logoutLink;

    @FindBy(xpath = "//div[@class='dataset__items']")
    WebElement datasetEmail;

    @FindBy(xpath = "//span[@class='list-item__text'][text()='Удалить']")
    WebElement contextMenuDeleteMessage;

    @FindBy(xpath = "//span[@class='list-item__text'][text()='Очистить содержимое']")
    WebElement contextMenuClearContents;

    @FindBy(xpath = "//span[text()='Очистить']/ancestor::span[@class='button2 button2_base button2_primary button2_compact button2_fluid button2_hover-support']")
    WebElement clearButton;

    @FindBy(xpath = "//div[@class='octopus__text'][text()='В корзине пусто']")
    WebElement emptyTrash;

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage writeNewLetter() {
        waitForElementVisible(newLetterButton);
        newLetterButton.click();
        return new NewLetterPage(driver);
    }

    public String checkAutorisationData() {
        waitUntilElementClickable(authorisationButton);
        return authorisationButton.getText();
    }

    public void tapOnDraftButton() {
        waitForElementVisible(draftButton);
        draftButton.click();
    }

    public void tapOnSentButton() {
        waitUntilElementClickable(sentButton);
        sentButton.click();
    }

    public OldLetterPage tapOnTheFirstLetterInFolder() {
        waitUntilElementClickable(firstLetterInFolder);
        firstLetterInFolder.click();
        return new OldLetterPage(driver);
    }

    public String checkSubjectInMailForSentFolder() {
        waitUntilElementClickable(subjectInMailForSentFolder);
        return subjectInMailForSentFolder.getText();
    }

    public boolean isEmailWithSubjectExists(String mailSubject) {
        waitUntilElementClickable(datasetEmail);
        String xpath = String.format(".//span[@class='ll-sj__normal'][text()='%s']", mailSubject);
        List mails = datasetEmail.findElements(By.xpath(xpath));
        return mails.size() > 0;
    }

    public void deleteMessageToTrashBySubject(String subject) {
        Actions actions = new Actions(driver);
        String xpath = String.format("//span[text()='%s']/ancestor::div[@class='llc__container']", subject);
        WebElement messageRow = driver.findElement(By.xpath(xpath));
        actions.contextClick(messageRow).build().perform();
        actions.release();
        waitForElementVisible(contextMenuDeleteMessage);
        contextMenuDeleteMessage.click();
    }

    public void cleanTrashFolder(String subject){
        Actions actions = new Actions(driver);
        actions.contextClick(trashButton).build().perform();
        actions.release();
        waitForElementVisible(contextMenuClearContents);
        contextMenuClearContents.click();
        waitForElementVisible(clearButton);
        actions.moveToElement(clearButton).click().build().perform();
    }

    public void tapOnTrashButton() {
        waitForElementVisible(trashButton);
        trashButton.click();
    }

    public boolean isTrashFolderEmpty() {
        new WebDriverWait(driver, AbstractPage.WAIT_FOR_ELEMENT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dataset__items']")));
        return emptyTrash.isDisplayed();
    }

    public void logOutFromMailBox(){
        logoutLink.click();
    }
}

