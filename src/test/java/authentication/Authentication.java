package authentication;

import base.Base;
import enviroments.EnvironmentsLoader;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.json.simple.JSONObject;
import request.Request;

import java.util.HashMap;

import static io.restassured.RestAssured.proxy;

public class Authentication extends Base {

    public Authentication(Base base) {
        EnvironmentsLoader envPropertiesLoader = EnvironmentsLoader.getInstance();

    }

    public Request getLoginRequest(String userName, String password) {

        HashMap<String, String> authenticationProperties = environmentPropPicker.getAuthenticationProperties();

        //Prepare Body
        JSONObject body = new JSONObject();
        body.put("client_id", authenticationProperties.get("client_id"));
        body.put("grant_type", "password");
        body.put("scope", "offline_access hawity");
        body.put("username", userName);
        body.put("password", password);

        System.out.println(body+ " body");

        //Create Request Builder
        Request.Builder requestBuilder = new Request.Builder(environmentPropPicker.getLoginURI(), Method.POST);
        System.out.println(environmentPropPicker.getLoginURI()+ " <<<<<< URI");
        //Set Content-type
        requestBuilder.contentType(ContentType.URLENC);

        //set body
        requestBuilder.body(body);


        return requestBuilder.build();
    }

}
