package testrunners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        dryRun = false,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        features = {"src/test/resources/features"},
        glue = {"stepdefinitions"},
        tags = "@getVendorData"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void setup() {
    }

    @DataProvider(parallel = false)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public void tearDown() {
    }
}