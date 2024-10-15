package stepdefinitions;

import base.Base;
import enviroments.EnvironmentsLoader;
import io.cucumber.java8.En;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.json.simple.JSONObject;
import request.Request;

public class Login implements En {

    EnvironmentsLoader envPropertiesLoader = EnvironmentsLoader.getInstance();

    public Login(Base base) {
        Given("^login as \"([^\"]*)\" \"([^\"]*)\"$", (String userName, String number) -> {

            JSONObject body = base.environmentPropPicker.getUserCredentials(userName, number);
            System.out.println(body);

            Request.Builder requestBuilder = new Request.Builder(base.environmentPropPicker.getRootURL(), Method.POST);
            requestBuilder.path(base.routes.LOGIN).contentType(ContentType.JSON);
            requestBuilder.body(body);

            Request request = requestBuilder.build();
            base.response = request.send();
            System.out.println(request.getLogs());

            if (base.response.getStatusCode() == 200) {
                JSONObject token = new JSONObject();
                token.put("Authorization", "Bearer " + base.response.jsonPath().get("token").toString());
                base.bearerToken = token;

            }

        });
    }
}
