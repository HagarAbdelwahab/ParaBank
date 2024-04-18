package services.requests;

import constants.Endpoints;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


import java.util.Map;
public class AccountService {

    @Step("open a new account")
    public static Response openAccount(String customerId, int fromAccountId,  Map<String, String> cookie){
        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("accept-language", "en-US,en;q=0.9,ar;q=0.8")
                .header("cache-control", "max-age=0")
                .header("content-type", "application/x-www-form-urlencoded")
                .cookies(cookie)
                .queryParam("customerId", customerId)
                .queryParam("newAccountType", 0)
                .queryParam("fromAccountId", fromAccountId)
                .log().all()
                .when()
                .post(Endpoints.CREATE_ACCOUNT);
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }

    @Step("get list of accounts")
    public static Response getAccounts(String customerId, Map<String, String> cookie){
        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("accept-language", "en-US,en;q=0.9,ar;q=0.8")
                .header("cache-control", "max-age=0")
                .header("content-type", "application/x-www-form-urlencoded")
                .cookies(cookie)
                .log().all()
                .when()
                .get(String.format(Endpoints.ACCOUNTS,customerId))
                .then().extract().response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }






}
