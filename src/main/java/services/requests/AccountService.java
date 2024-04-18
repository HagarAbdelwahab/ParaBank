package services.requests;

import constants.Endpoints;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


import java.util.Map;
public class AccountService {

    @Step("Open a new account")
    public static Response openAccount(String customerId, int fromAccountId,  Map<String, String> cookie){
        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .cookies(cookie)
                .queryParam("customerId", customerId)
                .queryParam("newAccountType", 0)
                .queryParam("fromAccountId", fromAccountId)
                .when()
                .post(Endpoints.CREATE_ACCOUNT);
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }

    @Step("Get list of accounts")
    public static Response getAccounts(String customerId, Map<String, String> cookie){
        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .cookies(cookie)
                .when()
                .get(String.format(Endpoints.ACCOUNTS,customerId))
                .then().extract().response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }






}
