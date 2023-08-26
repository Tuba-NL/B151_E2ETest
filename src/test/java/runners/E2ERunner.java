package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {},
        glue = {"stepdefinitions","hooks"},
        dryRun = false
)

public class E2ERunner {

}
