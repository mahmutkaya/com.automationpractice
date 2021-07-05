package utilities;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.internal.RestAssuredResponseOptionsImpl;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static Response response;

    private static final Filter FORCE_JSON_RESPONSE_BODY = (reqSpec, respSpec, ctx) -> {
        Response response = ctx.next(reqSpec, respSpec);
        ((RestAssuredResponseOptionsImpl) response).setContentType("application/json");
        return response;
    };

    static {
        RestAssured.baseURI = ConfigReader.getProperty("base_uri");
        //response comes as html format. So, we need to use this to get json response
        RestAssured.filters(FORCE_JSON_RESPONSE_BODY);
    }

    private static Map<String, String> getHeaderSpecs() {
        Map<String, String> headerSpecs = new HashMap<>();
        headerSpecs.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerSpecs.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headerSpecs.put("Cookie", ConfigReader.getProperty("cookie"));

        return headerSpecs;
    }


    public static Response post(String pathParams, Map<String, Object> formParams) {

        pathParams = pathParams != null ? pathParams : "";
        formParams.put("token", ConfigReader.getProperty("token"));

        try {
            response = given().headers(getHeaderSpecs())
                    .formParams(formParams)
                    .when()
                    .post(pathParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response get(String pathParams) {
        try {
            response = given()
                    .when()
                    .get(pathParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response put(String pathParams, Map<String, Object> reqBody) {
        try {
            response = given().headers(getHeaderSpecs())
                    .body(reqBody)
                    .when()
                    .put(pathParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response delete(String pathParams) {
        try {
            response = given()
                    .when()
                    .delete(pathParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
