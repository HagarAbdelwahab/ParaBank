package services.requests;

import constants.Endpoints;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OverviewService {

    @Step("get customer information")
    public static Response getCustomerInfo(Map<String, String> cookie) {

        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .cookies(cookie)
                .log().all()
                .when()
                .get(Endpoints.OVERVIEW)
                .then().log().all().extract().response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }
}
