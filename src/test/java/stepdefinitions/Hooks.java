package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.ConfigReader;
import utilities.Driver;

public class Hooks {

    private boolean isAPI;

    @Before
    public void setUp(Scenario scenario) {
        isAPI = scenario.getSourceTagNames().contains("@api");
        if(!isAPI) Driver.getDriver().get(ConfigReader.getProperty("web_url"));
    }

    @After
    public void tearDown(Scenario scenario) {
        if (!isAPI && scenario.isFailed()) {
            final byte[] screenshot = (
                    (TakesScreenshot) Driver.getDriver()
            ).getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "screenshot");
        }
        Driver.closeDriver();
    }
}
