package services.requests;

import constants.Endpoints;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class TransferService {

    @Step("transfer money from one account to another")

    public static Response transferMoney(int fromAccountId, int toAccountId, Double amount, Map<String, String> cookie) {
        Response response = given()
                .baseUri(Endpoints.BASE_URL)
                .cookies(cookie)
                .queryParam("fromAccountId", fromAccountId)
                .queryParam("toAccountId", toAccountId)
                .queryParam("amount", amount)
                .when()
                .post(Endpoints.TRANSFER_MONEY);

        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return response;

    }
}
