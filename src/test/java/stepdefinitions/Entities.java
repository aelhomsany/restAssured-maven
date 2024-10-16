package stepdefinitions;

import base.Base;
import io.cucumber.java8.En;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.json.simple.JSONObject;
import request.Request;

public class Entities implements En {
    public Entities(Base base) {


        Given("^send \"([^\"]*)\" get request to get vendor registered data details$", (String validity) -> {

            Request.Builder requestBuilder = new Request.Builder(base.environmentPropPicker.getRootURL(), Method.GET);
            requestBuilder.path(base.routes.GET_ENTITIES).contentType(ContentType.JSON);

            Request request = requestBuilder.build();
            base.response = request.send();
            System.out.println(request.getLogs());

            if (base.response.getStatusCode() == 200) {
                System.out.println("Entities name is: " + base.response.jsonPath().get("name"));

            }

        });
    }
}
