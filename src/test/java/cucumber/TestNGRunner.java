package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/java/cucumber",
	    glue = {"stepDefinition", "BaseClass"}, // Just the package name, not the full path
	    monochrome = true,
	    tags = "@tag2",
	    plugin = {"html:target/cucumber-reports.html","json:target/cucumber.json"}
	)
	public class TestNGRunner extends AbstractTestNGCucumberTests {
	
	}

