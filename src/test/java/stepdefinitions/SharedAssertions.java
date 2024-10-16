package stepdefinitions;

import base.Base;
import io.cucumber.java8.En;
import org.testng.Assert;

public class SharedAssertions implements En {
    public SharedAssertions(Base base) {
        Then("^response status code is \"([^\"]*)\"$", (String statusCode) -> {

            Assert.assertEquals(base.response.getStatusCode(), Integer.parseInt(statusCode));

        });
    }
}
