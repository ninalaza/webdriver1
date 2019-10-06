package stepdefs;

import com.epam.mentoring.page.HomePage;
import com.epam.mentoring.page.NewLetterPage;
import com.epam.mentoring.page.OldLetterPage;
import com.epam.mentoring.page.UserAccountPage;
import com.epam.mentoring.testdata.bean.User;
import com.epam.mentoring.util.MockDataUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class MyStepdefs {

    private UserAccountPage userAccountPage;
    WebDriver driver;
    User testUser;


    @Given("I open main MailRu page")
    public void iOpenMailRuSite() {
        new HomePage(driver).open();

    }

    @When("I enter <login> and <password>")
    public void iEnterLoginAndPassword() {
        new HomePage(driver).fillLoginField(testUser).clickEnterPassword()
                .fillPasswordField(testUser);
    }

    @And("I press to enter button")
    public void iPressToLoginButton() {
        new HomePage(driver).startUserSession();
    }


    @Then("Successful login is performed")
    public void successfulLoginIsPerformed() {
        String authorizationData = userAccountPage.checkAutorisationData();
        Assert.assertEquals(authorizationData, testUser.getEmail(), "The email does not belong to the account being verified");

    }
}

