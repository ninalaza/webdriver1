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

public class MailRuSimpleTest extends BaseTest {

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

       homePage.open().fillLoginField(testUser).clickEnterPassword()
                .fillPasswordField(testUser).startUserSession();

        String authorizationData = userAccountPage.checkAutorisationData();
        Assert.assertEquals(authorizationData, testUser.getEmail(), "The email does not belong to the account being verified");
    }

    @Test(description = "check if new mail present in draft folder", dependsOnMethods = {"loginToMailBoxTest"})
    public void checkMailSubjectInDraftFolderTest() {
        userAccountPage.writeNewLetter();
        newLetter.fillAdressField(testUser)
                .fillSubjectField(emailSubject).fillBodyInNewLetterField(emailBody);
        newLetter.saveLetterInDrafts();
        newLetter.closeComposeMessage();
        userAccountPage.tapOnDraftButton();
        userAccountPage.tapOnTheFirstLetterInFolder();

        Assert.assertEquals(oldLetterPage.getMailSubject(), emailSubject);
    }

    @Test(description = "check text in letter", dependsOnMethods = {"checkMailSubjectInDraftFolderTest"})
    public void checkMailTextInDraftsFolderTest() {
        Assert.assertTrue(oldLetterPage.isTextBodyExistsInMessage(emailBody), String.format("Text: %s is not found in message body", emailBody));
    }

    @Test(description = "check letter in Draft folder", dependsOnMethods = {"checkMailTextInDraftsFolderTest"})
    public void checkDraftFolderIsEmpty() {
        oldLetterPage.sendMail();
        oldLetterPage.closeAlertSendSuccessfull();
        userAccountPage.tapOnDraftButton();

        Assert.assertFalse(userAccountPage.isEmailWithSubjectExists(emailSubject), String.format("Email with subject %s exists in folder", emailSubject));
    }

    @Test(description = "check letter in Draft folder", dependsOnMethods = {"checkDraftFolderIsEmpty"})
    public void checkSentFolderIsNotEmpty() {
        userAccountPage.tapOnSentButton();
        userAccountPage.tapOnTheFirstLetterInFolder();
        Assert.assertEquals(userAccountPage.checkSubjectInMailForSentFolder(), emailSubject);
    }
}