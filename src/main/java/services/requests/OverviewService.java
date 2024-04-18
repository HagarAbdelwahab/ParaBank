package services.requests;

import constants.Endpoints;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OverviewService {

    @Step("Get customer information")
    public static Response getCustomerInfo(Map<String, String> cookie) {

        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .cookies(cookie)
                .when()
                .get(Endpoints.OVERVIEW)
                .then().extract().response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }
}
