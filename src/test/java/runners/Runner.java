package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/test-results/output.html",
                "json:target/test-results/output.json"
        },
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        tags = "@api",
        dryRun = false
)
public class Runner {
}
