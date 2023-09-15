package runner;

import io.cucumber.testng.*;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.Objects;

@CucumberOptions(features = "src/test/java/features",
        glue = "stepDefinitions",
        tags = "not @ignore",
        plugin = {"pretty",
                "html:target/site/cucumber-pretty",
                "json:target/cucumber.json"}
)

public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context) {
        XmlTest currentXmlTest = context.getCurrentXmlTest();
        Objects.requireNonNull(currentXmlTest);
        CucumberPropertiesProvider properties = currentXmlTest::getParameter;
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass(), properties);
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleEvent, FeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runScenario(pickleEvent.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
}
