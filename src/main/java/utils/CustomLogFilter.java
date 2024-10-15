package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomLogFilter implements Filter {
    private StringBuilder requestBuilderLogs;
    private StringBuilder responseBuilderLogs;

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {

        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);

        requestBuilderLogs = new StringBuilder();
        requestBuilderLogs.append("""

                -------------------------------------------------------------------------------------
                """);
        requestBuilderLogs.append("""

                #[API Request]:
                """);
        requestBuilderLogs.append("Request method: ").append(objectValidation(filterableRequestSpecification.getMethod())).append("\n");
        requestBuilderLogs.append("Request URI: ").append(objectValidation(filterableRequestSpecification.getURI())).append("\n");
        requestBuilderLogs.append("Form Params: ").append(objectValidation(filterableRequestSpecification.getFormParams())).append("\n");
        requestBuilderLogs.append("Request Param: ").append(objectValidation(filterableRequestSpecification.getRequestParams())).append("\n");
        requestBuilderLogs.append("Headers: ").append(objectValidation(filterableRequestSpecification.getHeaders())).append("\n");
        requestBuilderLogs.append("Body: ").append(objectValidation(filterableRequestSpecification.getBody())).append("\n");

        responseBuilderLogs = new StringBuilder();
        responseBuilderLogs.append("""

                -[API Response]:
                """);
        responseBuilderLogs.append("Status Code:").append(response.getStatusCode()).append("\n");
        responseBuilderLogs.append("Response Body: " + "\n").append(response.getBody().prettyPrint()).append("\n");
        responseBuilderLogs.append("Response Time: " + "\n").append(response.getTime()).append(" milliseconds").append("\n");
        responseBuilderLogs.append("""

                -------------------------------------------------------------------------------------
                """);

        return response;
    }

    public String getRequestBuilder() {
        return requestBuilderLogs.toString();
    }

    public String getResponseBuilder() {
        return responseBuilderLogs.toString();
    }

    public String getRequestAndResponse() {
        return getRequestBuilder() + getResponseBuilder();
    }

    public String objectValidation(Object o) {
        if (o == null)
            return null;
        else
            return o.toString();
    }

}
