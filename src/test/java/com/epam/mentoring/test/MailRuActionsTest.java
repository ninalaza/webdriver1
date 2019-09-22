package com.epam.mentoring.test;

import com.epam.mentoring.page.HomePage;
import com.epam.mentoring.page.NewLetterPage;
import com.epam.mentoring.page.OldLetterPage;
import com.epam.mentoring.page.UserAccountPage;
import com.epam.mentoring.testdata.TestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.epam.mentoring.util.MockDataUtils;

public class MailRuActionsTest  extends BaseTest {

    private HomePage homePage;
    private OldLetterPage oldLetterPage;
    private UserAccountPage userAccountPage;
    private NewLetterPage newLetter;
    private String emailSubject;
    private String emailBody;

    @BeforeClass()
    private void initPages() {
        homePage = new HomePage(driver);
        oldLetterPage = new OldLetterPage(driver);
        userAccountPage = new UserAccountPage(driver);
        newLetter = new NewLetterPage(driver);
        emailSubject = MockDataUtils.generateRandomString(10);
        emailBody = MockDataUtils.generateRandomString(10);
    }

    @Test(description = "check if login to mail.ru is successful")
    public void loginToMailBoxTest() {

        homePage.open().fillLoginField(TestData.LOGIN_NAME).clickEnterPassword()
                .fillPasswordField(TestData.PASSWORD).startUserSession();

        String authorizationData = userAccountPage.checkAutorisationData();
        Assert.assertEquals(authorizationData, TestData.EMAIL, "The email does not belong to the account being verified");
    }

    @Test(description = "Save email as draft message and check it subject in Draft folder", dependsOnMethods = {"loginToMailBoxTest"})
    public void saveEmailAsDraftTest(){
        userAccountPage.writeNewLetter();
        newLetter.fillAdressField(TestData.RECEIVER_EMAIL)
                .fillSubjectField(emailSubject).fillBodyInNewLetterField(emailBody);

        newLetter.saveLetterInDrafts();
        newLetter.closeComposeMessage();
        userAccountPage.tapOnDraftButton();
        userAccountPage.tapOnTheFirstLetterInFolder();
        Assert.assertEquals(oldLetterPage.getMailSubject(), emailSubject);
    }

    @Test(description = "check message body", dependsOnMethods = {"saveEmailAsDraftTest"})
    public void checkMailBodyInDraftsFolderTest() {
        Assert.assertTrue(oldLetterPage.isTextBodyExistsInMessage(emailBody), String.format("Text: %s is not found in message body", emailBody));
    }

    @Test(description = "Delete message by action to trash", dependsOnMethods = {"checkMailBodyInDraftsFolderTest"})
    public void deleteMessageFromDraftFolderByActionTest() {
        oldLetterPage.closeComposeMessage();
        userAccountPage.deleteMessageToTrashBySubject(emailSubject);
        userAccountPage.tapOnTrashButton();
        Assert.assertTrue(userAccountPage.isEmailWithSubjectExists(emailSubject));
    }

    @Test(description = "Clear trash folder", dependsOnMethods = {"deleteMessageFromDraftFolderByActionTest"})
    public void clearTrashFolderByActionTest() {
        userAccountPage.cleanTrashFolder(emailSubject);
        Assert.assertTrue(userAccountPage.isTrashFolderEmpty(), "Trash folder is not empty");
    }
}
