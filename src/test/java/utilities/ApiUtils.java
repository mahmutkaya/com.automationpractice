package utilities;

import io.restassured.filter.Filter;
import io.restassured.internal.RestAssuredResponseOptionsImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static Response response;

    private static RequestSpecification getRequest() {
        RequestSpecification request = given()
                .baseUri(ConfigReader.getProperty("base_uri"))
                .filters(FORCE_JSON_RESPONSE_BODY)
                .formParam("token", ConfigReader.getProperty("token"))
                .headers(getHeaderSpecs())
                .when();

        return request;
    }

    //response comes as html format. So, we need to use this to get json response
    private static final Filter FORCE_JSON_RESPONSE_BODY = (reqSpec, respSpec, ctx) -> {
        Response response = ctx.next(reqSpec, respSpec);
        ((RestAssuredResponseOptionsImpl) response).setContentType("application/json");
        return response;
    };

    private static Map<String, String> getHeaderSpecs() {
        Map<String, String> headerSpecs = new HashMap<>();
        headerSpecs.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerSpecs.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headerSpecs.put("Cookie", ConfigReader.getProperty("cookie"));

        return headerSpecs;
    }

    public static Response post(String pathParams, Map<String, Object> formParams) {
        try {
            response = getRequest().formParams(formParams).post(pathParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response get(String pathParams) {
        try {
            response = getRequest().get(pathParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response put(String pathParams, Map<String, Object> reqBody) {
        try {
            response = getRequest().formParams(reqBody).put(pathParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response delete(String pathParams) {
        try {
            response = getRequest().put(pathParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
