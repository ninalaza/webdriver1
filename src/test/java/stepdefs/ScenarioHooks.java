package stepdefs;
import com.epam.mentoring.driver.WebDriverInstance;
import io.cucumber.java.After;
import io.cucumber.java.Before;



public class ScenarioHooks {

    @Before
    public void beforeScenario(){
    }

    @After
    protected void afterScenario() {
        WebDriverInstance.closeDriver();
    }
    }
