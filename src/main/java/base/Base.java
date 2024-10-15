package base;

import com.jayway.jsonpath.JsonPath;
import enviroments.EnvironmentPropPicker;
import io.restassured.response.Response;
import helpermethods.AssistanceMethods;
import org.json.simple.JSONObject;
import request.Request;
import request.Routes;


public class Base {



    public Base() {
        // Initialize dependencies (e.g., assistanceMethods, request, etc.)
    }

    public Routes routes = new Routes();
    public AssistanceMethods assistanceMethods = new AssistanceMethods();
    public EnvironmentPropPicker environmentPropPicker = new EnvironmentPropPicker();
    // general Objects
    public Response response;
    public String city;
    public Request lastRequest;
    public String localDir = System.getProperty("user.dir");
    public JSONObject bearerToken = new JSONObject();



    // Vendor registration
    public String attachmentID;
    public String taxRegNo;

}
