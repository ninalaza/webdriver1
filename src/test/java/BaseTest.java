import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    WebDriver driver;
    private ChromeOptions options = new ChromeOptions();
    private DesiredCapabilities dc = DesiredCapabilities.chrome();

    @BeforeClass
    protected void initDriver() {
        WebDriverManager.chromedriver().setup();
        dc.setCapability(ChromeOptions.CAPABILITY, options);

        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4450/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(description = "close browser")
    protected void tearDown() {
        driver.close();
    }
}
