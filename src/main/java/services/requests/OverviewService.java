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
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("accept-language", "en-US,en;q=0.9,ar;q=0.8")
                .header("cache-control", "max-age=0")
                .header("content-type", "application/x-www-form-urlencoded")
                .cookies(cookie)
                .log().all()
                .when()
                .get(Endpoints.OVERVIEW)
                .then().log().all().extract().response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }
}
