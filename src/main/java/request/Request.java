package request;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CustomLogFilter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;


public class Request {

    private final String baseUrl;
    private final Method method;
    private final JSONObject headers;
    private final String path;
    private final JSONObject pathParameters;
    private final JSONObject queryParameters;
    private final ContentType contentType;
    private final Object body;
    private final Object formParam;
    private final String[] multiPart;
    private final String[] name;
    private final String mimeType;
    private final JSONObject formBody;
    private final Filter logFilter = new CustomLogFilter();
    private final Logger logger = LoggerFactory.getLogger(Request.class);

    private Request(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.method = builder.method;
        this.headers = builder.headers;
        this.path = builder.path;
        this.pathParameters = builder.pathParameters;
        this.queryParameters = builder.queryParameters;
        this.contentType = builder.contentType;
        this.body = builder.body;
        this.formParam = builder.formParam;
        this.multiPart = builder.multiPart;
        this.name = builder.name;
        this.mimeType = builder.mimeType;
        this.formBody = builder.formBody;
    }

    public static class Builder {

        private final String baseUrl;
        private final Method method;
        private JSONObject headers;
        private String path;
        private JSONObject pathParameters;
        private JSONObject queryParameters;
        private ContentType contentType;
        private Object body;
        private Object formParam;
        private String[] multiPart;
        private String[] name;
        private String mimeType;
        private JSONObject formBody;

        public Builder(String baseUrl, Method method) {
            this.baseUrl = baseUrl;
            this.method = method;
        }

        public Builder headers(JSONObject headers) {
            this.headers = headers;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder pathParameters(JSONObject pathParameters) {
            this.pathParameters = pathParameters;
            return this;
        }

        public Builder queryParameters(JSONObject queryParameters) {
            this.queryParameters = queryParameters;
            return this;
        }

        public Builder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public Builder formParam(Object formParam) {
            this.formParam = formParam;
            return this;

        }

        public Builder multiPart(String[] name, String[] multiPart, String mimeType) {
            this.name = name;
            this.multiPart = multiPart;
            this.mimeType = mimeType;
            this.formBody = formBody;

            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

    /**
     * @return request specification  with basic requirement
     */
    private RequestSpecification prepareRequest() throws IOException {
        //Create Request Spec Builder
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        //Set Base Url
        requestSpecBuilder.setBaseUri(this.baseUrl);
        //set Headers
        if (this.headers != null) {
            requestSpecBuilder.addHeaders(this.headers);

        }
        //Set path
        if (this.path != null) {
            requestSpecBuilder.setBasePath(this.path);
            requestSpecBuilder.setUrlEncodingEnabled(false);
        }
        //Set Path Parameters
        if (this.pathParameters != null) {
            requestSpecBuilder.addPathParams(this.pathParameters);
            requestSpecBuilder.setUrlEncodingEnabled(false);
        }
        //Set Query Parameters rest assured will encode the query parameters
        if (this.queryParameters != null) {
            requestSpecBuilder.addQueryParams(this.queryParameters);
            requestSpecBuilder.setUrlEncodingEnabled(true);

        }
        //Set Content Type
        if (this.contentType != null) {
            if (this.contentType == JSON) {
                requestSpecBuilder.addHeader("Accept", "application/json");
            }
            requestSpecBuilder.setContentType(this.contentType);

        }
        //Body
        if (this.body != null && this.contentType != null) {
            switch (contentType) {
                case JSON:
                    requestSpecBuilder.setBody(this.body, ObjectMapperType.GSON);
                    break;
                case URLENC:
                    requestSpecBuilder.addFormParams(((JSONObject) this.body));
                    break;
                case XML:
                    requestSpecBuilder.setBody(this.body, ObjectMapperType.JAXB);
                    break;
                default:
                    break;
            }
        }
        if (this.multiPart != null) {
            int size = this.name.length;
            for (int i = 0; i < size; i++) {
                String name = this.name[i];
                String multiPart = this.multiPart[i];
                String mimeType = this.mimeType;
                JSONObject formBody = (JSONObject) this.body;

                requestSpecBuilder.addMultiPart(name, new File(multiPart), mimeType);

                if (formBody != null && formBody.containsKey("threadId")) {
                    try {
                        requestSpecBuilder.addMultiPart("threadId", formBody.get("threadId").toString());
                        requestSpecBuilder.addMultiPart("type", formBody.get("type").toString());
                        requestSpecBuilder.addMultiPart("file", formBody.get("fileName").toString(), Files.readAllBytes(Paths.get(multiPart)), mimeType);
                        requestSpecBuilder.addMultiPart("fileName", formBody.get("fileName").toString());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (formBody != null && formBody.containsKey("target")) {
                    requestSpecBuilder.addMultiPart("title", formBody.get("title").toString());
                    requestSpecBuilder.addMultiPart("target", formBody.get("target").toString());
                }
                if (formBody != null && formBody.containsKey("mobile_app")) {
                    // Iterate through the keys of the formBody JSON object
                    for (Object key : formBody.keySet()) {
                        // Get the value for the key
                        Object value = formBody.get(key);

                        // Add the key-value pair as a multi-part to requestSpecBuilder
                        requestSpecBuilder.addMultiPart(key.toString(), value.toString());
                    }


                }
                if (formBody != null && formBody.containsKey("orgId")) {
                    requestSpecBuilder.addMultiPart("orgId", formBody.get("orgId").toString());
                }

                System.out.println("Form Body sent with the multiPart: "+formBody);
            }
        }
        //Create request Object
        RequestSpecification request = requestSpecBuilder.build();
        request = RestAssured.given().spec(request).filter(logFilter);
        return request;
    }

    public Response send() {
        //Request
        RequestSpecification request = null;
        try {
            request = prepareRequest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response;
        //Request based on request Type
        switch (this.method) {

            case POST:
                response = request.post();
                logger.info(this.getLogs());
                return response;
            case PATCH:
                response = request.patch();
                logger.info(this.getLogs());
                return response;
            case PUT:
                response = request.put();
                logger.info(this.getLogs());
                return response;
            case GET:
                response = request.get();
                logger.info(this.getLogs());
                return response;
            case DELETE:
                response = request.delete();
                logger.info(this.getLogs());
                return response;
            default:
                break;
        }
        return null;
    }

    public String getLogs() {
        return ((CustomLogFilter) logFilter).getRequestAndResponse();
    }

}
